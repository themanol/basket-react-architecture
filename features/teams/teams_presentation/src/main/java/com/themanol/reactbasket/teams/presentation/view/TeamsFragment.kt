package com.themanol.reactbasket.teams.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.themanol.reactbasket.teams.presentation.di.injectFeature
import com.themanol.reactbasket.teams.presentation.view.adapter.TeamsAdapter
import com.themanol.reactbasket.teams.presentation.viewmodel.TeamsViewModel
import kotlinx.android.synthetic.main.team_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class TeamsFragment : Fragment() {

    private val vm: TeamsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            com.themanol.reactbasket.teams.presentation.R.layout.team_fragment,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectFeature()
        teams_recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        vm.teamListLiveData.observe(this, Observer { list ->
            Log.d("Themanol", "The list size is ${list.size}")
            val adapter = TeamsAdapter(list) { teamId ->
                findNavController()
                    .navigate(TeamsFragmentDirections.actionInTeamsToTeamDetails(teamId))
            }
            teams_recyclerView.adapter = adapter
        })
    }

}