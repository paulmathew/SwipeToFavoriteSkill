package com.example.swipetofavorite.utils

import android.app.Dialog
import android.content.Context
import com.example.swipetofavorite.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogUtils {

    companion object{
        const val positiveButton = "positive"
        const val negativeButton = "negative"
        const val neutralButton = "neutral"
        private lateinit var dialogProgressAlert: Dialog // converted into public object

        fun showAlert(
            context: Context,
            title: String? = null,
            msgBody: String? = null,
            positiveButtonText: String? = null,
            negativeButtonText: String? = null,
            neutralButtonText: String? = null, onItemClick: ((buttonTag: String) -> Unit)? = null
        ) {
            val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
            builder.setTitle(title)
            builder.setCancelable(false)
            builder.setMessage(msgBody)
            builder.setNegativeButton(negativeButtonText) { dialog, which ->
                onItemClick?.invoke(negativeButton)
            }
            builder.setNeutralButton(neutralButtonText) { dialog, which ->
                onItemClick?.invoke(neutralButton)
            }
            builder.setPositiveButton(positiveButtonText) { dialog, which ->
                onItemClick?.invoke(positiveButton)
            }
            // finally, create the alert dialog and show it
            dialogProgressAlert = builder.create()
            try {
                dialogProgressAlert.show()
            } catch (e: Exception) {

            }
        }

        // added dismiss function
        fun dismissAlert() {
            try {
                dialogProgressAlert.dismiss()
            } catch (upe: Exception) {
                upe.printStackTrace()
            }
        }
    }
}