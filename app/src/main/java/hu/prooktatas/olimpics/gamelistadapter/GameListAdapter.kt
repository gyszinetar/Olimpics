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
    fun itemClicked()
}

class GameListAdapter(val gameInfos: List<GameInfo>,  val clickHandler: GameItemClickHandler): RecyclerView.Adapter<GameInfoViewHolder>(),
    View.OnClickListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameInfoViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.gameinfo_item_row, parent, false)
        return GameInfoViewHolder(rootView)

    }

    override fun getItemCount(): Int {
        Log.d(TAG, gameInfos.size.toString() )
        return gameInfos.size
    }

    override fun onBindViewHolder(holder: GameInfoViewHolder, position: Int) {
        val gi = gameInfos[position]
        holder.textViewCity.text = gi.city
        holder.textViewCountry.text = gi.country
        holder.textViewYear.text = gi.year.toString()

        holder.itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        clickHandler.itemClicked()
    }

}

class GameInfoViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val textViewCountry = v.findViewById<TextView>(R.id.textViewCountry)
    val textViewCity = v.findViewById<TextView>(R.id.textViewCity)
    val textViewYear = v.findViewById<TextView>(R.id.textViewYear)
}