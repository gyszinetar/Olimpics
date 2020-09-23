package hu.prooktatas.olimpics.persistence.repository

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import hu.prooktatas.olimpics.model.GameInfo
import hu.prooktatas.olimpics.model.MarkerInfo
import hu.prooktatas.olimpics.persistence.OlimpicsDatabase

class OlimpicGamesRepository(var context: Context) {
    private val database = OlimpicsDatabase.getDatabase(context)

    fun buildGames():List<GameInfo>{
        val daoGame = OlimpicsDatabase.getDatabase(context)?.gameDao()
        val daoCity = OlimpicsDatabase.getDatabase(context)?.cityDao()
        val daoCountry = OlimpicsDatabase.getDatabase(context)?.countryDao()
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
        val daoGame = OlimpicsDatabase.getDatabase(context)?.gameDao()
        val daoCity = OlimpicsDatabase.getDatabase(context)?.cityDao()
        val daoCountry = OlimpicsDatabase.getDatabase(context)?.countryDao()
        var allCity=daoCity!!.fetchAllCity()
        var allCountry=daoCountry!!.fetchAllCountry()
        var list= mutableListOf<MarkerInfo>()

        allCity.forEach {   city ->
            var years=daoGame!!.getYearsForCity(city.id)
            var countries = allCountry.filter {
                it.id==city.country_id
            }



            var sstring : String =city.name.plus(" Country:").plus(countries[0].name).plus(" years:")
            years.forEach{
                sstring=sstring.plus(it.toString()).plus(", ")
            }

            list.add(MarkerInfo(LatLng(city.latitude,city.longitude),sstring))



        }


        return list

    }
}