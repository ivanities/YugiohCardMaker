package files;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FilePath {

    public static final Map<String, String> FILE_PATHS = new HashMap<>();

    public static void main(String... args) {
        File file = new File("resources");
        showFiles(file);
        System.out.println("here");
    }

    private static void showFiles(File file) {
        if (file.isDirectory() && file.listFiles() != null) {
            System.out.println("\n=========================");
            System.out.println("Directory: " + file.getName());
            System.out.println("=========================");
            for (File f : file.listFiles()) {
                System.out.println(file.getPath());
//                if (f.isFile()) {
//                    FILE_PATHS.put(file.getName(), file.getPath());
//                }
                showFiles(f);
            }
        } else {
            FILE_PATHS.put(file.getName(), file.getPath());
            System.out.println("File: " + file.getName());
        }
    }
}
