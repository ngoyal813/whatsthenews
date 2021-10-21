package com.example.whatsthenews.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatsthenews.R
import com.example.whatsthenews.models.news_model
import java.lang.NullPointerException
import kotlin.coroutines.coroutineContext

class recycler_view_adapter: RecyclerView.Adapter<recycler_view_adapter.MainViewHolder>() {

            var news = mutableListOf<news_model>()
            lateinit var context : Context

            fun setNewsList(news:List<news_model>,context: Context){
                this.news = news.toMutableList()
                this.context = context
                notifyDataSetChanged()
            }

            class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var newsImage: ImageView
                lateinit var newsheadline: TextView
                lateinit var newsauthor: TextView
                lateinit var newsdescription: TextView
                var card : CardView
                init {
                    newsImage = itemView.findViewById(R.id.news_image)
                    newsheadline = itemView.findViewById(R.id.news_headline)
                    newsdescription = itemView.findViewById(R.id.news_description)
                    newsauthor = itemView.findViewById(R.id.news_author)
                    card = itemView.findViewById(R.id.card)
                }

            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_card, parent, false)

                return recycler_view_adapter.MainViewHolder(view)
            }

            override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

                if (news.get(position).author == null){
                    holder.newsauthor.setText("Author: No Author available")
                }else{
                    holder.newsauthor.setText("Published At: "+news.get(position).author)
                }
                if (news.get(position).title == null){
                    holder.newsheadline.setText("No Headlines Available")
                }else{
                    holder.newsheadline.setText(news.get(position).title)
                }
                if (news.get(position).description == null){
                    holder.newsdescription.setText("No Description Available! We regret for the inconvinience, Open it for more information")
                }else{
                    holder.newsdescription.setText(news.get(position).description)
                }
                if (news.get(position).urlToImage == null){
                    Glide.with(holder.itemView.context).load("https://media.istockphoto.com/vectors/breaking-news-vector-illustration-poster-banner-logo-badge-on-white-vector-id891605714?b=1&k=20&m=891605714&s=612x612&w=0&h=HR6jezIN5wQ7B8imsxws65esrjQTEUIu8IAY38f4ZQc=").into(holder.newsImage)
                }else{
                    Glide.with(holder.itemView.context).load(news.get(position).urlToImage).into(holder.newsImage)
                }
                holder.card.setOnClickListener(View.OnClickListener {
                    try {
                        val url : String? =news.get(position).url
                        val intent :Intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse(url))
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }catch (e:NullPointerException){
                        Toast.makeText(context,"You can't open this Article",Toast.LENGTH_LONG).show()
                    }

                })


    }

    override fun getItemCount(): Int = news.size
}