package io.capawesome.capacitorjs.plugins.liveupdate;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    @Test
    public void copyAndReportProgress_messageDoesNotLeakSourcePath() throws Exception {
        File source = new File(tmp.getRoot(), "secret-internal-path.zip");
        File destination = tmp.newFile("dest.zip");
        try {
            LiveUpdateFileScheme.copyAndReportProgress(source, destination, null);
            fail("expected FileNotFoundException");
        } catch (FileNotFoundException e) {
            assertFalse(
                "exception message must not include the absolute source path",
                e.getMessage() != null && e.getMessage().contains(source.getAbsolutePath())
            );
        }
    }

    @Test
    public void resolveSandboxedFile_acceptsPathInsideAllowedPrefix() throws Exception {
        File sandbox = tmp.newFolder("files");
        File bundle = new File(sandbox, "bundle.zip");
        Files.write(bundle.toPath(), "z".getBytes());

        File resolved = LiveUpdateFileScheme.resolveSandboxedFile(
            bundle.toURI().toString(),
            sandbox.getCanonicalPath()
        );

        assertEquals(bundle.getCanonicalFile(), resolved);
    }

    @Test
    public void resolveSandboxedFile_acceptsSandboxRootItself() throws Exception {
        File sandbox = tmp.newFolder("files");

        File resolved = LiveUpdateFileScheme.resolveSandboxedFile(
            sandbox.toURI().toString(),
            sandbox.getCanonicalPath()
        );

        assertEquals(sandbox.getCanonicalFile(), resolved);
    }

    @Test
    public void resolveSandboxedFile_rejectsMalformedUri() throws Exception {
        try {
            LiveUpdateFileScheme.resolveSandboxedFile("file:// not a uri", tmp.getRoot().getCanonicalPath());
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertTrue(true);
        }
    }

    @Test
    public void resolveSandboxedFile_rejectsNonFileScheme() throws Exception {
        try {
            LiveUpdateFileScheme.resolveSandboxedFile("https://example.com/bundle.zip", tmp.getRoot().getCanonicalPath());
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertTrue(true);
        }
    }

    @Test
    public void resolveSandboxedFile_rejectsContentScheme() throws Exception {
        try {
            LiveUpdateFileScheme.resolveSandboxedFile("content://com.evil/bundle.zip", tmp.getRoot().getCanonicalPath());
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            assertTrue(true);
        }
    }

    @Test
    public void resolveSandboxedFile_rejectsPathOutsideSandbox() throws Exception {
        File sandbox = tmp.newFolder("files");
        File outside = tmp.newFile("outside.zip");
        try {
            LiveUpdateFileScheme.resolveSandboxedFile(
                outside.toURI().toString(),
                sandbox.getCanonicalPath()
            );
            fail("expected SecurityException");
        } catch (SecurityException expected) {
            assertTrue(true);
        }
    }

    @Test
    public void resolveSandboxedFile_rejectsParentTraversal() throws Exception {
        File sandbox = tmp.newFolder("files");
        File outside = tmp.newFile("outside.zip");
        Files.write(outside.toPath(), "x".getBytes());
        // Build a uri that walks out of the sandbox via ".."
        String escapingUri = sandbox.toURI().toString() + "../" + outside.getName();
        try {
            LiveUpdateFileScheme.resolveSandboxedFile(escapingUri, sandbox.getCanonicalPath());
            fail("expected SecurityException — parent-directory traversal must be rejected after canonicalisation");
        } catch (SecurityException expected) {
            assertTrue(true);
        }
    }

    @Test
    public void resolveSandboxedFile_rejectsPrefixCollision() throws Exception {
        File sandbox = tmp.newFolder("files");
        // Sibling dir whose path string starts with the sandbox path string but is not inside it
        File sibling = tmp.newFolder("files-evil");
        File bundle = new File(sibling, "bundle.zip");
        Files.write(bundle.toPath(), "x".getBytes());
        try {
            LiveUpdateFileScheme.resolveSandboxedFile(
                bundle.toURI().toString(),
                sandbox.getCanonicalPath()
            );
            fail("expected SecurityException — '/sandbox-evil' must not match prefix '/sandbox'");
        } catch (SecurityException expected) {
            assertTrue(true);
        }
    }
}
