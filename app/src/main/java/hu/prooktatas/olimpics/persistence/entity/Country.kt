package hu.prooktatas.olimpics.persistence.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "countries")
class Country() {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0
    var name : String = ""


}