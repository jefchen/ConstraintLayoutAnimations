package com.jeffrey.contraintlayoutanimations

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private var show = false

    private lateinit var constraint: ConstraintLayout
    private lateinit var backgroundImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.circuit)

        constraint = findViewById(R.id.constraint)
        backgroundImage = findViewById(R.id.backgroundImage)

        val backgroundImage = findViewById<ImageView>(R.id.backgroundImage)
        backgroundImage.setOnClickListener {
            if(show)
                hideComponents() // if the animation is shown, we hide back the views
            else
                showComponents() // if the animation is NOT shown, we animate the views

            // toggleMoveBackgroundImage()
        }
    }

    private fun toggleMoveBackgroundImage() {
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        /*
            TransitionManager.beginDelayedTransition will animate layout changes, such as
            change in layout params, or when view is shown/hidden. However, it does not
            animate changes to property such as translationX and translationY.
         */
        show = !show
        TransitionManager.beginDelayedTransition(constraint, transition)
        if (show) {
            val layoutParams = backgroundImage.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.marginStart = 200
            backgroundImage.layoutParams = layoutParams
        } else {
            val layoutParams = backgroundImage.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.marginStart = 0
            backgroundImage.layoutParams = layoutParams
        }
    }

    private fun showComponents(){
        show = true

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.circuit_detail)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSet.applyTo(constraint)
    }

    private fun hideComponents(){
        show = false

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.circuit)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSet.applyTo(constraint)
    }

}
