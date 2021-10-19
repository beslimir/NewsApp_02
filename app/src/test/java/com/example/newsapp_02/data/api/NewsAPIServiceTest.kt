package com.example.newsapp_02.data.api

import com.example.newsapp_02.BuildConfig
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

class NewsAPIServiceTest {

    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getLatestNews_sendRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("news_response.json")
            val responseBody = service.getLatestNews("de", 1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull() //check if we get null response
            assertThat(request.path).isEqualTo("/api/1/news?country=de&page=1&apiKey=${BuildConfig.API_KEY}")
        }
    }

    @Test
    fun getLatestNews_receivedResponse_correctTotalResults() {
        runBlocking {
            enqueueMockResponse("news_response.json")
            val responseBody = service.getLatestNews("de", 1).body()
            val resultsNumber = responseBody!!.totalResults
            assertThat(resultsNumber).isEqualTo(8934)
        }
    }

    @Test
    fun getLatestNews_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("news_response.json")
            val responseBody = service.getLatestNews("de", 1).body()
            val articlesList = responseBody!!.results
            assertThat(articlesList.size).isEqualTo(10)
        }
    }

    @Test
    fun getLatestNews_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("news_response.json")
            val responseBody = service.getLatestNews("de", 1).body()
            val articlesList = responseBody!!.results
            val article = articlesList[0]
            assertThat(article.title).isEqualTo("News von heute: Versuchter Mord – 88-Jähriger muss fünfeinhalb Jahre in Haft")
            assertThat(article.creator).isEqualTo(null)
        }
    }


}