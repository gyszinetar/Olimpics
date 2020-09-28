package hu.prooktatas.olimpics.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hu.prooktatas.olimpics.persistence.entity.Country
import hu.prooktatas.olimpics.persistence.entity.Game

@Dao
interface GameDao{
    @Query("select * from games where city_id=:id")
    fun fetchAllGameByCityId(id:Long): List<Game>


    @Query("select * from games")
    fun fetchAllGame(): List<Game>

    @Query("select year from games where city_id=:id")
    fun getYearsForCity(id:Long): List<Int>

    @Query("select year from games where year=:year")
    fun checkYear(year:Int): Int?

    @Insert
    fun insertGame(game: Game) :Long


}