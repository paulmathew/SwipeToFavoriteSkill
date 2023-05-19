package com.example.swipetofavorite.utils

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.abs

abstract class OnSwipeTouchListener(context: Context?) : OnTouchListener {
    private val gestureDetector: GestureDetector

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return if (event != null)
            gestureDetector.onTouchEvent(event)
        else
            false
    }


    private inner class GestureListener : SimpleOnGestureListener() {

        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            val deltaX = e2.x - e1.x
            if (e1 == null || e2 == null) {
                return false
            }


            if (abs(deltaX) > SWIPE_THRESHOLD) {
                if (deltaX > 0) {
                    onSwipeRight()
                } else {
                    onSwipeLeft()
                }
                return true
            }
            return false
        }
    }

    abstract fun onSwipeRight()
    abstract fun onSwipeLeft()


    companion object {
        private const val SWIPE_THRESHOLD = 100// Adjust this value as needed
    }
}