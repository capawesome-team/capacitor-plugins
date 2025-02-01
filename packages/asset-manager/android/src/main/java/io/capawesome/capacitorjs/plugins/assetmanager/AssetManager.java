package io.capawesome.capacitorjs.plugins.assetmanager;

import android.util.Base64;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.assetmanager.classes.options.CopyOptions;
import io.capawesome.capacitorjs.plugins.assetmanager.classes.options.ListOptions;
import io.capawesome.capacitorjs.plugins.assetmanager.classes.options.ReadOptions;
import io.capawesome.capacitorjs.plugins.assetmanager.classes.results.ListResult;
import io.capawesome.capacitorjs.plugins.assetmanager.classes.results.ReadResult;
import io.capawesome.capacitorjs.plugins.assetmanager.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.assetmanager.interfaces.NonEmptyCallback;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class AssetManager {

    private final AssetManagerPlugin plugin;

    public AssetManager(@NonNull AssetManagerPlugin plugin) {
        this.plugin = plugin;
    }

    public void copy(@NonNull CopyOptions options, @NonNull EmptyCallback callback) throws IOException {
        String from = options.getFrom();
        String to = options.getTo();

        android.content.res.AssetManager assets = plugin.getContext().getAssets();
        InputStream inputStream = assets.open(from);
        File file = new File(to);
        copyFile(inputStream, file);

        callback.success();
    }

    public void list(@NonNull ListOptions options, @NonNull NonEmptyCallback<ListResult> callback) throws IOException {
        String path = options.getPath();

        android.content.res.AssetManager assets = plugin.getContext().getAssets();
        ArrayList<String> filesList = new ArrayList<>();
        String[] files = assets.list(path);
        if (files != null) {
            Collections.addAll(filesList, files);
        }

        ListResult result = new ListResult(filesList);
        callback.success(result);
    }

    public void read(@NonNull ReadOptions options, @NonNull NonEmptyCallback<ReadResult> callback) throws IOException {
        String encoding = options.getEncoding();
        String path = options.getPath();

        android.content.res.AssetManager assets = plugin.getContext().getAssets();
        InputStream inputStream = assets.open(path);
        String data;
        if (encoding != null && encoding.equals("utf8")) {
            data = readFileAsString(inputStream);
        } else {
            data = readFileAsBase64EncodedData(inputStream);
        }

        ReadResult result = new ReadResult(data);
        callback.success(result);
    }

    private void copyFile(InputStream input, File output) throws IOException {
        FileOutputStream out = new FileOutputStream(output);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        out.close();
        input.close();
    }

    private String readFileAsBase64EncodedData(InputStream inputStream) throws IOException {
        FileInputStream fileInputStream = (FileInputStream) inputStream;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int c;
        while ((c = fileInputStream.read(buffer)) != -1) {
            byteStream.write(buffer, 0, c);
        }
        fileInputStream.close();

        return Base64.encodeToString(byteStream.toByteArray(), Base64.NO_WRAP);
    }

    private String readFileAsString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }

        return outputStream.toString(StandardCharsets.UTF_8.name());
    }
}
