package com.themanol.reactbasket.games.presentation.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.extensions.format
import com.themanol.reactbasket.games.presentation.R

private const val LOADER_HOLDER: Int = 1
private const val GAME_HOLDER: Int = 2

class GamesAdapter(var loading: Boolean = false, private val onTeamClick: (Int) -> Unit) :
    ListAdapter<Game, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == GAME_HOLDER) {
            val teamLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.game_item, parent, false)
            GameViewHolder(teamLayout, onTeamClick)
        } else {
            val progressBar = ProgressBar(parent.context)
            progressBar.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            object : RecyclerView.ViewHolder(progressBar) {}
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (loading && position == itemCount - 1) {
            LOADER_HOLDER
        } else {
            GAME_HOLDER
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GameViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun getItemCount(): Int {
        return if (loading) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }
    }

    class GameViewHolder(
        gameItemView: View,
        private val onTeamClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(gameItemView) {

        private val homeNameTV: TextView = itemView.findViewById(R.id.home_team_name)
        private val visitorNameTV: TextView = itemView.findViewById(R.id.visitor_team_name)
        private val visitorScoreTV: TextView = itemView.findViewById(R.id.visitor_team_score)
        private val homeScoreTV: TextView = itemView.findViewById(R.id.home_team_score)
        private val locationScore: TextView = itemView.findViewById(R.id.location)
        private val goToVisitorView: View = itemView.findViewById(R.id.goToVisitorTeam)
        private val goToHomeView: View = itemView.findViewById(R.id.goToHomeTeam)

        fun bind(game: Game) {
            goToVisitorView.setOnClickListener { onTeamClick(game.visitorTeam.id) }
            goToHomeView.setOnClickListener { onTeamClick(game.homeTeam.id) }
            homeNameTV.text = game.homeTeam.name
            visitorNameTV.text = game.visitorTeam.name
            visitorScoreTV.text = game.visitorTeamScore.toString()
            homeScoreTV.text = game.homeTeamScore.toString()
            if (game.visitorTeamScore > game.homeTeamScore) {
                homeScoreTV.setTextColor(Color.GRAY)
                visitorScoreTV.setTextColor(Color.BLACK)
            } else {
                homeScoreTV.setTextColor(Color.BLACK)
                visitorScoreTV.setTextColor(Color.GRAY)
            }
            locationScore.text = locationScore.resources.getString(
                R.string.game_location,
                game.homeTeam.city, game.date.format()
            )
        }
    }

}

private object DiffCallback : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean = oldItem == newItem
}