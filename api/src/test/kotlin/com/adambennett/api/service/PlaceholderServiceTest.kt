package com.adambennett.api.service

import com.adambennett.api.api.PATH_COMMENTS
import com.adambennett.api.api.PATH_POSTS
import com.adambennett.api.api.PATH_USERS
import com.adambennett.api.koin.apiModule
import com.adambennett.api.service.testutils.getStringFromResource
import com.adambennett.api.service.testutils.mockNetworkModule
import com.adambennett.api.service.testutils.testrules.mockWebServerInit
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.`should equal to`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest

class PlaceholderServiceTest : KoinTest {

    private val subject: PlaceholderService by inject()
    private val server: MockWebServer = MockWebServer()

    @get:Rule
    private val mockServerRule = mockWebServerInit(server)

    @Before
    fun setUp() {
        startKoin(
            listOf(
                mockNetworkModule(server),
                apiModule
            )
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getComments() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getStringFromResource("api/Comments.json"))
        )

        subject.getComments()
            .test()
            .assertComplete()
            .assertNoErrors()
            .values()
            .first()
            .apply {
                this[0].id `should equal to` 1
                this[1].name `should equal to` "quo vero reiciendis velit similique earum"
                this[2].email `should equal to` "Nikita@garfield.biz"
                this[3].body `should equal to` "non et atque\noccaecati deserunt quas accusantium unde odit nobis qui voluptatem\nquia voluptas consequuntur itaque dolor\net qui rerum deleniti ut occaecati"
            }

        server.takeRequest().path `should equal to` "/$PATH_COMMENTS"
    }

    @Test
    fun getUsers() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getStringFromResource("api/Users.json"))
        )

        subject.getUsers()
            .test()
            .assertComplete()
            .assertNoErrors()
            .values()
            .first()
            .apply {
                val (name, id) = this[0]
                name `should equal to` "Leanne Graham"
                id `should equal to` 1
            }

        server.takeRequest().path `should equal to` "/$PATH_USERS"
    }

    @Test
    fun getPosts() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getStringFromResource("api/Posts.json"))
        )

        subject.getPosts()
            .test()
            .assertComplete()
            .assertNoErrors()
            .values()
            .first()
            .apply {
                this[0].userId `should equal to` 1
                this[1].id `should equal to` 2
                this[2].title `should equal to` "ea molestias quasi exercitationem repellat qui ipsa sit aut"
                this[3].body `should equal to` "ullam et saepe reiciendis voluptatem adipisci\nsit amet autem assumenda provident rerum culpa\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt voluptatem rerum illo velit"
            }

        server.takeRequest().path `should equal to` "/$PATH_POSTS"
    }
}