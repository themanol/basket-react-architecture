package com.themanol.reactbasket.players.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.themanol.players.presentation.R
import com.themanol.reactbasket.players.presentation.di.injectFeature
import com.themanol.reactbasket.players.presentation.view.adapter.PlayersAdapter
import com.themanol.reactbasket.players.presentation.viewmodel.PlayersViewModel
import com.themanol.reactbasket.views.BaseFragment
import kotlinx.android.synthetic.main.players_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayersFragment : BaseFragment() {
    private val vm: PlayersViewModel by viewModel()
    private val adapter: PlayersAdapter by lazy {
        PlayersAdapter { playerId ->
            findNavController()
                .navigate(PlayersFragmentDirections.actionInPlayersToPlayersDetails(playerId))
        }
    }

    private var onScrollListener: RecyclerView.OnScrollListener? = null

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
        return inflater.inflate(R.layout.players_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        players_recyclerView.adapter = adapter
        players_recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        vm.playersListLiveData.observe(this, Observer { list ->
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
                players_recyclerView.removeOnScrollListener(it)
            }
            onScrollListener = scrollListener(onScroll = onScroll).apply {
                players_recyclerView.addOnScrollListener(
                    this
                )
            }
        })
    }

}