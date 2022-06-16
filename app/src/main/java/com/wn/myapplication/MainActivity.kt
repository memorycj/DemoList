package com.wn.myapplication

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.wn.myapplication.span.GlideImageSpan
import com.wn.myapplication.span.LinearGradientSpan
import com.wn.myapplication.span.ViewSpan
import com.wn.myapplication.util.dp
import com.wn.myapplication.view.LinearGradientTextView
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val spanTextView = findViewById<TextView>(R.id.span_text)
        val tag = "tag_tag"
        val linearGradinet = "l_grad"
        val imgTag = "img_flag"
        val icon_text = "icon_text"
        val text = "normal textView $tag, linearGradinetText $linearGradinet, dynamic Image $imgTag, icon and Text $icon_text "
        val span = SpannableStringBuilder(text)


        val compile = Pattern.compile(tag)
        val matcher = compile.matcher(text)
        val layoutInflater = LayoutInflater.from(this)
        while (matcher.find()) {
            val tagView = layoutInflater.inflate(R.layout.tag_view, null, false)
            val findViewById = tagView.findViewById<TextView>(R.id.tag)
            findViewById.text = "s: ${matcher.start()},end: ${matcher.end()}"
            span.setSpan(
                ViewSpan(tagView),
                matcher.start(),
                matcher.end(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }



        val lCompile = Pattern.compile(linearGradinet)
        val lMatcher = lCompile.matcher(text)
        while (lMatcher.find()) {
            val tagView = layoutInflater.inflate(R.layout.linear_gradinet, null, false)
            val findViewById = tagView.findViewById<LinearGradientTextView>(R.id.span_text)
            findViewById.text = "linearColorText"
            findViewById.baseColor = Color.RED
            findViewById.lightColor = Color.GREEN
            findViewById.hasColorAnimation = true
            span.setSpan(
                LinearGradientSpan(findViewById,spanTextView),
                lMatcher.start(),
                lMatcher.end(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }


        val imgcompile = Pattern.compile(imgTag)
        val matcherImg = imgcompile.matcher(text)
        while (matcherImg.find()) {
            span.setSpan(
                GlideImageSpan(spanTextView,"https://cdn.pixabay.com/photo/2016/12/23/12/40/night-1927265__480.jpg").setDrawableSize(20.dp,20.dp),
                matcherImg.start(),
                matcherImg.end(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }


        val iconTextCompile = Pattern.compile(icon_text)
        val matcherIconText = iconTextCompile.matcher(text)
        while (matcherIconText.find()) {
            val view = layoutInflater.inflate(R.layout.img_view, null, false)

            val iconView = view.findViewById<ImageView>(R.id.icon)
            Glide.with(this).load("https://img0.baidu.com/it/u=512340543,3139277133&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281").into(object :
                SimpleTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    iconView.setImageDrawable(resource)
                    spanTextView.invalidate()
                }

            })
            span.setSpan(
                ViewSpan(view),
                matcherIconText.start(),
                matcherIconText.end(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        spanTextView.text = span
    }
}