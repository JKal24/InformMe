package com.astro.informme.adapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astro.informme.R
import com.astro.informme.api.News
import com.squareup.picasso.Picasso
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import androidx.core.content.ContextCompat


class NewsAdapter(var news: MutableList<News>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val view : View): RecyclerView.ViewHolder(view) {
        val newsImage : ImageView = view.findViewById(R.id.news_img)
        val newsName : TextView = view.findViewById(R.id.news_name)
        val newsTitle : TextView = view.findViewById(R.id.news_title)
        val newsDetails : TextView = view.findViewById(R.id.news_details)
        val newsLink : ImageButton = view.findViewById(R.id.news_link)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val newsView = inflater.inflate(R.layout.news, parent, false)
        return NewsViewHolder(newsView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsPiece: News = news[position]

        val newsImage = holder.newsImage
        Picasso.get().load(newsPiece.image).into(newsImage)

        val newsName = holder.newsName
        newsName.text = "By " + newsPiece.name

        val newsTitle = holder.newsTitle
        newsTitle.text = newsPiece.title

        val newsDetails = holder.newsDetails
        newsDetails.text = newsPiece.description

        val newsLink = holder.newsLink
        newsLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(newsPiece.url))
            startActivity(holder.view.context, browserIntent, null)
        }

    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun add(newNews : MutableList<News>) {
        this.news = newNews
    }

}