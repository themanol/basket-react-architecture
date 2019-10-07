package com.themanol.reactbasket.games.presentation.di

import com.themanol.reactbasket.games.di.dataSourceModule
import com.themanol.reactbasket.games.di.networkModule
import com.themanol.reactbasket.games.di.repositoryModule
import com.themanol.reactbasket.games.presentation.viewmodel.GamesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    val modules =  listOf(
        repositoryModule,
        dataSourceModule,
        networkModule,
        viewModelModule
    )
    unloadKoinModules(modules)
    loadKoinModules(modules)
}

val viewModelModule: Module = module {
    viewModel { GamesViewModel(repo = get()) }
}
