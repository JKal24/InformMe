package com.astro.informme

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astro.informme.adapters.NewsAdapter
import com.astro.informme.data.News
import com.astro.informme.api.Trends
import com.astro.informme.data.NewsRepository
import com.astro.informme.data.NewsRoomDatabase
import com.astro.informme.data.NewsViewModel
import com.astro.informme.data.NewsViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsFragment() : Fragment(), Callback<News> {

    private var newsPieces : MutableList<News> = ArrayList(100)
    private lateinit var newsAdapter : NewsAdapter

    private lateinit var database : NewsRoomDatabase
    private lateinit var repository : NewsRepository

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        database = NewsRoomDatabase.getDatabase(view.context)
        repository = NewsRepository(database.newsDao())

        val newsViewModel : NewsViewModel by viewModels {
            NewsViewModelFactory(repository)
        }
        this.newsViewModel = newsViewModel

        newsAdapter = NewsAdapter(newsPieces)

        val newsView = view.findViewById<RecyclerView>(R.id.news)

        newsView.adapter = newsAdapter
        newsView.layoutManager = LinearLayoutManager(this.context)

        lifecycleScope.launch {
            newsViewModel.getNews().collect { value ->
                if (value.size < 10) {
                    getNews(view.context)
                } else {
                    onAllComplete(value)
                }

            }
        }

        val spinner = view.findViewById<Spinner>(R.id.country_spinner)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinner.setSelection(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        ArrayAdapter.createFromResource(
            view.context,
            R.array.countries,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            NewsFragment()
    }

    fun getNews(context: Context) {
        val newsFragment = this
        lifecycleScope.launch {
            newsViewModel.empty()
            Trends().getTrends(context, "Canada", newsFragment)
        }
    }

    override fun onAllComplete(Result: List<News>) {
        newsPieces = Result as MutableList<News>
        newsAdapter.add(newsPieces)
        newsAdapter.notifyDataSetChanged()
    }

    override fun onComplete(Result: News) {
        newsPieces.add(Result)
        newsAdapter.add(newsPieces)
        newsViewModel.insert(Result)
        newsAdapter.notifyDataSetChanged()
    }
}