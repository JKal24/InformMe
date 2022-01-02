package com.astro.informme.api

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.astro.informme.BuildConfig
import com.astro.informme.NewsFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import android.R.string.no
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField


class Trends {

    fun getTrends(context: Context, country : String, fragment: NewsFragment) {

        val queue = Volley.newRequestQueue(context)
        val baseTrendsUrl = "https://4d44-72-53-195-163.ngrok.io/trends/$country"

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
        val yesterday: ZonedDateTime = ZonedDateTime.now().with(ChronoField.NANO_OF_DAY, 0).minusDays(1)
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val dateISO = dateTimeFormatter.format(yesterday)

        val url = "https://newsdata.io/api/1/archive?apikey=" + BuildConfig.key + "&q=" + keyword + "&from_date=" + dateISO

        val jsonObjectRequest = object: JsonObjectRequest(
            Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("results")

                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("creator"),
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("link"),
                        newsJsonObject.getString("image_url")
                    )

                    fragment.updateList(news)
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

//        val news = News(
//            keyword,
//            keyword,
//            keyword,
//            keyword,
//            "https://thumbor.forbes.com/thumbor/fit-in/1200x0/" +
//                    "filters%3Aformat%28jpg%29/https%3A%2F%2Fspecials-images.forbesimg.com%2Fimageserve%2F61d08d27170d27fa6d3ad616%2F0x0.jpg"
//        )
//
//        fragment.updateList(news)
    }

}