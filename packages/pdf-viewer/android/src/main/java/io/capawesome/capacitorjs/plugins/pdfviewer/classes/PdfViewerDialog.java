package io.capawesome.capacitorjs.plugins.pdfviewer.classes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.core.view.WindowInsetsControllerCompat;
import com.getcapacitor.Logger;
import com.github.barteksc.pdfviewer.PDFView;
import io.capawesome.capacitorjs.plugins.pdfviewer.classes.options.OpenOptions;
import java.io.File;

public class PdfViewerDialog extends Dialog {

    public interface Listener {
        void onClosed(@NonNull PdfViewerDialog dialog);
        void onLoadError(@NonNull PdfViewerDialog dialog, @NonNull Throwable throwable);
        void onLoaded();
        void onPageChanged(int page);
    }

    private static final String TAG = "PdfViewerPlugin";
    private static final int TOOLBAR_HEIGHT_IN_DP = 56;

    @NonNull
    private final File file;

    private int lastPage;

    @NonNull
    private final Listener listener;

    private boolean loadFailed = false;

    @NonNull
    private final OpenOptions options;

    public PdfViewerDialog(@NonNull Context context, @NonNull File file, @NonNull OpenOptions options, @NonNull Listener listener) {
        super(context, android.R.style.Theme_Material_Light_NoActionBar);
        this.file = file;
        this.lastPage = options.getPage();
        this.listener = listener;
        this.options = options;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createContentView());
        setOnDismissListener(dialog -> handleDismiss());
        applyStatusBarAppearance();
    }

    private void applyStatusBarAppearance() {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        window.setStatusBarColor(Color.WHITE);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(window, window.getDecorView());
        controller.setAppearanceLightStatusBars(true);
    }

    @NonNull
    private LinearLayout createContentView() {
        LinearLayout contentView = new LinearLayout(getContext());
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setFitsSystemWindows(true);
        contentView.setBackgroundColor(Color.WHITE);
        contentView.addView(
            createToolbar(),
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(TOOLBAR_HEIGHT_IN_DP))
        );
        contentView.addView(createPdfView(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        return contentView;
    }

    @NonNull
    private PDFView createPdfView() {
        PDFView pdfView = new PDFView(getContext(), null);
        pdfView
            .fromFile(file)
            .defaultPage(options.getPage() - 1)
            .password(options.getPassword())
            .onLoad(pageCount -> listener.onLoaded())
            .onError(this::handleLoadError)
            .onPageChange((page, pageCount) -> handlePageChange(page + 1))
            .load();
        return pdfView;
    }

    @NonNull
    private LinearLayout createToolbar() {
        LinearLayout toolbar = new LinearLayout(getContext());
        toolbar.setOrientation(LinearLayout.HORIZONTAL);
        toolbar.setGravity(Gravity.CENTER_VERTICAL);
        toolbar.setBackgroundColor(Color.WHITE);
        toolbar.setPadding(dpToPx(8), 0, dpToPx(8), 0);

        TextView closeButton = new TextView(getContext());
        closeButton.setText("✕");
        closeButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        closeButton.setTextColor(Color.BLACK);
        closeButton.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
        closeButton.setClickable(true);
        closeButton.setOnClickListener(view -> dismiss());
        toolbar.addView(closeButton);

        TextView titleView = new TextView(getContext());
        titleView.setText(options.getTitle() == null ? file.getName() : options.getTitle());
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setTextColor(Color.BLACK);
        titleView.setMaxLines(1);
        titleView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        titleView.setPadding(dpToPx(8), 0, dpToPx(8), 0);
        toolbar.addView(titleView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        if (options.getShowShareButton()) {
            ImageView shareButton = new ImageView(getContext());
            shareButton.setImageResource(android.R.drawable.ic_menu_share);
            shareButton.setContentDescription("Share");
            shareButton.setColorFilter(Color.BLACK);
            shareButton.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
            shareButton.setClickable(true);
            shareButton.setOnClickListener(view -> handleShare());
            toolbar.addView(shareButton, new LinearLayout.LayoutParams(dpToPx(40), dpToPx(40)));
        }

        return toolbar;
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getContext().getResources().getDisplayMetrics().density);
    }

    private void handleDismiss() {
        if (!loadFailed) {
            listener.onClosed(this);
        }
    }

    private void handleLoadError(@NonNull Throwable throwable) {
        loadFailed = true;
        listener.onLoadError(this, throwable);
        dismiss();
    }

    private void handleShare() {
        try {
            Context context = getContext();
            String authority = context.getPackageName() + ".fileprovider";
            Uri uri = FileProvider.getUriForFile(context, authority, file);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            String title = options.getTitle() == null ? file.getName() : options.getTitle();
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(intent, null));
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    private void handlePageChange(int page) {
        if (page == lastPage) {
            return;
        }
        lastPage = page;
        listener.onPageChanged(page);
    }
}
