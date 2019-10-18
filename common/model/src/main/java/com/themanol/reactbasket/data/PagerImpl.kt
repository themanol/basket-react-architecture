package com.themanol.reactbasket.data

import com.themanol.reactbasket.domain.Pager

class PagerImpl : Pager {

    var currentPage = 1
    var nextPage: Int? = null
    var totalPages = 0
    var lastPageLoaded = 1

    override suspend fun <T> start(
        getInitial: suspend (Int) -> PaginatedDataEntity<T>
    ): PaginatedDataEntity<T> {
        currentPage = 1
        nextPage = null
        lastPageLoaded = 1
        val result = getInitial(currentPage)
        currentPage = result.meta.currentPage
        nextPage = result.meta.nextPage
        totalPages = result.meta.totalPages
        return result
    }

    override suspend fun <T> more(
        getMore: suspend (Int) -> PaginatedDataEntity<T>
    ): PaginatedDataEntity<T>? {
        return nextPage?.let { next ->
            if (next != lastPageLoaded) {
                lastPageLoaded = next
                try {
                    val result = getMore(next)
                    currentPage = result.meta.currentPage
                    nextPage = result.meta.nextPage
                    return result
                } catch (e: Exception) {
                    lastPageLoaded = currentPage
                    throw e
                }
            }
            return null
        }
    }

}