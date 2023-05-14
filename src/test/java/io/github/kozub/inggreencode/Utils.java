package io.github.kozub.inggreencode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Utils {

    public static String loadFile(String filePath) throws IOException {
        ClassLoader classLoader = Utils.class.getClassLoader();
        File requestFile = new File(classLoader.getResource(filePath).getFile());
        return Files.readString(requestFile.toPath());
    }
}
