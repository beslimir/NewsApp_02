package com.example.newsapp_02.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp_02.data.model.Article
import com.example.newsapp_02.databinding.NewsListItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val myArticle = differ.currentList[position]
        holder.bind(myArticle)
    }

    override fun getItemCount() = differ.currentList.size


    private var onItemClickListener: ((Article) -> Unit)? = null
    private var onItemLongClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: (Article) -> Unit) {
        onItemLongClickListener = listener
    }


    private val differCallback = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    //constructor parameter because of viewBinding
    inner class NewsViewHolder(val binding: NewsListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.content.toString()
            binding.tvPublishedAt.text = article.pubDate
            binding.tvSource.text = article.creator.toString()
            Glide.with(binding.ivArticleImage.context)
                .load(article.image_url)
                .into(binding.ivArticleImage)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
            binding.root.setOnLongClickListener {
                onItemLongClickListener?.let {
                    it(article)
                }
                true
            }
        }
    }
}