package hu.prooktatas.olimpics.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import hu.prooktatas.olimpics.persistence.entity.Country

@Dao
interface CountryDao{
    @Insert
    fun insertAlbum(country: Country)
}