package app.learn.kotlin.network

import java.net.URL

class ApiRepository : ApiRepositoryService {

    override fun doRequest(url: String): String {
        return URL(url).readText()
    }
}