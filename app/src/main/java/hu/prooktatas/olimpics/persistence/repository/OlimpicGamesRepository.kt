package hu.prooktatas.olimpics.persistence.repository

import android.content.Context
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import hu.prooktatas.olimpics.model.*
import hu.prooktatas.olimpics.persistence.OlimpicsDatabase
import hu.prooktatas.olimpics.persistence.entity.City
import hu.prooktatas.olimpics.persistence.entity.Country
import hu.prooktatas.olimpics.persistence.entity.Game

class OlimpicGamesRepository(context: Context) {

    private val daoGame = OlimpicsDatabase.getDatabase(context)?.gameDao()
    private val daoCity = OlimpicsDatabase.getDatabase(context)?.cityDao()
    private val daoCountry = OlimpicsDatabase.getDatabase(context)?.countryDao()


    fun buildGames():List<GameInfo>{



        val list= mutableListOf<GameInfo>()
        val year = daoGame!!.fetchAllGame()
        year.forEach {
            val city=daoCity!!.fetchCity(it.city_id)
            val country=daoCountry!!.fetchCountry(city.country_id)
            list.add(GameInfo(country.name,city.name,it.year))
        }
        return list
    }

    fun buildMarkerInfo():List<MarkerInfo>{

        val allCity=daoCity!!.fetchAllCity()
        val list= mutableListOf<MarkerInfo>()

        allCity.forEach {   city ->
                val years=daoGame!!.getYearsForCity(city.id)
                val yearsList=years.joinToString(", ")
                val cityName=city.name
                list.add(MarkerInfo(LatLng(city.latitude,city.longitude),cityName+": "+yearsList))
        }
        return list
    }

    fun buildMarkerInfoForOneCity(city:City):MarkerInfo{

            val years=daoGame!!.getYearsForCity(city.id)
            val yearsList=years.joinToString(", ")
            val cityName=city.name
            val markerInfo:MarkerInfo=MarkerInfo(LatLng(city.latitude,city.longitude),cityName+": "+yearsList)

        return markerInfo
    }

    fun cityCloseToLocation(pos1:LatLng): Pair<String,String>?{

        val allCity=daoCity!!.fetchAllCity()
        allCity.forEach {
            val array= floatArrayOf(0f)
            Location.distanceBetween(pos1.latitude,pos1.longitude,it.latitude,it.longitude,array)
            val distance=array[0]/1000
            if(distance<50){
                val city= it.name
                val country=daoCountry!!.fetchCountry(it.country_id).name
                return Pair(city,country)
            }
        }
        return null
    }


    fun processGameRequest(req:AddGameRequest):AddGameResponse{
        val checkYear=daoGame!!.checkYear(req.year)
        val checkCountry = daoCountry!!.selectCountry(req.country)
        val checkCity = daoCity!!.selectCity(req.city)
        var countryId:Long=0
        var cityId:Long=0
        if(checkYear==null) {
            if(checkCountry==null){
               countryId=daoCountry.insertCountry(Country(req.country))
            }else{ countryId=checkCountry
            }
            if(checkCity==null){
                cityId=daoCity.insertCity(City(req.city,req.latitude,req.longitude,countryId))
                daoGame.insertGame(Game(cityId,req.year))
                return AddGameResponse(AddGameResult.SUCCESS_NEW_CITY,buildMarkerInfoForOneCity(daoCity.fetchCity(cityId)))
            }else{
                cityId=checkCity
                daoGame.insertGame(Game(cityId,req.year))
                return AddGameResponse(AddGameResult.SUCCESS_EXISTING_CITY,buildMarkerInfoForOneCity(daoCity.fetchCity(cityId)))
            }


        }
        return AddGameResponse(AddGameResult.ERROR_INVALID_YEAR,null)
    }

    fun getCityPositionByYear(year: Int): LatLng? {
        val cityId=daoGame!!.fetchCitybyYear(year)
        if(cityId!=null){
        val city=daoCity!!.fetchCity(cityId)
        return LatLng(city.latitude,city.longitude)
        }
        return null
    }
}