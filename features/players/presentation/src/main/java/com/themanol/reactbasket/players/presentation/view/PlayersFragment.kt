package com.themanol.reactbasket.players.presentation.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
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
    private lateinit var searchView: SearchView
    private val adapter: PlayersAdapter by lazy {
        PlayersAdapter { playerId ->
            findNavController()
                .navigate(PlayersFragmentDirections.actionInPlayersToPlayersDetails(playerId))
        }
    }

    private var onScrollListener: RecyclerView.OnScrollListener? = null

    private fun scrollListener(onScroll: (String) -> Unit) =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                        val visibleItemCount = it.childCount;
                        val totalItemCount = it.getItemCount();
                        val pastVisibleItems = it.findLastVisibleItemPosition()

                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            onScroll(searchView.query.toString())
                        }
                    }
                }
            }
        }

    private fun onQueryChangeListener(onQueryChange: (String) -> Unit) =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                onQueryChange(newText)
                return true
            }
        }

    private fun onActionExpandListener(onClose: () -> Unit) =
        object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                onClose()
                return true
            }

        }

    override val progressIndicator: View?
        get() = progress_circular

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
        injectFeature()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            com.themanol.players.presentation.R.layout.players_fragment,
            container,
            false
        )
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        menu.findItem(R.id.search).apply {
            searchView = (actionView as SearchView).apply {
                queryHint = getString(R.string.search_hint)
                vm.onQueryChangeLiveData.observe(this@PlayersFragment, Observer {
                    setOnQueryTextListener(onQueryChangeListener(it))
                })
            }
            vm.onCloseSearchLiveData.observe(this@PlayersFragment, Observer {
                setOnActionExpandListener(onActionExpandListener(it))
            })
            if (vm.lastSearch.isNotEmpty() && !isActionViewExpanded) {
                expandActionView()
                searchView.setQuery(vm.lastSearch, true)
            }

        }
        super.onCreateOptionsMenu(menu, inflater)
    }

}