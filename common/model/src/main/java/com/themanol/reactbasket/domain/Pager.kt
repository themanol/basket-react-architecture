package com.themanol.reactbasket.domain

import com.themanol.reactbasket.data.PaginatedDataEntity
import io.reactivex.Single

interface Pager {

    fun <T> start(getInitial: (Int) -> Single<PaginatedDataEntity<T>>): Single<PaginatedDataEntity<T>>
    fun <T> more(getMore: (Int) -> Single<PaginatedDataEntity<T>>): Single<PaginatedDataEntity<T>>?

}