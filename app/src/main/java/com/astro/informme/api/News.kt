package com.astro.informme.api

class News(val title : String, val name : String, val description : String, val url : String, val image : String) {
    override fun toString(): String {
        return "News(title='$title', name='$name', description='$description', url='$url', image='$image')"
    }
}