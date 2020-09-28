package hu.prooktatas.olimpics.persistence.repository

import android.content.Context
import android.location.Location
import com.google.android.gms.maps.model.LatLng
import hu.prooktatas.olimpics.model.GameInfo
import hu.prooktatas.olimpics.model.MarkerInfo
import hu.prooktatas.olimpics.persistence.OlimpicsDatabase
import hu.prooktatas.olimpics.persistence.entity.City

class OlimpicGamesRepository(var context: Context) {
    private val database = OlimpicsDatabase.getDatabase(context)
    private val daoGame = OlimpicsDatabase.getDatabase(context)?.gameDao()
    private val daoCity = OlimpicsDatabase.getDatabase(context)?.cityDao()
    private val daoCountry = OlimpicsDatabase.getDatabase(context)?.countryDao()
    fun buildGames():List<GameInfo>{

        var list= mutableListOf<GameInfo>()
        var year = daoGame!!.fetchAllGame()
        year.forEach {
            var city=daoCity!!.fetchCity(it.city_id)
            var country=daoCountry!!.fetchCountry(city.country_id)
            list.add(GameInfo(country.name,city.name,it.year))
        }
        return list
    }

    fun buildMarkerInfo():List<MarkerInfo>{

        var allCity=daoCity!!.fetchAllCity()
        var list= mutableListOf<MarkerInfo>()




        allCity.forEach {   city ->
                var years=daoGame!!.getYearsForCity(city.id)
                val yearslist=years.joinToString(", ")
                val cityname=city.name
                list.add(MarkerInfo(LatLng(city.latitude,city.longitude),cityname+" "+yearslist))
        }
        return list
    }

    fun cityCloseToLocation(pos1:LatLng): Pair<String,String>?{

        var allCity=daoCity!!.fetchAllCity()
        allCity.forEach {
            var array= floatArrayOf(0f)
            Location.distanceBetween(pos1.latitude,pos1.longitude,it.latitude,it.longitude,array)
            var distance=array[0]/1000
            if(distance<50){
                var city= it.name
                var country=daoCountry!!.fetchCountry(it.country_id).name
                return Pair(city,country)
            }
        }
        return null
    }
}