package com.greenbot.weatherapp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.greenbot.weatherapp.R
import com.greenbot.weatherapp.convertDpToPixel


class VerticalDividerItemDecoration(context: Context) :
    androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

    private val mDivider: Drawable =
        ContextCompat.getDrawable(context, R.drawable.item_divider_decoration)!!
    private val margin = convertDpToPixel(context, 24f)

    override fun onDrawOver(
        c: Canvas,
        parent: androidx.recyclerview.widget.RecyclerView,
        state: androidx.recyclerview.widget.RecyclerView.State
    ) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params =
                child.layoutParams as androidx.recyclerview.widget.RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight

            mDivider.setBounds(left + margin, top, right - margin, bottom)
            mDivider.draw(c)
        }
    }
}