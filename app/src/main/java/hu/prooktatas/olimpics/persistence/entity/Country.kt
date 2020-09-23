package hu.prooktatas.olimpics.persistence.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "countries")
class Country(val name:String) {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Long = 0



}