package com.themanol.reactbasket.teams.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.themanol.reactbasket.teams.presentation.R
import com.themanol.reactbasket.teams.presentation.di.injectFeature
import com.themanol.reactbasket.teams.presentation.view.adapter.TeamsAdapter
import com.themanol.reactbasket.teams.presentation.viewmodel.TeamsViewModel
import com.themanol.reactbasket.views.BaseFragment
import kotlinx.android.synthetic.main.team_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamsFragment : BaseFragment() {

    private val vm: TeamsViewModel by viewModel()
    override val progressIndicator: View?
        get() = progress_circular

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.team_fragment,
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
        vm.errorLiveData.observe(this, Observer { error ->
            showError(error.message)
        })

        vm.progressLiveData.observe(this, Observer { show ->
            showProgressBar(show)
        })
    }

}