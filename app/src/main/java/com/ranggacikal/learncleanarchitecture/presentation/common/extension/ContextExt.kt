package com.ranggacikal.learncleanarchitecture.presentation.common.extension

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.ranggacikal.learncleanarchitecture.presentation.utils.Constants

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showGenericAlertDialog(message: String){
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton(Constants.TEXT_BUTTON_OK){ dialog, _ ->
            dialog.dismiss()
        }
    }.show()
}