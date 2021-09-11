package com.example.gamebaaz.util

import android.view.ViewGroup
import androidx.transition.TransitionManager

fun ViewGroup.makeFadeTransition(animationDuration: Long) {
    val fade: androidx.transition.Fade = androidx.transition.Fade()

    fade.apply {
        duration = animationDuration
    }
    TransitionManager.beginDelayedTransition(this, fade)
}