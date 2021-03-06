package hu.prooktatas.olimpics.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hu.prooktatas.olimpics.persistence.entity.City
import hu.prooktatas.olimpics.persistence.entity.Country

@Dao
interface CountryDao{
    @Query("select * from countries where id=:refId")
    fun fetchCountry(refId:Long): Country


    @Query("select id from countries where name=:country")
    fun selectCountry(country:String): Long?

    @Query("select * from countries")
    fun fetchAllCountry(): List<Country>

    @Insert
    fun insertCountry(country: Country) :Long
}