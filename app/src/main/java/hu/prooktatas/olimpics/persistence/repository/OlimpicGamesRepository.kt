package hu.prooktatas.olimpics.persistence.repository

import android.content.Context
import hu.prooktatas.olimpics.model.GameInfo
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
}