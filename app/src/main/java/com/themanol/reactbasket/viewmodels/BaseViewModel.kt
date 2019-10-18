package com.themanol.reactbasket.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.themanol.reactbasket.domain.ResultError
import com.themanol.reactbasket.utils.SingleLiveEvent

open class BaseViewModel : ViewModel() {
    protected val mErrorLiveData = SingleLiveEvent<ResultError>()
    val errorLiveData: LiveData<ResultError> = mErrorLiveData

}