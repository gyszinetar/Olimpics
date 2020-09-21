package hu.prooktatas.olimpics.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import hu.prooktatas.olimpics.persistence.entity.Game

@Dao
interface GameDao{

    @Insert
    fun insertAlbum(game: Game)


}