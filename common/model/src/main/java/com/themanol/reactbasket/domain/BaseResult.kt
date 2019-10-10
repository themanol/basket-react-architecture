package com.themanol.reactbasket.domain


data class Result<T>(val data: T?, val status: ResultState) {

    companion object {
        fun <T> loading(): Result<T> {
            return Result(null, ResultState.IN_PROGRESS)
        }

        fun <T> success(data: T): Result<T> {
            return Result(data, ResultState.SUCCESS)
        }

        fun <T> error(): Result<T> {
            return Result(null, ResultState.ERROR)
        }
    }
}

enum class ResultState {
    SUCCESS, IN_PROGRESS, ERROR
}