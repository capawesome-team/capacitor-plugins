package io.capawesome.capacitorjs.plugins.liveupdate;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class LiveUpdateFileSchemeTest {

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    @Test
    public void copyAndReportProgress_copiesBytesIdentically() throws Exception {
        File source = tmp.newFile("bundle.zip");
        byte[] payload = new byte[16 * 1024 + 7];
        new Random(0).nextBytes(payload);
        Files.write(source.toPath(), payload);
        File destination = tmp.newFile("dest.zip");

        List<long[]> events = new ArrayList<>();
        long copied = LiveUpdateFileScheme.copyAndReportProgress(source, destination, (d, t) -> events.add(new long[] { d, t }));

        assertEquals(payload.length, copied);
        assertArrayEquals(payload, Files.readAllBytes(destination.toPath()));
        assertFalse(events.isEmpty());
        long[] last = events.get(events.size() - 1);
        assertEquals(payload.length, last[0]);
        assertEquals(payload.length, last[1]);
    }

    @Test(expected = FileNotFoundException.class)
    public void copyAndReportProgress_throwsWhenSourceMissing() throws Exception {
        File source = new File(tmp.getRoot(), "missing.zip");
        File destination = tmp.newFile("dest.zip");
        LiveUpdateFileScheme.copyAndReportProgress(source, destination, null);
    }

    @Test
    public void copyAndReportProgress_acceptsNullListener() throws Exception {
        File source = tmp.newFile("bundle.zip");
        Files.write(source.toPath(), "hello".getBytes());
        File destination = tmp.newFile("dest.zip");

        long copied = LiveUpdateFileScheme.copyAndReportProgress(source, destination, null);

        assertEquals(5, copied);
    }
}
