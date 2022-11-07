package jt.projects.gbnasaapp.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

fun <T> getUniString(context: Context, text: T): String {
    return ((text as? Int)?.let { context.resources.getText(text as Int) } ?: text) as String
}


fun String.toDecoratedDescription(): SpannableString {
    return SpannableString(this).apply {
        for (i in this.indices) {
            if (this[i] == '?') {
                setSpan(
                    ForegroundColorSpan(Color.RED),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            if (this[i] in 'A'..'Z') {
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            if (this[i] in '0'..'9') {
                setSpan(
                    ForegroundColorSpan(Color.BLUE),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }
}

fun String.toDecoratedSign(): SpannableString {
    return SpannableString(this).apply {
        setSpan(
            BackgroundColorSpan(Color.rgb(0, 153, 200)),
            0,
            length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}