package com.astro.informme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.main_app_bar))

        supportFragmentManager.commit {
            add(R.id.main_header, Header())
            add(R.id.main_content, NewsFragment())
            setReorderingAllowed(true)
        }
    }
}