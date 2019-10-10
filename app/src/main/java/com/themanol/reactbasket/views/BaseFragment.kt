package com.themanol.reactbasket.views

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.themanol.reactbasket.R

open class BaseFragment : Fragment() {

    open protected val progressIndicator: View? = null

    fun showProgressBar(show: Boolean) {
        progressIndicator?.apply {
            visibility = if (show) {
                VISIBLE
            } else {
                GONE
            }
        }
    }

    fun showError(message: String?) {
        Toast.makeText(context, message ?: getString(R.string.default_error), Toast.LENGTH_LONG)
            .show()
    }

}