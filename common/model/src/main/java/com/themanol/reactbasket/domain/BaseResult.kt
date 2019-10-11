package com.themanol.reactbasket.domain


data class Result<T>(val data: T? = null, val status: ResultState, val error: ResultError? = null) {

    companion object {
        fun <T> loading(): Result<T> {
            return Result(status = ResultState.IN_PROGRESS)
        }

        fun <T> success(data: T): Result<T> {
            return Result(data, ResultState.SUCCESS)
        }

        fun <T> error(message: String): Result<T> {
            return Result(status = ResultState.ERROR, error = ResultError(message))
        }
    }
}

data class ResultError(val message: String)

enum class ResultState {
    SUCCESS, IN_PROGRESS, ERROR
}