package com.adambennett.network

import com.adambennett.network.interfaces.Logger
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.io.IOException
import java.util.Locale

class LoggingInterceptor(private val logger: Logger) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = System.nanoTime()

        var requestLog = String.format(
            "Sending request of type %s to %s with headers %s",
            request.method,
            request.url,
            request.headers
        )

        if (request.method.equals("post", ignoreCase = true) ||
            request.method.equals("put", ignoreCase = true)
        ) {
            requestLog = "\n$requestLog\n${requestBodyToString(request.body)}"
        }

        logger.v("Request:\n$requestLog")

        val response = chain.proceed(request)
        val endTime = System.nanoTime()

        val responseLog = String.format(
            Locale.ENGLISH,
            "Received response from %s in %.1fms%n%s",
            response.request.url,
            (endTime - startTime) / 1e6,
            response.headers
        )

        val bodyString = response.body!!.string()
        if (response.code == 200 || response.code == 201 || response.code == 101) {
            logger.v("Response: ${response.code}\n$responseLog\n$bodyString")
        } else {
            logger.e("Response: ${response.code}\n$responseLog\n$bodyString")
        }

        return response.newBuilder()
            .body(bodyString.toResponseBody(response.body!!.contentType()))
            .build()
    }

    private fun requestBodyToString(request: RequestBody?): String {
        val buffer = Buffer()
        return try {
            if (request != null) {
                request.writeTo(buffer)
                buffer.readUtf8()
            } else {
                ""
            }
        } catch (e: IOException) {
            "IOException reading request body"
        } finally {
            buffer.close()
        }
    }
}
