package io.capawesome.capacitorjs.plugins.liveupdate.interfaces;

import androidx.annotation.NonNull;
import java.io.File;

public interface FileVerifier {
    void verify(@NonNull File file) throws Exception;
}
