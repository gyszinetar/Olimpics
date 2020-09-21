package hu.prooktatas.olimpics.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import hu.prooktatas.olimpics.persistence.entity.City


@Dao
interface CityDao{
    @Insert
    fun insertAlbum(city: City)
}