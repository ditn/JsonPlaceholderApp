package com.adambennett.api.service.testutils

import com.adambennett.testutils.testrules.after
import com.adambennett.testutils.testrules.before
import okhttp3.mockwebserver.MockWebServer

fun mockWebServerInit(server: MockWebServer) =
    before {
        server.start()
    } after {
        server.shutdown()
    }