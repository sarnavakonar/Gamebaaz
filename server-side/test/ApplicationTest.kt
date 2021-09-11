package com.games

import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import kotlin.test.*
import io.ktor.server.testing.*
import io.netty.handler.codec.http.HttpHeaders.addHeader
import main.module

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/getAllGames").apply {
                assertEquals(HttpStatusCode.Unauthorized, response.status())
                //assertEquals("FIFA 21", response.content.toString().contains("FIFA 21"))
            }
        }
    }
}
