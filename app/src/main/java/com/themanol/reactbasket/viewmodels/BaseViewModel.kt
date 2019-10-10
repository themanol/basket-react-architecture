package com.themanol.reactbasket.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    protected val mErrorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> = mErrorLiveData

    protected val disposables = CompositeDisposable()


    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}