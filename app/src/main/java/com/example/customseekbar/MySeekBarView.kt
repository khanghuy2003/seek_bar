package com.example.customseekbar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.scale
import androidx.core.graphics.toColorInt
import java.util.Locale
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt


class MySeekBarView : View {

    interface WhenSeekBarChangeListener {
        fun onSeekBarChange(progress : Float)
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private var listener : WhenSeekBarChangeListener? = null

    private var sizeThumb: Float = convertDpToPx(30f)
    private var bitmapThumb: Bitmap? = null

    private var widthView: Float = 0f
    private var heightView: Float = 0f
    private var progress: Float = 60f
    private var isSeeking = false
    private var seekbarHeight = 0f

    private var startX = 0f // start Background
    private var startY = 0f

    private var stopX1 = 0f // end background
    private var stopY1 = 0f

    private var stopX2 = 0f // end progress
    private var stopY2 = 0f

    private var limitMinY = 0f
    private var limitMaxY = 0f

    private val PADDING_VERTICAL = 30f

    private val paintBackground = Paint().apply {
        color = "#313138".toColorInt()
        style = Paint.Style.FILL_AND_STROKE
        isAntiAlias = true
        strokeWidth = convertDpToPx(6f)
        strokeCap = Paint.Cap.ROUND
    }

    private val paintProgress = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        isAntiAlias = true
        strokeWidth = convertDpToPx(6f)
        strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widthView = w.toFloat()
        heightView = h.toFloat()
        val bitmapImage = BitmapFactory.decodeResource(resources, R.drawable.thumb)
        bitmapThumb = bitmapImage.scale(sizeThumb.toInt(), sizeThumb.toInt(), false)

        startX = widthView / 2f
        startY = heightView - PADDING_VERTICAL

        stopX1 = widthView / 2f
        stopY1 = PADDING_VERTICAL

        stopX2 = widthView / 2f
        stopY2 = heightView - PADDING_VERTICAL - ((progress / 100f) * (heightView - 2 * PADDING_VERTICAL))

        seekbarHeight = startY - stopY1

        paintProgress.shader = LinearGradient(
            startX,
            startY,
            stopX1,
            stopY1,
            "#6A48FF".toColorInt(),
            "#AC6DE3".toColorInt(),
            Shader.TileMode.CLAMP
        )

        limitMinY = PADDING_VERTICAL
        limitMaxY = heightView - PADDING_VERTICAL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawLine(
            startX,
            startY,
            stopX1,
            stopY1,
            paintBackground
        )

        canvas.drawLine(
            startX,
            startY,
            stopX2,
            stopY2,
            paintProgress
        )

        bitmapThumb?.let {
            canvas.drawBitmap(
                it,
                stopX2 - sizeThumb / 2f,
                stopY2 - sizeThumb / 2f,
                null
            )
        }
    }

    private var touchX = 0f
    private var touchY = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                touchX = event.x
                touchY = event.y
                stopY2 = touchY.coerceIn(limitMinY, limitMaxY)
//                isSeeking = checkIsSeeking(event)
                progress = ((startY - stopY2).coerceAtLeast(0f) / seekbarHeight) * 100f
                listener?.onSeekBarChange(progress)
                invalidate()
                true
            }

            MotionEvent.ACTION_MOVE -> {
//                if (isSeeking) {
                    stopY2 = event.y.coerceIn(limitMinY, limitMaxY)
//                }
                progress = ((startY - stopY2).coerceAtLeast(0f) / seekbarHeight) * 100f
                listener?.onSeekBarChange(progress)
                invalidate()
                true
            }

            MotionEvent.ACTION_UP -> {
                touchX = 0f
                touchY = 0f
                isSeeking = false
                true
            }

            else -> {
                false
            }
        }
    }

    private fun checkIsSeeking(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        val distance =
            sqrt((touchX - stopX2).toDouble().pow(2.0) + (touchY - stopY2).toDouble().pow(2.0))
        return distance.toFloat() <= sizeThumb / 2f
    }

    fun setSeekbarListener(listener : WhenSeekBarChangeListener) {
        this.listener = listener
    }

    private fun getProgress(): Float {
        return this.progress
    }

    private fun setProgress(progress: Float) {
        this.progress = progress
        stopY2 = heightView - PADDING_VERTICAL - ((progress / 100f) * (heightView - 2 * PADDING_VERTICAL))
        listener?.onSeekBarChange(progress)
        invalidate()
    }

    private fun convertDpToPx(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    }

    private fun getAngleBABC(
        ax: Double, ay: Double,
        bx: Double, by: Double,
        cx: Double, cy: Double
    ): Double? {

        val bax = ax - bx
        val bay = ay - by
        val bcx = cx - bx
        val bcy = cy - by

        val dot = bax * bcx + bay * bcy

        val lenBA = sqrt(bax * bax + bay * bay)
        val lenBC = sqrt(bcx * bcx + bcy * bcy)

        if (lenBA == 0.0 || lenBC == 0.0) return null

        val cosTheta = (dot / (lenBA * lenBC)).coerceIn(-1.0, 1.0)

        val angle = acos(cosTheta)
        return Math.toDegrees(angle)
    }
}