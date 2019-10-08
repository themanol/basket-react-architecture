package com.themanol.reactbasket.teams.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.themanol.reactbasket.domain.Team
import com.themanol.reactbasket.teams.presentation.di.injectFeature
import com.themanol.reactbasket.teams.presentation.viewmodel.TeamDetailsViewModel
import com.themanol.reactbasket.navigation.GamesRoute
import com.themanol.reactbasket.navigation.Navigator
import kotlinx.android.synthetic.main.team_details_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TeamDetailsFragment : Fragment() {
    val args: TeamDetailsFragmentArgs by navArgs()
    private val vm: TeamDetailsViewModel by viewModel { parametersOf(args.teamId ) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            com.themanol.reactbasket.teams.presentation.R.layout.team_details_fragment,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectFeature()

        vm.teamDetailsLiveData.observe(this, Observer { team ->
            Log.d("Themanol", "The team is ${team.fullName}")
            fillView(team)
        })
    }

    private fun fillView(team: Team) {
        view_games_button.setOnClickListener{
            Navigator.navigateTo(GamesRoute, bundleOf("teamId" to team.id))
        }
    }

}