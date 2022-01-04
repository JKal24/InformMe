package com.astro.informme

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astro.informme.adapters.NewsAdapter
import com.astro.informme.api.News
import com.astro.informme.api.Trends

class NewsFragment : Fragment(), Callback<News> {

    private var newsPieces : MutableList<News> = ArrayList(100)
    private lateinit var newsAdapter : NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        if (savedInstanceState != null) newsPieces = savedInstanceState.get("LIST") as MutableList<News>
        newsAdapter = NewsAdapter(newsPieces)

        Trends().getTrends(view.context, "canada", this)

        val newsView = view.findViewById<RecyclerView>(R.id.news)

        newsView.adapter = newsAdapter
        newsView.layoutManager = LinearLayoutManager(this.context)

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putParcelableArrayList("LIST", ArrayList<Parcelable>(newsPieces))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment()
    }

    override fun onComplete(Result: News) {
        newsPieces.add(Result)
        newsAdapter.add(newsPieces)
        newsAdapter.notifyDataSetChanged()
    }
}