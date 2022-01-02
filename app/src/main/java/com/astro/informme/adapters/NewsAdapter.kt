package com.astro.informme.adapters

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astro.informme.R
import com.astro.informme.api.News

class NewsAdapter(var news: MutableList<News>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(private val view : View): RecyclerView.ViewHolder(view) {
        val newsImage : ImageView = view.findViewById(R.id.news_img)
        val newsName : TextView = view.findViewById(R.id.news_name)
        val newsTitle : TextView = view.findViewById(R.id.news_title)
        val newsDetails : TextView = view.findViewById(R.id.news_details)
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
        newsImage.setImageURI(Uri.parse(newsPiece.image))

        val newsName = holder.newsName
        newsName.text = newsPiece.name

        val newsTitle = holder.newsTitle
        newsTitle.text = newsPiece.title

        val newsDetails = holder.newsDetails
        newsDetails.text = newsPiece.description
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun add(newNews : MutableList<News>) {
        this.news = newNews
    }

}