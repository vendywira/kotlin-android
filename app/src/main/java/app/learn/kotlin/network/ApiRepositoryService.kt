package app.learn.kotlin.network

interface ApiRepositoryService {
    fun doRequest(url: String): String
}