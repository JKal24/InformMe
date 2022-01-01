package com.astro.informme.api

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.astro.informme.BuildConfig

class Trends {

    fun getTrends(context: Context, country : String) {

        val queue = Volley.newRequestQueue(context)
        val baseTrendsUrl = "https://d236-72-53-195-163.ngrok.io/trends/canada"

        val jsonArrayRequest = JsonArrayRequest(baseTrendsUrl, { response ->
            var count = 1;

            for (i in 0..response.length()) {
                if (count > 3) break
                val keyword = java.net.URLEncoder.encode(response.get(i) as String?, "utf-8")

                fetchNews(queue, keyword)

                count++
            }

        }, {
            print("not found")
        })

        queue.add(jsonArrayRequest)

    }

    private fun fetchNews(queue : RequestQueue, keyword : String) {
        val url = "https://newsapi.org/v2/everything?q=" + keyword + "&from=2021-12-31&pageSize=2&sortBy=popularity&apiKey=" + BuildConfig.key
        //making a request
        val jsonObjectRequest = object: JsonObjectRequest(
            Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("name"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

            },
            Response.ErrorListener {

            }

        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        queue.add(jsonObjectRequest)
    }

    class News(val title : String, val name : String, val author : String, val url : String, val image : String) {

    }

}