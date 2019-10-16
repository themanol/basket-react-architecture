package com.themanol.reactbasket.data

import com.themanol.reactbasket.domain.Result
import com.themanol.reactbasket.domain.ResultState
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

fun <T> BehaviorSubject<Result<T>>.init(initial: () -> Unit): Observable<Result<T>> {
    return this.hide().doOnSubscribe {
        if (hasValue()) {
            val status = value?.status
            if (status == ResultState.ERROR) {
                initial()
            }
        }
    }
}