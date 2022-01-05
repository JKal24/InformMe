package com.astro.informme.api

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.astro.informme.BuildConfig
import com.astro.informme.NewsFragment
import com.astro.informme.data.News

class Trends {

    fun getTrends(context: Context, country : String, fragment: NewsFragment) {

        val queue = Volley.newRequestQueue(context)
        val baseTrendsUrl = BuildConfig.ngrok + "/trends/$country"

        val jsonArrayRequest = JsonArrayRequest(baseTrendsUrl, { response ->

            for (i in 0 until response.length()) {
                val keyword = java.net.URLEncoder.encode(response.get(i) as String?, "utf-8")

                fetchNews(queue, keyword, fragment)
            }

        }, {
        })

        queue.add(jsonArrayRequest)

    }

    /**
     * API being restricted, will re-enable closer to production
     */

    private fun fetchNews(queue : RequestQueue, keyword : String, fragment : NewsFragment) {
//        val yesterday: ZonedDateTime = ZonedDateTime.now().with(ChronoField.NANO_OF_DAY, 0).minusDays(1)
//        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
//        val dateISO = dateTimeFormatter.format(yesterday)
//
//        var url = "https://newsapi.org/v2/everything?apiKey=" + BuildConfig.key + "&q=" + keyword + "&from=" + dateISO + "&sortBy=popularity"
//
//        val jsonObjectRequest = object: JsonObjectRequest(
//            Method.GET,
//            url,
//            null,
//            Response.Listener {
//                val newsJsonArray = it.getJSONArray("articles")
//
//                for(i in 0 until newsJsonArray.length()) {
//                    val newsJsonObject = newsJsonArray.getJSONObject(i)
//                    val news = News(
//                        newsJsonObject.getString("title"),
//                        newsJsonObject.getJSONObject("source").getString("name"),
//                        newsJsonObject.getString("description"),
//                        newsJsonObject.getString("url"),
//                        newsJsonObject.getString("urlToImage")
//                    )
//
//                    fragment.onComplete(news)
//                }
//
//            },
//            Response.ErrorListener {
//
//            }
//
//        ) {
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["User-Agent"] = "Mozilla/5.0"
//                return headers
//            }
//        }
//
//        queue.add(jsonObjectRequest)

        val news = News(
            keyword,
            "$keyword part 2",
            "$keyword part 3",
            "http://developer.android.com",
            "https://thumbor.forbes.com/thumbor/fit-in/1200x0/" +
                    "filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F61d08d27170d27fa6d3ad616%2F0x0.jpg"
        )

        fragment.onComplete(news)
    }

}