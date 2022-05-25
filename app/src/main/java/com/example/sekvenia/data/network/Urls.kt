package com.example.sekvenia.data.remote.network

sealed class Urls {

    class RELEASE: Urls() {
        val BASE_URL = "https://s3-eu-west-1.amazonaws.com/"
    }
}
