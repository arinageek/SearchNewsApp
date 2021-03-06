package com.example.searchnewsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mynewsapp.api.Article
import com.example.searchnewsapp.databinding.NewsRecyclerViewItemBinding

class NewsAdapter : PagingDataAdapter<Article, NewsAdapter.NewsViewHolder>(NEWS_COMPARATOR) {

    inner class NewsViewHolder(private val binding: NewsRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init{
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item != null){
                        OnItemClickListener?.invoke(item)
                    }
                }
            }
        }

        fun bind(article: Article) {
            binding.apply{
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
                tvTitle.text = article.title
                tvDescription.text = article.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let{
            holder.bind(it)
        }
    }

    private var OnItemClickListener: ((Article) -> Unit)? = null
    fun setOnItemClickListener(listener: (Article)->Unit){
        OnItemClickListener = listener
    }

    companion object{
        private val NEWS_COMPARATOR = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
    }

}