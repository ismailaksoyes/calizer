package com.avalon.calizer.ui.tutorial

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.ActionBar
import android.icu.util.Measure
import android.os.Bundle
import android.service.autofill.FieldClassification
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import androidx.core.view.isVisible
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.R
import com.avalon.calizer.databinding.FragmentTutorialBinding
import com.avalon.calizer.databinding.FragmentViewPagerBinding
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.utils.MySharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding>(FragmentViewPagerBinding::inflate) {
    private lateinit var viewPager: ViewPager2

    @Inject
    lateinit var prefs: MySharedPreferences
    private val tutorialAdapter by lazy { TutorialPagerAdapter(this) }


    private var onTutorialPageChangeCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            getTutorialAnim(position)
           binding.clStartNowNext.setOnClickListener {
               when(position){
                   0->{
                       viewPager.setCurrentItem(1,true)
                   }
                   1->{
                       viewPager.setCurrentItem(2,true)
                   }
                   else->{
                       prefs.showIntro = false
                       it.findNavController().navigate(R.id.action_destination_tutorial_to_destination_accounts)
                   }
               }
           }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPager2
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = tutorialAdapter
        viewPager.registerOnPageChangeCallback(onTutorialPageChangeCallBack)
       setupPagerClick()

    }


    private fun setupPagerClick(){
        binding.tvNextIntro.setOnClickListener {
            viewPager.setCurrentItem(2,true)
        }
    }

    fun getTutorialAnim(position: Int) {
        when (position) {
            0 -> {
                if (!binding.tvNextIntro.isVisible) {
                    binding.tvNextIntro.textAnimVisible()
                }

            }
            1 -> {
                if (binding.tvNextIntro.isVisible) {
                    binding.tvNextIntro.textAnimGone()
                }
                if(binding.tvStartNow.isVisible){
                    binding.clStartNowNext.closeAnim()
                }

            }
            2 -> {
                if (binding.tvNextIntro.isVisible) {
                    binding.tvNextIntro.textAnimGone()
                }
               binding.clStartNowNext.openAnim()


            }


        }
    }

    private fun ConstraintLayout.openAnim() {

            val getParams = this.layoutParams

            val view: LinearLayout = binding.viewMeasure
            getParams.width = view.width - 20
            val anim = ValueAnimator.ofInt(150, getParams.width)
            anim.duration = 500
            anim.addUpdateListener {
                getParams.width = it.animatedValue as Int
                this.layoutParams = getParams

            }
            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.tvStartNow.textAnimVisible()
                }
            })
            anim.start()


    }
    private fun ConstraintLayout.closeAnim() {
        binding.tvStartNow.textAnimGone()
        val getParams = this.layoutParams

        val anim = ValueAnimator.ofInt(getParams.width, 150)
        anim.duration = 600
        anim.addUpdateListener {
            getParams.width = it.animatedValue as Int
            this.layoutParams = getParams

        }

        anim.start()


    }


    fun TextView.textAnimVisible() {
        visibility = View.VISIBLE
        val anim = AnimationUtils.loadAnimation(this.context, R.anim.fade_in)
        this.startAnimation(anim)
    }

    fun TextView.textAnimGone() {
        val anim = AnimationUtils.loadAnimation(this.context, R.anim.fade_out)
        this.startAnimation(anim)
        postDelayed({
            visibility = View.GONE
        }, 500)
    }


}

