package com.astro.informme.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="news_table")
data class News(val title : String, val name : String, val description : String, val url : String, val image : String) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
