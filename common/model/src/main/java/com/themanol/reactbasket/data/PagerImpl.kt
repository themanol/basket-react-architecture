package com.themanol.reactbasket.data

import com.themanol.reactbasket.domain.Pager
import io.reactivex.Single

class PagerImpl : Pager {

    var currentPage = 1
    var nextPage: Int? = null
    var totalPages = 0
    var lastPageLoaded = 1

    override fun <T> start(
        getInitial: (Int) -> Single<PaginatedDataEntity<T>>
    ): Single<PaginatedDataEntity<T>> {
        currentPage = 1
        nextPage = null
        lastPageLoaded = 1
        return getInitial(currentPage).doOnSuccess {
            currentPage = it.meta.currentPage
            nextPage = it.meta.nextPage
            totalPages = it.meta.totalPages
        }
    }

    override fun <T> more(
        getMore: (Int) -> Single<PaginatedDataEntity<T>>
    ): Single<PaginatedDataEntity<T>>? {
        return nextPage?.let { next ->
            if (next != lastPageLoaded) {
                lastPageLoaded = next
                getMore(next).doOnSuccess {
                    currentPage = it.meta.currentPage
                    nextPage = it.meta.nextPage
                }.doOnError {
                    lastPageLoaded = currentPage
                }
            } else {
                null
            }
        }
    }

}