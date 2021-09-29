package com.example.galleryapp.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * 그리드 모양에서 아이템 간에
 * 수직, 수평으로 공간을 만들기 위함
 */
class GridDividerDecoration internal constructor(context: Context, @DrawableRes layout: Int) : ItemDecoration() {

    private var mDivider: Drawable? = null
    private val mInsets: Int

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawVertical(c, parent)
        drawHorizontal(c, parent)
    }

    init {
        mDivider = ContextCompat.getDrawable(context, layout)
        mInsets = 1
    }

    /**
     * Draw dividers at each expected grid interval
     */
    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        if (parent.childCount == 0) return
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val child = parent.getChildAt(0)
        if (child.height == 0) return
        val params = child.layoutParams as RecyclerView.LayoutParams
        var top = child.bottom + params.bottomMargin + mInsets
        var bottom = top + (mDivider?.intrinsicHeight ?: 0)
        val parentBottom = parent.height - parent.paddingBottom
        while (bottom < parentBottom) {
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(c)
            top += mInsets + params.topMargin + child.height + params.bottomMargin + mInsets
            bottom = top + (mDivider?.intrinsicHeight ?: 0)
        }
    }

    /**
     * Draw dividers to the right of each child view
     */
    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin + mInsets
            val right = left + (mDivider?.intrinsicWidth ?: 0)
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) { // We can supply forced insets for each item view here in the Rect
        outRect[mInsets, mInsets, mInsets] = mInsets
    }

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}
