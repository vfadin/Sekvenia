package com.example.sekvenia.data.network

suspend fun <T> safeApiCall(apiCall: suspend () -> T): T? {
    return try {
        apiCall.invoke()
    } catch (throwable: Exception) {
        throwable.printStackTrace()
        null
    }
}