package hu.prooktatas.olimpics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.prooktatas.olimpics.gamelistadapter.GameItemClickHandler
import hu.prooktatas.olimpics.gamelistadapter.GameListAdapter
import hu.prooktatas.olimpics.model.GameInfo
import hu.prooktatas.olimpics.persistence.repository.OlimpicGamesRepository

class GameListActivity : AppCompatActivity(), GameItemClickHandler {
    private lateinit var recyclerView: RecyclerView

//    val allGames = listOf(
//        GameInfo(
//            "France",
//             "Paris",
//            1939
//        ),
//        GameInfo(
//            "Greece",
//            "Athen",
//            1896
//        ),
//        GameInfo(
//            "Belgium",
//            "Antwerpen",
//            1920
//        ),
//        GameInfo(
//            "Neatherland",
//            "Amsterdam",
//            1939
//        )
//    )

    private  fun loadItems(){
        Thread{
            val ogr = OlimpicGamesRepository(this)
            val list = ogr.buildGames()
            runOnUiThread {
                recyclerView.adapter = GameListAdapter(list,this)
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        }.start()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)
        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = GameListAdapter(emptyList(),this)

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onStart() {
        super.onStart()
        loadItems()
    }

    override fun itemClicked(gameInfo: GameInfo) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("selectedGame", gameInfo)
        startActivity(intent)
    }
}

const val TAG = "OLY"