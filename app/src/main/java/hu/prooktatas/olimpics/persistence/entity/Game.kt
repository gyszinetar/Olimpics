package hu.prooktatas.olimpics.persistence.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "games")
class Game() {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0
    var city_id : Int = 0
    var year : Int = 0


}