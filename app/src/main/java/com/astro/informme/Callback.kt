package com.astro.informme

// AsyncTask is deprecated, therefore simple callbacks will be used

interface Callback<R> {

    fun onComplete(Result : R)

    fun onAllComplete(Result: List<R>)
}