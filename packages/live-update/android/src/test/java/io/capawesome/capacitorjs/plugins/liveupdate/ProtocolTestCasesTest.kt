package io.capawesome.capacitorjs.plugins.liveupdate

import io.capawesome.capacitorjs.plugins.liveupdate.classes.CustomException
import io.capawesome.capacitorjs.plugins.liveupdate.classes.DownloadUrl
import io.capawesome.capacitorjs.plugins.liveupdate.classes.FileUrlTemplate
import io.capawesome.capacitorjs.plugins.liveupdate.classes.Manifest
import io.capawesome.capacitorjs.plugins.liveupdate.classes.UpdateFailure
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.CreateCheckRequest
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.CreateCheckResponse
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.CreateCheckResponseBundle
import io.capawesome.capacitorjs.plugins.liveupdate.enums.FailureReason
import java.io.File
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

/**
 * Runs the shared cases of `protocol-test-cases/`, which every Live Update SDK runs as well.
 */
class ProtocolTestCasesTest {

    @Test
    fun checkRequest() {
        forEachCase(readArray("check-request.json")) { case ->
            val input = case.getJSONObject("input")
            val request = CreateCheckRequest()
                .setAppVersionCode(input.optStringOrNull("appVersionCode"))
                .setAppVersionName(input.optStringOrNull("appVersionName"))
                .setBundleId(input.optStringOrNull("bundleId"))
                .setChannelName(input.optStringOrNull("channelName"))
                .setCustomId(input.optStringOrNull("customId"))
                .setDeviceId(input.optStringOrNull("deviceId"))
                .setFailure(input.optJSONObject("failure")?.let { createFailure(it) })
                .setOsVersion(input.optStringOrNull("osVersion"))
                .setPlatform(input.optStringOrNull("platform"))
                .setPluginVersion(input.optStringOrNull("pluginVersion"))
                .setRuntime(input.optStringOrNull("runtime"))

            val body = request.toJSONObject()

            val expectedBody = case.getJSONObject("expectedBody")
            assertJsonEquals(expectedBody, body)
        }
    }

    @Test
    fun checkResponse() {
        forEachCase(readArray("check-response.json")) { case ->
            val body = if (case.has("rawBody")) case.getString("rawBody") else case.get("body").toString()

            val response = CreateCheckResponse.parse(case.getInt("statusCode"), case.optStringOrNull("retryAfter"), body)

            val expected = case.getJSONObject("expected")
            assertEquals(expected.optStringOrNull("outcome"), response.outcome?.value)
            assertEquals(expected.optStringOrNull("skipReason"), response.skipReason)
            assertEquals(expected.optStringOrNull("channelName"), response.channelName)
            assertBundle(expected.optJSONObject("bundle"), response.bundle)
            assertEquals(if (expected.isNull("retryAfterMs")) null else expected.getLong("retryAfterMs"), response.retryAfterMs)
            assertEquals(expected.getBoolean("clearsPendingFailure"), response.clearsPendingFailure())
        }
    }

    @Test
    fun downloadUrlBuild() {
        forEachCase(readObject("download-url.json").getJSONArray("build")) { case ->
            val bundleJson = JSONObject(case.getJSONObject("bundle").toString()).put("id", "test-bundle")
            val bundle = CreateCheckResponseBundle.parse(bundleJson)

            assertNotNull(bundle)
            assertEquals(case.getString("expectedDownloadUrl"), DownloadUrl.fromBundle(bundle!!).toString())
        }
    }

    @Test
    fun downloadUrlParse() {
        forEachCase(readObject("download-url.json").getJSONArray("parse")) { case ->
            val text = case.getString("downloadUrl")
            if (case.has("expectedError")) {
                val exception = assertThrows(CustomException::class.java) { DownloadUrl.parse(text) }
                assertEquals(case.getString("expectedError"), exception.message)
                return@forEachCase
            }

            val downloadUrl = DownloadUrl.parse(text)

            val expected = case.getJSONObject("expected")
            assertEquals(expected.getString("url"), downloadUrl.url)
            assertEquals(expected.getBoolean("hasMarker"), downloadUrl.hasMarker())
            assertEquals(expected.optStringOrNull("artifactType"), downloadUrl.artifactType?.value)
            assertEquals(expected.optStringOrNull("checksum"), downloadUrl.checksum)
            assertEquals(expected.optStringOrNull("fileUrlTemplate"), downloadUrl.fileUrlTemplate?.value)
            assertEquals(expected.optStringOrNull("signature"), downloadUrl.signature)
        }
    }

    @Test
    fun fileUrl() {
        forEachCase(readArray("file-url.json")) { case ->
            val url = FileUrlTemplate(case.getString("fileUrlTemplate")).fill(case.getString("checksum"))

            assertEquals(case.getString("expectedUrl"), url)
        }
    }

    @Test
    fun manifestParse() {
        forEachCase(readObject("manifest.json").getJSONArray("parse")) { case ->
            val manifest = Manifest.parse(case.getString("text"))

            val expectedItems = case.optJSONArray("expectedItems")
            if (expectedItems == null) {
                assertNull(manifest)
                return@forEachCase
            }
            val items = manifest!!.items
            assertEquals(expectedItems.length(), items.size)
            items.forEachIndexed { index, item ->
                val expectedItem = expectedItems.getJSONObject(index)
                assertEquals(expectedItem.getString("href"), item.href)
                assertEquals(expectedItem.getString("checksum"), item.checksum)
                assertEquals(expectedItem.getInt("sizeInBytes"), item.sizeInBytes)
            }
        }
    }

    @Test
    fun manifestPlan() {
        forEachCase(readObject("manifest.json").getJSONArray("plan")) { case ->
            val currentManifest = case.optJSONArray("currentItems")?.let { Manifest.parse(it.toString()) }
            val latestManifest = Manifest.parse(case.getJSONArray("latestItems").toString())!!

            val plan = latestManifest.createPlan(currentManifest)

            val expected = case.getJSONObject("expected")
            val expectedCopies = expected.getJSONArray("copy").toList<JSONObject>().map { it.getString("from") to it.getString("to") }
            assertEquals(expectedCopies, plan.filesToCopy.map { it.sourceHref to it.item.href })
            assertEquals(expected.getJSONArray("download").toList<String>(), plan.itemsToDownload.map { it.href })
        }
    }

    private fun assertBundle(expected: JSONObject?, actual: CreateCheckResponseBundle?) {
        if (expected == null) {
            assertNull(actual)
            return
        }
        assertNotNull(actual)
        assertEquals(expected.getString("id"), actual!!.id)
        assertEquals(expected.getString("artifactType"), actual.artifactType.value)
        assertEquals(expected.getString("fileUrlTemplate"), actual.fileUrlTemplate.value)
        assertEquals(expected.optStringOrNull("checksum"), actual.checksum)
        assertEquals(expected.optStringOrNull("signature"), actual.signature)
        val expectedCustomProperties = expected.optJSONObject("customProperties")
        if (expectedCustomProperties == null) {
            assertNull(actual.customProperties)
        } else {
            assertJsonEquals(expectedCustomProperties, actual.customProperties)
        }
    }

    private fun assertJsonEquals(expected: Any?, actual: Any?) {
        assertEquals(toComparableValue(expected), toComparableValue(actual))
    }

    private fun createFailure(json: JSONObject): UpdateFailure {
        val reason = FailureReason.fromValue(json.getString("reason"))!!
        return UpdateFailure().setAppId("test-app").setBundleId(json.getString("bundleId")).setReason(reason)
    }

    private fun forEachCase(cases: JSONArray, assertCase: (JSONObject) -> Unit) {
        for (index in 0 until cases.length()) {
            val case = cases.getJSONObject(index)
            try {
                assertCase(case)
            } catch (error: AssertionError) {
                throw AssertionError("Case \"${case.getString("name")}\" failed: ${error.message}", error)
            }
        }
    }

    private fun readArray(fileName: String): JSONArray = JSONArray(readCaseFile(fileName))

    private fun readCaseFile(fileName: String): String = File("../protocol-test-cases", fileName).readText()

    private fun readObject(fileName: String): JSONObject = JSONObject(readCaseFile(fileName))

    private fun toComparableValue(value: Any?): Any? = when (value) {
        is JSONObject -> value.keys().asSequence().associateWith { toComparableValue(value.get(it)) }
        is JSONArray -> (0 until value.length()).map { toComparableValue(value.get(it)) }
        JSONObject.NULL -> null
        else -> value
    }

    private fun JSONObject.optStringOrNull(key: String): String? = if (isNull(key)) null else getString(key)

    @Suppress("UNCHECKED_CAST")
    private fun <T> JSONArray.toList(): List<T> = (0 until length()).map { get(it) as T }
}
