package files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FindJavaFiles {

    public static List<File> findFiles(String dirPath) {
        List<File> files = new ArrayList<>();

        File dir = new File(dirPath);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                // Do something with child
                if (file.isDirectory()) {
                    files.addAll(findFiles(file.getPath()));
                }
                else if (file.isFile()) {
                    if (file.getName().endsWith(".java")) {
                        files.add(file);
                    }
                }
            }
        }
        else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
        }
        return files;
    }
}
