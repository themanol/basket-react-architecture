package com.themanol.reactbasket.games.presentation.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.themanol.reactbasket.domain.Game
import com.themanol.reactbasket.extensions.format
import com.themanol.reactbasket.games.presentation.R

class GamesAdapter(private val games: List<Game>, private val onTeamClick: (Int) -> Unit) :
    RecyclerView.Adapter<GamesAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val teamLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_item, parent, false)
        return GameViewHolder(teamLayout, onTeamClick)
    }

    override fun getItemCount(): Int =
        games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(games[position])
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