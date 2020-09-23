package hu.prooktatas.olimpics.persistence.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "cities")
class City() {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0
    var name : String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var country_id: Int = 0

}