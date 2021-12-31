package com.astro.informme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            add(R.id.main_header, Header())
            add(R.id.main_content, NewsFragment())
            setReorderingAllowed(true)
        }

//        supportFragmentManager.commit {
//            add(R.id.fragment_container_view, AccountLink())
//            setReorderingAllowed(true)
//        }
    }
}