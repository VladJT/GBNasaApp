package jt.projects.gbnasaapp.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors
import jt.projects.gbnasaapp.R

class PaintView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    val cellSize = 30

    override fun onDraw(canvas: Canvas) {
        val mPaint = Paint()
        mPaint.color = MaterialColors.getColor(this, com.google.android.material.R.attr.colorPrimary)
        mPaint.strokeWidth = 1f

        for (y in 0..height step cellSize) {
            canvas.drawLine(0f, y.toFloat(), width.toFloat(),  y.toFloat(), mPaint)
        }

        for (x in 0..width step cellSize) {
            canvas.drawLine(x.toFloat(), 0f, x.toFloat(), height.toFloat(), mPaint)
        }
    }
}