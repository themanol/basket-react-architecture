package com.themanol.reactbasket.teams.presentation.di

import com.themanol.reactbasket.teams.presentation.viewmodel.TeamsViewModel
import com.themanol.teams.di.dataSourceModule
import com.themanol.teams.di.serviceModule
import com.themanol.teams.di.repositoryModule
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
        serviceModule,
        viewModelModule
    )
    unloadKoinModules(modules)
    loadKoinModules(modules)
}

val viewModelModule: Module = module {
    viewModel { TeamsViewModel(repo = get()) }
}
