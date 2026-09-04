package io.capawesome.capacitorjs.plugins.pdfannotator

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.ParcelFileDescriptor
import android.os.ext.SdkExtensions
import androidx.activity.result.ActivityResult
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.pdf.PdfWriteHandle
import io.capawesome.capacitorjs.plugins.pdfannotator.classes.CustomExceptions
import io.capawesome.capacitorjs.plugins.pdfannotator.classes.PdfAnnotatorActivity
import io.capawesome.capacitorjs.plugins.pdfannotator.classes.options.OpenOptions
import io.capawesome.capacitorjs.plugins.pdfannotator.classes.results.IsAvailableResult
import io.capawesome.capacitorjs.plugins.pdfannotator.classes.results.OpenResult
import io.capawesome.capacitorjs.plugins.pdfannotator.interfaces.EmptyCallback
import io.capawesome.capacitorjs.plugins.pdfannotator.interfaces.NonEmptyResultCallback
import java.io.File
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PdfAnnotator(private val plugin: PdfAnnotatorPlugin) {

    init {
        cleanUpDocumentsDirectory()
    }

    fun createOpenIntent(options: OpenOptions): Intent {
        if (!isSupported()) {
            throw CustomExceptions.NOT_SUPPORTED
        }
        val file = getFileByPath(options.path)
        if (file == null || !file.exists()) {
            throw CustomExceptions.FILE_NOT_FOUND
        }
        val outputFile = File(getDocumentsDirectory(), "${UUID.randomUUID()}.pdf")
        return Intent(plugin.context, PdfAnnotatorActivity::class.java)
            .putExtra(PdfAnnotatorActivity.EXTRA_INPUT_PATH, file.absolutePath)
            .putExtra(PdfAnnotatorActivity.EXTRA_OUTPUT_PATH, outputFile.absolutePath)
    }

    fun handleOpenResult(result: ActivityResult, callback: NonEmptyResultCallback<OpenResult>) {
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val outputPath = result.data?.getStringExtra(PdfAnnotatorActivity.EXTRA_OUTPUT_PATH)
                if (outputPath == null) {
                    callback.error(CustomExceptions.SAVE_FAILED)
                    return
                }
                callback.success(OpenResult(File(outputPath)))
            }
            Activity.RESULT_CANCELED -> callback.error(CustomExceptions.CANCELED)
            PdfAnnotatorActivity.RESULT_LOAD_FAILED -> callback.error(CustomExceptions.LOAD_FAILED)
            else -> callback.error(CustomExceptions.SAVE_FAILED)
        }
    }

    fun isAvailable(callback: NonEmptyResultCallback<IsAvailableResult>) {
        callback.success(IsAvailableResult(isSupported()))
    }

    private fun cleanUpDocumentsDirectory() {
        getDocumentsDirectory().listFiles()?.forEach { it.delete() }
    }

    private fun getDocumentsDirectory(): File {
        val directory = File(plugin.context.cacheDir, DOCUMENTS_DIRECTORY_NAME)
        directory.mkdirs()
        return directory
    }

    private fun getFileByPath(path: String): File? {
        val uri = Uri.parse(path)
        val scheme = uri.scheme
        if (scheme != null && scheme != ContentResolver.SCHEME_FILE) {
            return null
        }
        val filePath = (if (scheme == null) path else uri.path) ?: return null
        return File(filePath)
    }

    @ChecksSdkIntAtLeast(extension = Build.VERSION_CODES.S, api = MIN_SDK_EXTENSION_VERSION)
    private fun isSupported(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
            SdkExtensions.getExtensionVersion(Build.VERSION_CODES.S) >= MIN_SDK_EXTENSION_VERSION
    }

    companion object {
        private const val DOCUMENTS_DIRECTORY_NAME = "capawesome_capacitor_pdf_annotator_documents"

        // Annotation support of the PDF system module (`androidx.pdf.ink.EditablePdfViewerFragment` requirement).
        private const val MIN_SDK_EXTENSION_VERSION = 18

        @JvmStatic
        fun writeEdits(handle: PdfWriteHandle, file: File, callback: EmptyCallback) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    handle.use { writeHandle ->
                        ParcelFileDescriptor.open(
                            file,
                            ParcelFileDescriptor.MODE_READ_WRITE or ParcelFileDescriptor.MODE_CREATE or ParcelFileDescriptor.MODE_TRUNCATE
                        ).use { fileDescriptor -> writeHandle.writeTo(fileDescriptor) }
                    }
                    withContext(Dispatchers.Main) { callback.success() }
                } catch (exception: Exception) {
                    withContext(Dispatchers.Main) { callback.error(exception) }
                }
            }
        }
    }
}
