package com.avalon.calizer.ui.main.fragments.analyze.storyanalyze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.avalon.calizer.databinding.FragmentStoryViewsBinding
import com.avalon.calizer.ui.base.BaseFragment
import com.avalon.calizer.ui.main.fragments.analyze.storyanalyze.adapter.StoryViewsAdapter
import com.avalon.calizer.utils.CubeTransformer


class StoryViewsFragment : BaseFragment<FragmentStoryViewsBinding>(FragmentStoryViewsBinding::inflate) {
    private val args: StoryViewsFragmentArgs by navArgs()
    private lateinit var viewPager: ViewPager2


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val urlList = args.storyList
        if (urlList.isNotEmpty()){
            createViewPagerAdapter(urlList.toList())
        }
    }


    private fun createViewPagerAdapter(list: List<String>) {
         val storyAdapter = StoryViewsAdapter(this, list)
        viewPager = binding.viewPagerStory
        viewPager.setPageTransformer(CubeTransformer())
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = storyAdapter
        val child = viewPager.getChildAt(0)
        if (child is RecyclerView){
            child.overScrollMode = View.OVER_SCROLL_NEVER
        }

    }


}