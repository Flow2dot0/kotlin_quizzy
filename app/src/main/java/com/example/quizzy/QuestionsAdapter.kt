package com.example.quizzy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzy.models.MyQuestion

class QuestionsAdapter(
	val questions: List<MyQuestion>,
	val itemClickListener: View.OnClickListener
) :
	RecyclerView.Adapter<QuestionsAdapter.ViewHolder>() {

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val cardView: CardView = itemView.findViewById(R.id.card_view)
		val name: TextView = itemView.findViewById(R.id.name)
		val icon: ImageView = itemView.findViewById(R.id.icon)

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val viewItem = inflater.inflate(R.layout.item_question, parent, false)

		return ViewHolder(viewItem)
	}

	override fun getItemCount(): Int {
		return questions.size
	}

	override fun onBindViewHolder(holder: QuestionsAdapter.ViewHolder, position: Int) {
		val question = questions[position]

		holder.name.text = question.title
		holder.cardView.tag = position
		holder.cardView.setOnClickListener(itemClickListener)

		when (question.level) {
			0 -> {
				holder.icon.setImageResource(R.drawable.green_circle)
			}
			1 -> {
				holder.icon.setImageResource(R.drawable.orange_circle)
			}
			2 -> {
				holder.icon.setImageResource(R.drawable.red_circle)
			}
		}
	}

}