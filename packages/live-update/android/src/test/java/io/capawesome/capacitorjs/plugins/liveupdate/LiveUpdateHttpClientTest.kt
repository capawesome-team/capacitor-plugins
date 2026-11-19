package io.capawesome.capacitorjs.plugins.liveupdate

import okhttp3.Request
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LiveUpdateHttpClientTest {

    private val url = "https://files.example.com/index.html"
    private val userAgentPattern = Regex("""^capawesome-capacitor-live-update/\d+\.\d+\.\d+(-[0-9A-Za-z.-]+)? \(android\)$""")
    private lateinit var httpClient: LiveUpdateHttpClient

    @Before
    fun setUp() {
        httpClient = LiveUpdateHttpClient(LiveUpdateConfig())
        httpClient.setDeviceId("device-1")
    }

    @Test
    fun buildRequest_carriesUserAgentAndNoDeviceId() {
        val request = httpClient.buildRequest(url)

        assertUserAgent(request)
        assertNull(request.header("X-Capawesome-Device-Id"))
    }

    @Test
    fun buildPostRequest_carriesUserAgentAndNoDeviceId() {
        val request = httpClient.buildPostRequest(url, JSONObject())

        assertUserAgent(request)
        assertNull(request.header("X-Capawesome-Device-Id"))
    }

    @Test
    fun buildRequestWithDeviceId_carriesUserAgentAndDeviceId() {
        val request = httpClient.buildRequestWithDeviceId(url)

        assertUserAgent(request)
        assertEquals("device-1", request.header("X-Capawesome-Device-Id"))
    }

    @Test
    fun buildRequestWithoutCompression_acceptsOnlyTheIdentityEncoding() {
        val request = httpClient.buildRequestWithoutCompression(url)

        assertEquals("identity", request.header("Accept-Encoding"))
    }

    private fun assertUserAgent(request: Request) {
        val userAgent = request.header("User-Agent")
        assertEquals("capawesome-capacitor-live-update/${LiveUpdatePlugin.VERSION} (android)", userAgent)
        assertTrue(userAgentPattern.matches(userAgent.orEmpty()))
    }
}
