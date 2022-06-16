package com.wn.myapplication.span

import android.widget.TextView
import com.wn.myapplication.view.LinearGradientTextView

class LinearGradientSpan(showView: LinearGradientTextView, parentView: TextView): ViewSpan(showView) {

    init {
        showView.parentTextView = parentView
    }
}