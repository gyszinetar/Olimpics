package hu.prooktatas.olimpics.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hu.prooktatas.olimpics.persistence.entity.City


@Dao
interface CityDao{

    @Query("select * from cities where id=:refId")
    fun fetchCity(refId:Int): City


    @Query("select * from cities")
    fun fetchAllCity(): List<City>

    @Insert
    fun insertAlbum(city: City)
}