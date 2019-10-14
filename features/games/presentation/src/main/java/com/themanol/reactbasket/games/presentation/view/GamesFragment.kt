package com.themanol.reactbasket.games.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private val adapter: GamesAdapter by lazy {
        GamesAdapter { teamId ->
            Navigator.navigateTo(
                TeamDetailsRoute,
                bundleOf("teamId" to teamId)
            )
        }
    }

    private var onScrollListener : RecyclerView.OnScrollListener? = null

    private fun scrollListener(onScroll: () -> Unit) =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                        val visibleItemCount = it.childCount;
                        val totalItemCount = it.getItemCount();
                        val pastVisibleItems = it.findLastVisibleItemPosition()

                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            onScroll()
                        }
                    }
                }
            }
        }


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
        games_recyclerView.adapter = adapter
        games_recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        vm.gameListLiveData.observe(this, Observer { list ->
            adapter.submitList(list)
        })

        vm.errorLiveData.observe(this, Observer { error ->
            showError(error.message)
        })

        vm.progressLiveData.observe(this, Observer { show ->
            showProgressBar(show)
        })

        vm.loadingMoreLiveData.observe(this, Observer { show ->
            adapter.loading = show
            adapter.notifyItemChanged(adapter.itemCount)
        })

        vm.onScrollEndLiveData.observe(this, Observer { onScroll ->
            onScrollListener?.let {
                games_recyclerView.removeOnScrollListener(it)
            }
           onScrollListener = scrollListener(onScroll = onScroll).apply {
               games_recyclerView.addOnScrollListener(
                   this
               )
           }
        })
        vm.onStart()
    }

}