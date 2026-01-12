package com.example.firstapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.withRotation

class DrawClock(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var widthClock: Float = 300f
    private var isDraggingClock = false
    private var dx = 0f
    private var dy = 0f
    private var cx = 0f
    private var cy = 0f
    private var radius = 0f
    private val clockBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        color = Color.BLACK
    }
    private val clockNumberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        textAlign = Paint.Align.CENTER
        textSize = widthClock / 10f
    }
    private val handPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        attrs?.let {
            context.withStyledAttributes(
                it,
                R.styleable.DrawClock
            ) {

                widthClock = getDimension(
                    R.styleable.DrawClock_widthClock,
                    300f
                )

            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (cx == 0f && cy == 0f) {
            cx = w / 2f
            cy = h / 2f
            radius = widthClock / 2f
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(cx, cy, radius, clockBorderPaint)
        drawFaceClock(cx, cy, canvas, radius)

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        drawHand(canvas, cx, cy, (hour + minute / 60f) * 30f, radius * 0.5f, 12f, Color.BLACK)
        drawHand(canvas, cx, cy, (minute + second / 60f) * 6f, radius * 0.7f, 8f, Color.BLACK)
        drawHand(canvas, cx, cy, second * 6f, radius * 0.9f, 4f, Color.RED)

        postInvalidateDelayed(1000)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isTouchInsideClock(event.x, event.y, cx, cy, radius)) {
                    isDraggingClock = true
                    dx = event.x - cx
                    dy = event.y - cy

                    return true
                }
                return false
            }

            MotionEvent.ACTION_MOVE -> {
                if (isDraggingClock) {
                    cx = event.x - dx
                    cy = event.y - dy
                    invalidate()
                }
            }

            MotionEvent.ACTION_CANCEL -> {
                isDraggingClock = false
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun drawFaceClock(
        cx: Float,
        cy: Float,
        canvas: Canvas,
        radius: Float
    ) {
        for (i in 1..12) {
            val angle = Math.toRadians((i * 30 - 90).toDouble())
            val x = cx + cos(angle).toFloat() * radius * 0.8f
            val y = cy + sin(angle).toFloat() * radius * 0.8f + clockNumberPaint.textSize / 3
            canvas.drawText(i.toString(), x, y, clockNumberPaint)
        }
    }

    private fun drawHand(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        angle: Float,
        length: Float,
        stroke: Float,
        color: Int
    ) {
        handPaint.color = color
        handPaint.strokeWidth = stroke

        canvas.withRotation(angle, cx, cy) {
            drawLine(
                cx,
                cy,
                cx,
                cy - length,
                handPaint
            )
        }
    }

    private fun isTouchInsideClock(
        touchX: Float,
        touchY: Float,
        centerX: Float,
        centerY: Float,
        radius: Float
    ): Boolean {
        val horizontal = touchX - centerX
        val vertical = touchY - centerY
        val distance = kotlin.math.sqrt(horizontal * horizontal + vertical * vertical)
        return distance <= radius
    }
}