package com.wn.myapplication.view

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.RelativeLayout
import com.wn.myapplication.R
import com.wn.myapplication.databinding.LayComboViewBinding
import com.wn.myapplication.util.dp

class ComboView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {
    private val animDuration = 150L
    private var binding: LayComboViewBinding =
        LayComboViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val bgScaleAnim = ScaleAnimation(1.0f,1.1f,1.0f,1.1f).apply {
        duration = animDuration
        pivotX = 0.5f
        pivotY = 0.5f
        fillBefore = true
    }
    private val numberScaleAnim = ValueAnimator.ofFloat(1.5f,1f).apply {
        duration = 2000L
    }

    private var comCount = 1
    private val maxHeight = 25.dp

    private var heightChangeAnims:List<ValueAnimator> = arrayListOf(ValueAnimator.ofInt(maxHeight, maxHeight + 2.dp),ValueAnimator.ofInt(maxHeight, maxHeight - 2.dp))
    private var heightChangeAnimSet = AnimatorSet().apply {
        playSequentially(heightChangeAnims)
        duration = animDuration
    }
    init {
        val updateListener = ValueAnimator.AnimatorUpdateListener { anim ->
            binding.prog.layoutParams =  binding.prog.layoutParams.apply {
                height = anim.animatedValue as Int
            }
        }
        heightChangeAnims.forEach {
            it.addUpdateListener(updateListener)
        }
        binding.circleBg.setOnClickListener {
            comCount++
            it.clearAnimation()
            it.startAnimation(bgScaleAnim)
            binding.comboCount.clearAnimation()
            numberScaleAnim.addUpdateListener {  anim ->
                val scale = anim.animatedValue as Float
                binding.comboCount.scaleX = scale
                binding.comboCount.scaleY = scale
            }
            binding.comboCount.text = comCount.toString()
            numberScaleAnim.cancel()
            numberScaleAnim.start()
            if(binding.prog.height < maxHeight){
                binding.prog.layoutParams =  binding.prog.layoutParams.apply {
                    height = (height + comCount.dp).coerceAtMost(maxHeight)
                }
            } else {
                heightChangeAnimSet.cancel()
                heightChangeAnimSet.start()
            }
        }
    }

    fun combo(duration: Int){

    }
}