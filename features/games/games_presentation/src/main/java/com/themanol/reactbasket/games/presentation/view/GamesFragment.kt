package com.themanol.reactbasket.games.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.themanol.reactbasket.games.presentation.R
import com.themanol.reactbasket.games.presentation.di.injectFeature
import com.themanol.reactbasket.games.presentation.view.adapter.GamesAdapter
import com.themanol.reactbasket.games.presentation.viewmodel.GamesViewModel
import com.themanol.reactbasket.navigation.Navigator
import com.themanol.reactbasket.navigation.TeamDetailsRoute
import com.themanol.reactbasket.views.BaseFragment
import kotlinx.android.synthetic.main.game_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class GamesFragment : BaseFragment() {
    private val args: GamesFragmentArgs by navArgs()
    private val vm: GamesViewModel by viewModel { parametersOf(args.teamId) }
    override val progressIndicator: View?
        get() = progress_circular

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
        games_recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        vm.gameListLiveData.observe(this, Observer { list ->
            Log.d("TheManol", "The list of games size is ${list.size}")
            val adapter = GamesAdapter(list) { teamId ->
                Navigator.navigateTo(TeamDetailsRoute, bundleOf("teamId" to teamId))
            }
            games_recyclerView.adapter = adapter
        })

        vm.errorLiveData.observe(this, Observer { error ->
            showError(error.message)
        })

        vm.progressLiveData.observe(this, Observer { show ->
            showProgressBar(show)
        })

        vm.onStart()
    }

}