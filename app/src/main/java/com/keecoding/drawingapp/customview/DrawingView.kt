package com.keecoding.drawingapp.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context : Context, attrs : AttributeSet) : View(context, attrs) {

    private var mDrawPath : CustomPath? = null
    private var mCanvasBitmap : Bitmap? = null
    private var mDrawPaint : Paint? = null
    private var mCanvasPaint : Paint? = null
    private var mBrushSize = 0f
    private var selectedColor = Color.BLACK
    private var canvas : Canvas? = null
    private val mPaths = ArrayList<CustomPath>()

    init {
        setUpDrawing()
    }

    private fun setUpDrawing() {
        mDrawPaint = Paint()
        mDrawPath = CustomPath(selectedColor, mBrushSize)
        mDrawPaint?.apply {
            color = selectedColor
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)

        mPaths.forEach {
            mDrawPaint!!.strokeWidth = it.brushThickness
            mDrawPaint!!.color = it.color
            canvas?.drawPath(it, mDrawPaint!!)
        }

        if (!mDrawPath!!.isEmpty) {
            mDrawPaint!!.strokeWidth = mDrawPath!!.brushThickness
            mDrawPaint!!.color = mDrawPath!!.color
            canvas?.drawPath(mDrawPath!!, mDrawPaint!!)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                mDrawPath!!.apply {
                    color = selectedColor
                    brushThickness = mBrushSize
                    reset()
                    if (touchX != null && touchY != null){
                        this.moveTo(touchX, touchY)
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                mDrawPath!!.apply {
                    if (touchX != null && touchY != null) {
                        this.lineTo(touchX, touchY)
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                mPaths.add(mDrawPath!!)
                mDrawPath = CustomPath(selectedColor, mBrushSize)
            }

            else -> return false
        }
        invalidate()

        return true
    }

    fun setBrushSize(newSize : Float){
        mBrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, resources.displayMetrics)
        mDrawPaint!!.strokeWidth = mBrushSize
    }

    internal inner class CustomPath(var color: Int, var brushThickness : Float) : Path()
}