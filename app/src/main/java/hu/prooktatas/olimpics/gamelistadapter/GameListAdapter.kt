package hu.prooktatas.olimpics.gamelistadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.prooktatas.olimpics.R
import hu.prooktatas.olimpics.TAG
import hu.prooktatas.olimpics.model.GameInfo

interface GameItemClickHandler {
    fun itemClicked(gameInfo: GameInfo)
}

class GameListAdapter(val gameInfos: List<GameInfo>,  val clickHandler: GameItemClickHandler): RecyclerView.Adapter<GameInfoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameInfoViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.gameinfo_item_row, parent, false)
        return GameInfoViewHolder(rootView)

    }

    override fun getItemCount(): Int {
        Log.d(TAG, gameInfos.size.toString() )
        return gameInfos.size
    }

    override fun onBindViewHolder(holder: GameInfoViewHolder, position: Int) {
        val currentGame = gameInfos[position]
        holder.textViewCity.text = currentGame.city
        holder.textViewCountry.text = currentGame.country
        holder.textViewYear.text = currentGame.year.toString()

        holder.itemView.setOnClickListener {
            clickHandler.itemClicked(currentGame)
        }
    }

}

class GameInfoViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val textViewCountry: TextView = v.findViewById(R.id.textViewCountry)
    val textViewCity: TextView = v.findViewById(R.id.textViewCity)
    val textViewYear: TextView = v.findViewById(R.id.textViewYear)
}