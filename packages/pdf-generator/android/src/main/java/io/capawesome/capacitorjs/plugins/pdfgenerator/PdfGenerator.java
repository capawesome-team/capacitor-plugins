package io.capawesome.capacitorjs.plugins.pdfgenerator;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.print.PdfGeneratorPrintDriver;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.CustomException;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.options.GenerateFromHtmlOptions;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.options.GenerateFromUrlOptions;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.options.GenerateOptions;
import io.capawesome.capacitorjs.plugins.pdfgenerator.classes.results.GenerateResult;
import io.capawesome.capacitorjs.plugins.pdfgenerator.interfaces.NonEmptyResultCallback;
import java.io.File;
import java.util.ArrayDeque;

public class PdfGenerator {

    private static final String DOCUMENTS_DIRECTORY_NAME = "capawesome_capacitor_pdf_generator_documents";
    private static final int MARGIN_IN_MILS = 278; // ~20 pt
    private static final int SCREEN_RESOLUTION_IN_DPI = 96;
    private static final long SETTLE_DELAY_IN_MILLISECONDS = 500;

    @NonNull
    private final Handler handler = new Handler(Looper.getMainLooper());

    @NonNull
    private final PdfGeneratorPlugin plugin;

    @NonNull
    private final ArrayDeque<Runnable> queue = new ArrayDeque<>();

    @Nullable
    private NonEmptyResultCallback<GenerateResult> currentCallback;

    @Nullable
    private GenerateOptions currentOptions;

    @Nullable
    private Runnable currentSettleRunnable;

    @Nullable
    private Runnable currentTimeoutRunnable;

    @Nullable
    private WebView currentWebView;

    private boolean generating = false;

    public PdfGenerator(@NonNull PdfGeneratorPlugin plugin) {
        this.plugin = plugin;
        cleanUpDocumentsDirectory();
    }

    public void generateFromHtml(@NonNull GenerateFromHtmlOptions options, @NonNull NonEmptyResultCallback<GenerateResult> callback) {
        enqueue(() -> {
            WebView webView = startGeneration(options, callback);
            webView.loadDataWithBaseURL(options.getBaseUrl(), options.getHtml(), "text/html", "UTF-8", null);
        });
    }

    public void generateFromUrl(@NonNull GenerateFromUrlOptions options, @NonNull NonEmptyResultCallback<GenerateResult> callback) {
        enqueue(() -> {
            WebView webView = startGeneration(options, callback);
            webView.loadUrl(options.getUrl());
        });
    }

    private void cleanUpDocumentsDirectory() {
        File[] files = getDocumentsDirectory().listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            file.delete();
        }
    }

    private void createDocument() {
        GenerateOptions options = currentOptions;
        WebView webView = currentWebView;
        if (options == null || webView == null) {
            return;
        }
        PrintAttributes attributes = new PrintAttributes.Builder()
            .setMediaSize(options.getMediaSize())
            .setMinMargins(new PrintAttributes.Margins(MARGIN_IN_MILS, MARGIN_IN_MILS, MARGIN_IN_MILS, MARGIN_IN_MILS))
            .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600))
            .build();
        File file = new File(getDocumentsDirectory(), options.getFileName());
        PrintDocumentAdapter adapter = webView.createPrintDocumentAdapter(options.getFileName());
        NonEmptyResultCallback<GenerateResult> callback = currentCallback;
        PdfGeneratorPrintDriver.print(
            adapter,
            attributes,
            file,
            new PdfGeneratorPrintDriver.Callback() {
                @Override
                public void onFailure(@Nullable String message) {
                    handler.post(() -> {
                        if (currentCallback != callback) {
                            return;
                        }
                        finishWithError(
                            message == null ? CustomExceptions.GENERATION_FAILED : new CustomException("GENERATION_FAILED", message)
                        );
                    });
                }

                @Override
                public void onSuccess() {
                    handler.post(() -> {
                        if (currentCallback != callback) {
                            return;
                        }
                        finishWithSuccess(new GenerateResult(file));
                    });
                }
            }
        );
    }

    @SuppressLint("SetJavaScriptEnabled")
    @NonNull
    private WebView createWebView(@NonNull GenerateOptions options) {
        WebView webView = new WebView(plugin.getContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        int widthInPixels = Math.round((options.getMediaSize().getWidthMils() / 1000f) * SCREEN_RESOLUTION_IN_DPI);
        int heightInPixels = Math.round((options.getMediaSize().getHeightMils() / 1000f) * SCREEN_RESOLUTION_IN_DPI);
        webView.measure(
            View.MeasureSpec.makeMeasureSpec(widthInPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(heightInPixels, View.MeasureSpec.EXACTLY)
        );
        webView.layout(0, 0, widthInPixels, heightInPixels);
        webView.setWebViewClient(createWebViewClient());
        return webView;
    }

    @NonNull
    private WebViewClient createWebViewClient() {
        return new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (view == currentWebView) {
                    scheduleDocumentCreation();
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (view == currentWebView && request.isForMainFrame()) {
                    finishWithError(CustomExceptions.LOAD_FAILED);
                }
            }
        };
    }

    private void enqueue(@NonNull Runnable task) {
        handler.post(() -> {
            queue.add(task);
            processQueue();
        });
    }

    @Nullable
    private NonEmptyResultCallback<GenerateResult> finishGeneration() {
        NonEmptyResultCallback<GenerateResult> callback = currentCallback;
        if (callback == null) {
            return null;
        }
        if (currentSettleRunnable != null) {
            handler.removeCallbacks(currentSettleRunnable);
            currentSettleRunnable = null;
        }
        if (currentTimeoutRunnable != null) {
            handler.removeCallbacks(currentTimeoutRunnable);
            currentTimeoutRunnable = null;
        }
        if (currentWebView != null) {
            currentWebView.destroy();
            currentWebView = null;
        }
        currentCallback = null;
        currentOptions = null;
        generating = false;
        processQueue();
        return callback;
    }

    private void finishWithError(@NonNull Exception exception) {
        NonEmptyResultCallback<GenerateResult> callback = finishGeneration();
        if (callback != null) {
            callback.error(exception);
        }
    }

    private void finishWithSuccess(@NonNull GenerateResult result) {
        NonEmptyResultCallback<GenerateResult> callback = finishGeneration();
        if (callback != null) {
            callback.success(result);
        }
    }

    @NonNull
    private File getDocumentsDirectory() {
        File directory = new File(plugin.getContext().getCacheDir(), DOCUMENTS_DIRECTORY_NAME);
        directory.mkdirs();
        return directory;
    }

    private void handleTimeout() {
        finishWithError(CustomExceptions.TIMEOUT);
    }

    private void processQueue() {
        if (generating) {
            return;
        }
        Runnable task = queue.poll();
        if (task == null) {
            return;
        }
        generating = true;
        task.run();
    }

    private void scheduleDocumentCreation() {
        if (currentSettleRunnable != null) {
            handler.removeCallbacks(currentSettleRunnable);
        }
        currentSettleRunnable = this::createDocument;
        handler.postDelayed(currentSettleRunnable, SETTLE_DELAY_IN_MILLISECONDS);
    }

    @NonNull
    private WebView startGeneration(@NonNull GenerateOptions options, @NonNull NonEmptyResultCallback<GenerateResult> callback) {
        currentCallback = callback;
        currentOptions = options;
        WebView webView = createWebView(options);
        currentWebView = webView;
        currentTimeoutRunnable = this::handleTimeout;
        handler.postDelayed(currentTimeoutRunnable, options.getTimeout());
        return webView;
    }
}
