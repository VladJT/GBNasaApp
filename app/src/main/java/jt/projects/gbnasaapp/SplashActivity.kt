package jt.projects.gbnasaapp

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.*
import jt.projects.gbnasaapp.databinding.ActivitySplashScreenBinding

private const val SPLASH_DELAY = 3000L

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val handler = Handler(Looper.getMainLooper())

    private val rotateValue = 750f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageViewLoading.animate().rotationBy(rotateValue)
            //.scaleXBy(1F)
            .alpha(1f)
            .setInterpolator(LinearInterpolator())
            .setDuration(SPLASH_DELAY)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?) {
                    startActivity(
                        Intent(
                            this@SplashActivity,
                            MainActivity::class.java
                        )
                    )
                    finish()
                }

                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {
                    flySpaceman()
                    flyRocket()
                }
            })
    }

    private fun flyRocket() {
        val rocketTransition = TransitionSet().apply {
            val fade = Fade()
            fade.duration = SPLASH_DELAY
            addTransition(fade)
            val changeBounds = ChangeBounds()
            changeBounds.setPathMotion(ArcMotion())
            changeBounds.duration = SPLASH_DELAY
            addTransition(changeBounds)
        }

        TransitionManager.beginDelayedTransition(
            binding.transitionsContainerRocket,
            rocketTransition
        )

        binding.imageViewRocket.animate().rotationBy(-40F).setDuration(SPLASH_DELAY)

        val params = binding.imageViewRocket.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.END or Gravity.TOP
        binding.imageViewRocket.layoutParams = params
    }

    private fun flySpaceman() {
        val spacemanTransition = TransitionSet().apply {
            val fade = Fade()
            fade.duration = SPLASH_DELAY
            addTransition(fade)
            val changeBounds = ChangeBounds()
            changeBounds.setPathMotion(ArcMotion())
            changeBounds.duration = SPLASH_DELAY
            addTransition(changeBounds)
        }

        TransitionManager.beginDelayedTransition(
            binding.transitionsContainerSpaceman,
            spacemanTransition
        )

        binding.imageViewSpaceman.animate().alpha(1F).setDuration(SPLASH_DELAY/2)
        binding.imageViewSpaceman.animate().rotationBy(900F).setDuration(SPLASH_DELAY)

        val params = binding.imageViewSpaceman.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.END or Gravity.BOTTOM
        binding.imageViewSpaceman.layoutParams = params
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

}