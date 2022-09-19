package jt.projects.gbnasaapp.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


// **** VIEW ****
// принимает текст для вывода или как строку, или как id Resources (String)
fun <T> View.showSnackBarShort(text: T) {
    Snackbar.make(this, getUniString(context, text), Snackbar.LENGTH_SHORT).show()
}

// принимает текст для вывода SnackBar или как строку, или как id Resources (String)
fun <T, R> View.showSnackBarWithAction(text: T, actionText: R, action: () -> Unit) {
    Snackbar.make(
        this,
        getUniString(context, text),
        Snackbar.LENGTH_LONG
    )// отображается неопределенное время - Snackbar.LENGTH_INDEFINITE
        .setAction(getUniString(context, text)) { action.invoke() }
        .show()
}


// **** Activity ****
fun Activity.toast(string: String?) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}


// **** Fragment ****
fun Fragment.toast(string: String?) {
    Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.BOTTOM, 0, 250)
        show()
    }
}

fun Fragment.snackBar(text: String) {
    this.view?.let { Snackbar.make(it, text, Snackbar.LENGTH_LONG).show() }
}

fun Fragment.showPictureInFullMode(src: String) {
    Intent().apply {
        action = Intent.ACTION_VIEW
        setDataAndType(Uri.parse(src), "image/*")
        requireActivity().startActivity(this)
    }
}