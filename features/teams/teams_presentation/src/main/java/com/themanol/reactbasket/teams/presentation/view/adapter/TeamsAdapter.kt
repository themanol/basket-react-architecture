package com.themanol.reactbasket.teams.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.presentation.R

class TeamsAdapter(private val teams: List<Team>, private val onTeamClick: (Int) -> Unit) :
    RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val teamLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_item, parent, false)
        return TeamViewHolder(teamLayout)
    }

    override fun getItemCount(): Int =
        teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teams[position])
    }

    inner class TeamViewHolder(teamItemView: View) : RecyclerView.ViewHolder(teamItemView) {
        fun bind(team: Team) {
            itemView.findViewById<TextView>(R.id.team_name).text = team.name
            itemView.setOnClickListener{onTeamClick(team.id)}
        }
    }
}