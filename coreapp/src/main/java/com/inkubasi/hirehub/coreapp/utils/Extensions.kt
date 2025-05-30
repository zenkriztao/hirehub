package com.inkubasi.hirehub.coreapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    try {
        (context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager).hideSoftInputFromWindow(windowToken, 0)
    } catch (e: ClassCastException) {
        e.printStackTrace()
    }
}