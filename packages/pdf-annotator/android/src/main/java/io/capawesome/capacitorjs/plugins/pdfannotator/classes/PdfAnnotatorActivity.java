package io.capawesome.capacitorjs.plugins.pdfannotator.classes;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresExtension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.pdf.ExperimentalPdfApi;
import androidx.pdf.PdfWriteHandle;
import com.getcapacitor.Logger;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import io.capawesome.capacitorjs.plugins.pdfannotator.PdfAnnotator;
import io.capawesome.capacitorjs.plugins.pdfannotator.R;
import io.capawesome.capacitorjs.plugins.pdfannotator.interfaces.EmptyCallback;
import java.io.File;

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 18)
@OptIn(markerClass = ExperimentalPdfApi.class)
public class PdfAnnotatorActivity extends AppCompatActivity implements PdfAnnotatorFragment.Listener {

    public static final String EXTRA_INPUT_PATH = "inputPath";
    public static final String EXTRA_OUTPUT_PATH = "outputPath";
    public static final int RESULT_LOAD_FAILED = RESULT_FIRST_USER;
    public static final int RESULT_SAVE_FAILED = RESULT_FIRST_USER + 1;

    private static final String FRAGMENT_TAG = "PdfAnnotatorFragment";
    private static final String TAG = "PdfAnnotatorPlugin";

    private PdfAnnotatorFragment fragment;
    private File inputFile;
    private File outputFile;
    private MenuItem saveMenuItem;

    @Override
    public void onApplyEditsFailed(@NonNull Throwable throwable) {
        Logger.error(TAG, throwable.getMessage(), throwable);
        finishWithResult(RESULT_SAVE_FAILED);
    }

    @Override
    public void onApplyEditsSuccess(@NonNull PdfWriteHandle handle) {
        PdfAnnotator.writeEdits(
            handle,
            outputFile,
            new EmptyCallback() {
                @Override
                public void success() {
                    Intent data = new Intent().putExtra(EXTRA_OUTPUT_PATH, outputFile.getAbsolutePath());
                    setResult(RESULT_OK, data);
                    finish();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    finishWithResult(RESULT_SAVE_FAILED);
                }
            }
        );
    }

    @Override
    public void onEditModeChanged(boolean editModeEnabled) {
        saveMenuItem.setVisible(editModeEnabled);
    }

    @Override
    public void onLoadDocumentError(@NonNull Throwable throwable) {
        Logger.error(TAG, throwable.getMessage(), throwable);
        finishWithResult(RESULT_LOAD_FAILED);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputFile = new File(getIntent().getStringExtra(EXTRA_INPUT_PATH));
        outputFile = new File(getIntent().getStringExtra(EXTRA_OUTPUT_PATH));
        setContentView(createContentView());
        fragment = getOrCreateFragment(savedInstanceState);
        fragment.setListener(this);
        saveMenuItem.setVisible(fragment.isEditModeEnabled());
        getOnBackPressedDispatcher().addCallback(
            this,
            new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    handleClose();
                }
            }
        );
    }

    @NonNull
    private LinearLayout createContentView() {
        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setFitsSystemWindows(true);
        contentView.addView(
            createToolbar(),
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        FragmentContainerView fragmentContainerView = new FragmentContainerView(this);
        fragmentContainerView.setId(R.id.capawesome_pdf_annotator_fragment_container);
        contentView.addView(fragmentContainerView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        return contentView;
    }

    @NonNull
    private MaterialToolbar createToolbar() {
        MaterialToolbar toolbar = new MaterialToolbar(this);
        toolbar.setTitle(inputFile.getName());
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_clear_material);
        toolbar.setNavigationContentDescription("Close");
        toolbar.setNavigationOnClickListener(view -> handleClose());
        saveMenuItem = toolbar.getMenu().add("Save");
        saveMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        saveMenuItem.setOnMenuItemClickListener(item -> {
            handleSave();
            return true;
        });
        return toolbar;
    }

    private void finishWithResult(int resultCode) {
        setResult(resultCode);
        finish();
    }

    @NonNull
    private PdfAnnotatorFragment getOrCreateFragment(@Nullable Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            PdfAnnotatorFragment restoredFragment = (PdfAnnotatorFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
            if (restoredFragment != null) {
                return restoredFragment;
            }
        }
        PdfAnnotatorFragment fragment = new PdfAnnotatorFragment();
        fragmentManager.beginTransaction().add(R.id.capawesome_pdf_annotator_fragment_container, fragment, FRAGMENT_TAG).commitNow();
        fragment.setDocumentUri(Uri.fromFile(inputFile));
        return fragment;
    }

    private void handleClose() {
        if (fragment.hasUnsavedChanges()) {
            showDiscardChangesDialog();
            return;
        }
        finishWithResult(RESULT_CANCELED);
    }

    private void handleSave() {
        if (fragment.isApplyEditsInProgress()) {
            return;
        }
        if (!fragment.hasUnsavedChanges()) {
            finishWithResult(RESULT_CANCELED);
            return;
        }
        saveMenuItem.setEnabled(false);
        fragment.applyDraftEdits();
    }

    private void showDiscardChangesDialog() {
        new MaterialAlertDialogBuilder(this)
            .setTitle(androidx.pdf.R.string.discard_changes_dialog_title)
            .setMessage(androidx.pdf.R.string.discard_changes_dialog_message)
            .setPositiveButton(androidx.pdf.R.string.discard_button, (dialog, which) -> finishWithResult(RESULT_CANCELED))
            .setNegativeButton(androidx.pdf.R.string.keep_editing_button, null)
            .show();
    }
}
