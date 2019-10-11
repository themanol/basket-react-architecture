package com.themanol.reactbasket.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.themanol.reactbasket.domain.ResultError
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    protected val mErrorLiveData = MutableLiveData<ResultError>()
    val errorLiveData: LiveData<ResultError> = mErrorLiveData

    protected val disposables = CompositeDisposable()


    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}