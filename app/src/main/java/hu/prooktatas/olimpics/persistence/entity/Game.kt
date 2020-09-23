package hu.prooktatas.olimpics.persistence.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "games")
class Game(val city_id: Long, val year: Int) {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Long = 0



}