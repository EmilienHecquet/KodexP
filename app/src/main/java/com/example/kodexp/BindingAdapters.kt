package com.example.kodexp

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

@BindingAdapter("app:visibility")
fun setViewVisibility(view: View, isVisible: LiveData<Boolean>?) {
    isVisible?.value?.let {
        view.visibility = if (it) View.VISIBLE else View.GONE
    }
}