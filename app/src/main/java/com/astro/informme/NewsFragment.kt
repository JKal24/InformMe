package com.astro.informme

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astro.informme.adapters.NewsAdapter
import com.astro.informme.data.News
import com.astro.informme.api.Trends
import com.astro.informme.data.NewsRepository
import com.astro.informme.data.NewsRoomDatabase
import com.astro.informme.data.NewsViewModel
import com.astro.informme.data.NewsViewModelFactory

class NewsFragment() : Fragment(), Callback<News> {

    private var newsPieces : MutableList<News> = ArrayList(100)
    private lateinit var newsAdapter : NewsAdapter

    private lateinit var database : NewsRoomDatabase
    private lateinit var repository : NewsRepository

    private lateinit var newsViewModel: NewsViewModel;

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

        Trends().getTrends(view.context, "Canada", this)

        val newsView = view.findViewById<RecyclerView>(R.id.news)

        newsView.adapter = newsAdapter
        newsView.layoutManager = LinearLayoutManager(this.context)


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

    override fun onComplete(Result: News) {
        newsPieces.add(Result)
        newsAdapter.add(newsPieces)
        newsViewModel.insert(Result)
        newsAdapter.notifyDataSetChanged()
    }
}