package com.vmoreno.butterfly.rick.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vmoreno.butterfly.rick.domain.utils.EMPTY_STRING

fun View.hideKeyboard() {
    context.hideKeyboard(this)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun View.setSingleClickListener(action: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(action))
}

fun List<String>.listStringToString(): String {
    return if (this.isEmpty()) EMPTY_STRING
    else this.toString().replace("[", "").replace("]", "")
}

fun List<Int>.listIntToString(): String {
    return if (this.isEmpty()) EMPTY_STRING
    else this.toString().replace("[", "").replace("]", "")
}

fun String.stringToListString(): List<String> {
    return if (this.isEmpty()) emptyList()
    else this.split(",").map { it.trim() }
}

fun String.stringToListInt(): List<Int> {
    return if (this.isEmpty()) emptyList()
    else this.split(",").map { it.trim().toInt() }
}