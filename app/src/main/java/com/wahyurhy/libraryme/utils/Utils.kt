package com.wahyurhy.libraryme.utils

import android.app.Activity
import android.view.WindowManager
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText

object Utils {
    fun setSystemBarFitWindow(activity: Activity) {
        activity.apply {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    fun String.isEmpty(binding: TextInputEditText, errorMessage: String): Boolean {
        return if (this.isEmpty()) {
            binding.error = errorMessage
            false
        } else {
            binding.error = null
            true
        }
    }
}