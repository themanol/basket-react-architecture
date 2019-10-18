package com.themanol.reactbasket.domain

import com.themanol.reactbasket.data.PaginatedDataEntity

interface Pager {

    suspend fun <T> start(getInitial: suspend (Int) -> PaginatedDataEntity<T>): PaginatedDataEntity<T>
    suspend fun <T> more(getMore: suspend (Int) -> PaginatedDataEntity<T>): PaginatedDataEntity<T>?

}