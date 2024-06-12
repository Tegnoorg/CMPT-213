package ca.cmpt213.as2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private List<File> feedbackFiles;

    public FileHandler(String directoryPath) throws IOException {
        File inputPath = new File(directoryPath);
        if(!inputPath.exists() || !inputPath.isDirectory()){
            throw new FileNotFoundException("Input path does not exist or is not a directory");
        }
        feedbackFiles = new ArrayList<>();
    }
}
