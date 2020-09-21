package hu.prooktatas.olimpics.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hu.prooktatas.olimpics.persistence.entity.Country
import hu.prooktatas.olimpics.persistence.entity.Game

@Dao
interface GameDao{


    @Query("select * from games")
    fun fetchAllGame(): List<Game>


    @Insert
    fun insertAlbum(game: Game)


}