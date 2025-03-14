package com.example.sampleapp.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun View.show() {
    this.visibility = View.VISIBLE
}
fun View.hide() {
    this.visibility = View.GONE
}
fun View.invisible() {
    this.visibility = View.INVISIBLE
}
fun View.onSingleClick(hapticEnabled: Boolean = true, callback: () -> Unit) {
    setOnClickListener {
        if (ClickUtils.isSingleClick()) {
            if (hapticEnabled) {
                performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }
            /** enables haptic */
            callback.invoke()
        }

    }
}
fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
fun Activity.hideKeyboard() {
    val view = currentFocus ?: View(this)
    view.hideKeyboard()
}
fun EditText.addDebouncedTextWatcher(
    debounceTime: Long = 300L,
    coroutineScope: CoroutineScope,
    onDebouncedTextChanged: (String) -> Unit
) {
    var job: Job? = null

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            job?.cancel()
            job = coroutineScope.launch {
                delay(debounceTime)
                onDebouncedTextChanged(s.toString())
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}