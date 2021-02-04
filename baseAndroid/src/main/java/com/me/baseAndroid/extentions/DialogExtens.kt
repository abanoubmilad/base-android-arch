/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 2/4/21 6:15 AM
 *  * Last modified 2/4/21 6:15 AM
 *
 */

package com.me.baseAndroid.extentions

import androidx.annotation.ArrayRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.me.baseAndroid.R


fun FragmentActivity.showDialog(
    title: String? = null,
    items: Array<String>,
    onItemSelected: (Int) -> Unit
) {
    var alertDialog: AlertDialog? = null
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder.setItems(items) { dialog, which ->
        onItemSelected(which)
        dialog.dismiss()
    }
    if (title != null)
        builder.setTitle(title)
    alertDialog = builder.create()
    alertDialog.show()
}


fun FragmentActivity.showDialogSingleChoice(
    title: String? = null,
    @ArrayRes itemsArrayResourceId: Int,
    selectedIndex: Int = -1,
    onItemSelected: (Int) -> Unit
) {
    showDialogSingleChoice(
        title,
        resources.getStringArray(itemsArrayResourceId),
        selectedIndex,
        onItemSelected
    )
}

fun FragmentActivity.showDialogSingleChoice(
    title: String? = null,
    items: Array<String>,
    selectedIndex: Int = -1,
    onItemSelected: (Int) -> Unit
) {
    var newIndex = -1
    val builder: AlertDialog.Builder = AlertDialog.Builder(this).apply {

        setSingleChoiceItems(items, selectedIndex) { _, which -> newIndex = which }
        setPositiveButton(R.string.base_arch_module_dialog_positive_button) { dialog, _ ->
            dialog.dismiss()
            if (newIndex >= 0 && newIndex < items.size) {
                onItemSelected(newIndex)
            }
        }
        setNegativeButton(R.string.base_arch_module_dialog_negative_button) { dialog, _ ->
            dialog.dismiss()
        }

        if (title != null)
            setTitle(title)
    }
    builder.create().show()
}