package com.astro.informme.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class News(val title : String, val name : String, val description : String, val url : String, val image : String) : Parcelable {
    override fun toString(): String {
        return "News(title='$title', name='$name', description='$description', url='$url', image='$image')"
    }
}