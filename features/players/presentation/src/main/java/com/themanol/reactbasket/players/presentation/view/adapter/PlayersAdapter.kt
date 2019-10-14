package com.themanol.reactbasket.players.presentation.view.adapter

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
import com.themanol.players.presentation.R
import com.themanol.reactbasket.domain.Player

private const val LOADER_HOLDER: Int = 1
private const val PLAYER_HOLDER: Int = 2

class PlayersAdapter(var loading: Boolean = false, private val onTeamClick: (Int) -> Unit) :
    ListAdapter<Player, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == PLAYER_HOLDER) {
            val teamLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.player_item, parent, false)
            PlayerViewHolder(
                teamLayout,
                onTeamClick
            )
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
            PLAYER_HOLDER
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PlayerViewHolder) {
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

    class PlayerViewHolder(
        playerItemView: View,
        private val onPlayerClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(playerItemView) {

        private val playerName: TextView = itemView.findViewById(R.id.playerName)
        private val teamName: TextView = itemView.findViewById(R.id.teamName)

        fun bind(player: Player) {
            itemView.setOnClickListener { onPlayerClick(player.id) }
            playerName.text = "${player.firstName} ${player.lastName}"
            teamName.text = player.team.name
        }
    }

}

private object DiffCallback : DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean = oldItem == newItem
}