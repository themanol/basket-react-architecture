package com.themanol.reactbasket.games.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.themanol.reactbasket.games.presentation.R
import com.themanol.reactbasket.games.presentation.di.injectFeature
import com.themanol.reactbasket.games.presentation.viewmodel.GamesViewModel
import com.themanol.reactbasket.views.Navigator
import com.themanol.reactbasket.views.TeamsRoute
import kotlinx.android.synthetic.main.game_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class GamesFragment : Fragment(){

    private val vm: GamesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFeature()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.gameListLiveData.observe(this, Observer { list ->
            Log.d("Themanol", "The list of games size is ${list.size}")
        })

        button.setOnClickListener{
            Navigator.navigateTo(TeamsRoute)
        }
    }

}