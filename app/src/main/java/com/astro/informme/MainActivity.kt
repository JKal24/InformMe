package com.astro.informme

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var newsFragment: NewsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val refresh = findViewById<ImageButton>(R.id.refresh)
        val context = this.applicationContext
        refresh.setOnClickListener {
            lifecycleScope.launch{
                newsFragment.getNews(context)
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                newsFragment = NewsFragment()
                add(R.id.main_content, newsFragment)
                setReorderingAllowed(true)
            }
        }
    }
}