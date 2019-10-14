package com.themanol.reactbasket.players.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.themanol.players.presentation.R
import com.themanol.reactbasket.domain.Player
import com.themanol.reactbasket.players.presentation.viewmodel.PlayerDetailsViewModel
import com.themanol.reactbasket.views.BaseFragment
import kotlinx.android.synthetic.main.player_details_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlayerDetailsFragment : BaseFragment() {
    private val args: PlayerDetailsFragmentArgs by navArgs()
    private val vm: PlayerDetailsViewModel by viewModel { parametersOf(args.playerId) }
    override val progressIndicator: View?
        get() = progress_circular

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.player_details_fragment,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.playerDetailsLiveData.observe(this, Observer { player ->
            fillView(player)
        })

        vm.errorLiveData.observe(this, Observer { error ->
            showError(error.message)
        })

        vm.progressLiveData.observe(this, Observer { show ->
            showProgressBar(show)
        })

        vm.onStart()
    }

    private fun fillView(player: Player) {
        player_name.text = "${player.firstName} ${player.lastName}"
        player_team.text = player.team.fullName
        player_position.text = player.position
        player_height.text = if (player.heightFeet != 0) {
            "${player.heightFeet}' ${player.heightInches}\""
        } else {
            "not specified"
        }
        player_weight.text = if (player.weightPounds != 0) {
            "${player.weightPounds} pounds"
        } else {
            "not specified"
        }
    }
}