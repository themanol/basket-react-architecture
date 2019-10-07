package com.themanol.reactbasket.teams.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.themanol.reactbasket.teams.presentation.R
import com.themanol.reactbasket.teams.presentation.di.injectFeature
import com.themanol.reactbasket.teams.presentation.viewmodel.TeamsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TeamsFragment : Fragment(){

    private val vm: TeamsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectFeature()
        vm.teamListLiveData.observe(this, Observer { list ->
            Log.d("Themanol", "The list size is ${list.size}")
        })
    }

}