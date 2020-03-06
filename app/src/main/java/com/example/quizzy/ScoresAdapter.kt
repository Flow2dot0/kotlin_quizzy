package com.example.quizzy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzy.models.MyScore
import java.text.SimpleDateFormat

class ScoresAdapter(
	val scores: List<MyScore>,
	private val itemClickListener: View.OnClickListener
) :
	RecyclerView.Adapter<ScoresAdapter.ViewHolder>() {

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val cardView: CardView = itemView.findViewById(R.id.card_view_score)
		val icon: ImageView = itemView.findViewById(R.id.icon)
		val winrate: TextView = itemView.findViewById(R.id.winrate)
		val date: TextView = itemView.findViewById(R.id.date)

		// Other MyScore Attribute
		//		val level: Int? = null
		//		val date: String? = null
		//		val first: Boolean? = null
		//		val second: Boolean? = null
		//		val third: Boolean? = null
		//		val fourth: Boolean? = null
		//		val fifth: Boolean? = null
		//		val correct: Int? = null


	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val viewItem = inflater.inflate(R.layout.item_score, parent, false)

		return ViewHolder(viewItem)
	}

	override fun getItemCount(): Int {
		return scores.size
	}

	override fun onBindViewHolder(holder: ScoresAdapter.ViewHolder, position: Int) {
		val score = scores[position]

		holder.winrate.text = "${score.winrate.toString()} %"

		val sdf = SimpleDateFormat("MM/dd/yyyy")
		val dateStr = sdf.format(score.date?.toDate())

		holder.date.text = dateStr
		holder.cardView.tag = position
		holder.cardView.setOnClickListener(itemClickListener)

		when (score.level) {
			0 -> {
				holder.icon.setImageResource(R.drawable.green_circle)
			}
			1 -> {
				holder.icon.setImageResource(R.drawable.orange_circle)
			}
			2 -> {
				holder.icon.setImageResource(R.drawable.red_circle)
			}
			else -> {
				holder.icon.setImageResource(R.drawable.ic_launcher_background)

			}
		}
	}

}