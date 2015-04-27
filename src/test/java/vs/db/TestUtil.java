package vs.db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static junit.framework.Assert.assertEquals;

public class TestUtil {

    public static void assertFile(File expected, File result) throws IOException {
        String str1 = new String(Files.readAllBytes(expected.toPath()));
        String str2 = new String(Files.readAllBytes(result.toPath()));
        str1 = str1.trim().replace("\r\n", "\n");
        str2 = str2.trim().replace("\r\n", "\n");

        assertEquals(str1, str2);
    }

}
