package jt.projects.gbnasaapp.utils

import android.content.Context

fun <T> getUniString(context: Context, text: T): String {
    return ((text as? Int)?.let { context.resources.getText(text as Int) } ?: text) as String
}
