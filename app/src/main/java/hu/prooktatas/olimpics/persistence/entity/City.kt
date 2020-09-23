package hu.prooktatas.olimpics.persistence.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "cities")
data class City(val name: String,val latitude: Double,val longitude: Double,val country_id: Long) {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Long = 0


}