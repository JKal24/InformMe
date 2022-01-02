package com.astro.informme

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astro.informme.adapters.NewsAdapter
import com.astro.informme.api.News
import com.astro.informme.api.Trends

class NewsFragment : Fragment() {

    private val newsPieces : MutableList<News> = ArrayList(100)
    private val newsAdapter = NewsAdapter(newsPieces)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        Trends().getTrends(view.context, "canada", this)
        val newsView = view.findViewById<RecyclerView>(R.id.news)

        newsView.adapter = newsAdapter
        newsView.layoutManager = LinearLayoutManager(this.context)

        return view
    }

    fun updateList(news : News) {
        newsPieces.add(news)
        newsAdapter.add(newsPieces)
        newsAdapter.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment()
    }
}