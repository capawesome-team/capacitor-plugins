package io.capawesome.capacitorjs.plugins.camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity {

    private static final String TAG = "CameraActivity";
    private static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(FILENAME_FORMAT, Locale.US);

    private ImageCapture imageCapture;
    private File outputDirectory;
    private ExecutorService cameraExecutor;

    private PreviewView viewFinder;
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> photoPaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        viewFinder = findViewById(R.id.viewFinder);
        recyclerView = findViewById(R.id.recyclerView);
        ImageButton shutterButton = findViewById(R.id.shutterButton);
        Button doneButton = findViewById(R.id.doneButton);

        setupRecyclerView();

        // Set up the capture listener
        shutterButton.setOnClickListener(v -> takePhoto());
        doneButton.setOnClickListener(v -> finishWithResult());

        outputDirectory = getOutputDirectory();
        cameraExecutor = Executors.newSingleThreadExecutor();

        startCamera();
    }

    private void setupRecyclerView() {
        photoAdapter = new PhotoAdapter(photoPaths, position -> {
            photoPaths.remove(position);
            photoAdapter.notifyItemRemoved(position);
            photoAdapter.notifyItemRangeChanged(position, photoPaths.size());
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(photoAdapter);
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder().build();

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Use case binding failed", e);
                Toast.makeText(this, "Failed to initialize camera: " + e.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        if (imageCapture == null) return;

        File photoFile = new File(
            outputDirectory,
            dateFormat.format(System.currentTimeMillis()) + ".jpg"
        );

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    String savedUri = Uri.fromFile(photoFile).toString();
                    photoPaths.add(photoFile.getAbsolutePath());
                    photoAdapter.notifyItemInserted(photoPaths.size() - 1);
                    recyclerView.scrollToPosition(photoPaths.size() - 1);
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    Log.e(TAG, "Photo capture failed: " + exception.getMessage(), exception);
                    Toast.makeText(CameraActivity.this, "Photo capture failed", Toast.LENGTH_SHORT).show();
                }
            }
        );
    }

    private void finishWithResult() {
        Intent data = new Intent();
        data.putStringArrayListExtra("photoPaths", photoPaths);
        setResult(RESULT_OK, data);
        finish();
    }

    private File getOutputDirectory() {
        File[] externalMediaDirs = getExternalMediaDirs();
        if (externalMediaDirs.length > 0 && externalMediaDirs[0] != null) {
            File appDir = new File(externalMediaDirs[0], getResources().getString(R.string.app_name));
            if (appDir.exists() || appDir.mkdirs()) {
                return appDir;
            }
        }
        return getFilesDir();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}
