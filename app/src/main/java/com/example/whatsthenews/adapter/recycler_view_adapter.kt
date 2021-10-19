package com.example.whatsthenews.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatsthenews.R
import com.example.whatsthenews.models.news_model

class recycler_view_adapter: RecyclerView.Adapter<recycler_view_adapter.MainViewHolder>() {

            var news = mutableListOf<news_model>()

            fun setNewsList(news:List<news_model>){
                this.news = news.toMutableList()
                notifyDataSetChanged()
            }

            class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var newsImage: ImageView
                lateinit var newsheadline: TextView
                lateinit var newsauthor: TextView
                lateinit var newsdescription: TextView
                init {
                    newsImage = itemView.findViewById(R.id.news_image)
                    newsheadline = itemView.findViewById(R.id.news_headline)
                    newsdescription = itemView.findViewById(R.id.news_description)
                    newsauthor = itemView.findViewById(R.id.news_author)

                }

            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_card, parent, false)

                return recycler_view_adapter.MainViewHolder(view)
            }

            override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
                holder.newsauthor.setText(news.get(position).author)
                holder.newsheadline.setText(news.get(position).title)
                holder.newsdescription.setText(news.get(position).description)
                Glide.with(holder.itemView.context).load(news.get(position).urlToImage).into(holder.newsImage)

    }

    override fun getItemCount(): Int = news.size
}