package com.avalon.calizer.ui.main.fragments.analyze.followanalyze.allposts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avalon.calizer.data.local.analyze.PostViewData
import com.avalon.calizer.data.local.follow.FollowData
import com.avalon.calizer.databinding.PostsItemViewBinding
import com.avalon.calizer.utils.AllPostState
import com.avalon.calizer.utils.loadPPUrl
import com.avalon.calizer.utils.loadPostUrl

class AllPostAdapter :ListAdapter<PostViewData,MainViewHolder>(DiffCallback()){

    private var _allPost = mutableListOf<PostViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
       val binding = PostsItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setAllPost(allPost:List<PostViewData>){
        _allPost = allPost.toMutableList()
    }

    fun setPostState(postState:AllPostState){
        if (_allPost.isNotEmpty()){
            when(postState){
                AllPostState.MOST_LIKE->{
                    _allPost.sortByDescending{it.likeCount}
                    submitList(_allPost.take(9))
                }
                AllPostState.MOST_COMMENT->{
                    _allPost.sortByDescending { it.commentCount }
                    submitList(_allPost.take(9))
                }
            }
        }
    }


}
private class DiffCallback : DiffUtil.ItemCallback<PostViewData>() {
    override fun areItemsTheSame(oldItem: PostViewData, newItem: PostViewData): Boolean {
        return oldItem.mediaId == newItem.mediaId
    }

    override fun areContentsTheSame(oldItem: PostViewData, newItem: PostViewData): Boolean {
        return oldItem == newItem
    }

}

class MainViewHolder(val binding:PostsItemViewBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(data:PostViewData){
        binding.ivPost.loadPostUrl(data.imageUrl)
        binding.tvLikeCount.text = data.likeCount.toString()
        binding.tvCommentCount.text = data.commentCount.toString()
    }
}