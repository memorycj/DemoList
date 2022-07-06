package com.wn.myapplication.span;

import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by bby
 * Date: 2017/9/20.
 */

public abstract class LongClickableSpan extends ClickableSpan {

    public abstract void onLongClick(View view);

}
