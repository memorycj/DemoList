package com.wn.myapplication.util

val Float.dp
    get() = DensityUtils.dip2px(this)

val Float.px
    get() = DensityUtils.px2dip(this)

val Float.sp
    get() = DensityUtils.sp2px(this)

val Int.dp
    get() = toFloat().dp

val Int.px
    get() = toFloat().px

val Int.sp
    get() = toFloat().sp