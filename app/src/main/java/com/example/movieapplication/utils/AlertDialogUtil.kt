package com.example.movieapplication.utils

import android.app.AlertDialog
import android.content.Context
import com.example.movieapplication.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object AlertDialogUtil {
    fun questionDialog(
        context: Context,
        title: Int,
        message: Int,
        positiveText: Int? = R.string.button_accept,
        negativeText: Int? = R.string.cancel,
        callback: () -> Unit
    ) {
        val materialAlertDialog = MaterialAlertDialogBuilder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveText!!, null)
            setNegativeButton(negativeText!!) { dialog, _ ->
                dialog.dismiss()
            }
        }.create()

        materialAlertDialog.setOnShowListener {
            materialAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                materialAlertDialog.dismiss()
                callback()
            }
        }

        materialAlertDialog.show()
    }

    fun questionDialogCancel(
        context: Context,
        title: Int,
        message: Int,
        positiveText: Int? = R.string.button_accept,
        negativeText: Int? = R.string.cancel,
        positiveCallback: () -> Unit,
        negativeCallback: () -> Unit,
    ) {
        val materialAlertDialog = MaterialAlertDialogBuilder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveText!!, null)
            setNegativeButton(negativeText!!) { dialog, _ ->
                dialog.dismiss()
            }
        }.create()

        materialAlertDialog.setOnShowListener {
            materialAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                materialAlertDialog.dismiss()
                positiveCallback()
            }
            materialAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                materialAlertDialog.dismiss()
                negativeCallback()
            }
        }

        materialAlertDialog.show()
    }


    fun confirmationDialog(
        context: Context,
        title: Int,
        message: Any,
        positiveText: Int? = R.string.button_accept
    ) {
        val materialAlertDialog = MaterialAlertDialogBuilder(context).apply {
            setTitle(title)
            if(message is String) setMessage(message)
            if(message is Int) setMessage(message)
            setPositiveButton(positiveText!!, null)

        }.create()
        materialAlertDialog.show()
    }
}