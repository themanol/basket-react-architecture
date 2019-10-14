package com.themanol.reactbasket.players.presentation.di

import com.themanol.reactbasket.players.di.dataSourceModule
import com.themanol.reactbasket.players.di.repositoryModule
import com.themanol.reactbasket.players.di.serviceModule
import com.themanol.reactbasket.players.presentation.viewmodel.PlayerDetailsViewModel
import com.themanol.reactbasket.players.presentation.viewmodel.PlayersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    val modules = listOf(
        repositoryModule,
        dataSourceModule,
        serviceModule,
        viewModelModule
    )
    loadKoinModules(modules)
}

val viewModelModule: Module = module {
    viewModel { PlayersViewModel(repo = get()) }
    viewModel { (id: Int) -> PlayerDetailsViewModel(id = id, repo = get()) }
}
