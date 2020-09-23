package hu.prooktatas.olimpics.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.prooktatas.olimpics.persistence.entity.City
import hu.prooktatas.olimpics.persistence.entity.Country
import hu.prooktatas.olimpics.persistence.entity.Game


@Database(entities = [Country::class, City::class, Game::class], version = 1)
abstract class OlimpicsDatabase: RoomDatabase() {

    //abstract fun authorDao(): AuthorDao
    //abstract fun albumDao(): AlbumDao

    companion object {
        private var dbInstance: OlimpicsDatabase? = null

            internal fun getDatabase(context: Context): OlimpicsDatabase? {
                if (dbInstance == null) {
                    synchronized(OlimpicsDatabase::class.java) {
                        if (dbInstance == null) {
                            //dbInstance = Room.databaseBuilder(context.applicationContext, OlimpicsDatabase::class.java, "Olimpics").build()
                            dbInstance = Room.databaseBuilder(context.applicationContext, OlimpicsDatabase::class.java, "olimpic_games").createFromAsset("databases/olimpic_games.db").build()
                        }
                    }
                }

                return dbInstance
        }
    }
}