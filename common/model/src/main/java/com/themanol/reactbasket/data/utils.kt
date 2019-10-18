package com.themanol.reactbasket.data

import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.domain.ResultState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@UseExperimental(FlowPreview::class)
@ExperimentalCoroutinesApi
fun <T> ConflatedBroadcastChannel<Result<T>>.init(initial: () -> Unit): Flow<Result<T>> {
    valueOrNull?.let {
        val status = it.status
        if (status == ResultState.ERROR) {
            initial()
        }
    }
    return this.asFlow()
}