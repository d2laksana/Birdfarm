package com.d2l.birdfarm.utils

import android.content.Context
import android.widget.Toast

class ToastMSG {

    private fun showToast(msg: String, context: Context){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun SuccessMSG(msg: String, context: Context) {
        showToast(msg, context)
    }

    fun ErrorMSG(msg: String?, context: Context) {
        showToast("Error:$msg", context)
    }
}