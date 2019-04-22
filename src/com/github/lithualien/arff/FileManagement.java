package com.github.lithualien.arff;

import weka.core.Instance;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

class FileManagement {

    private final String PATH = "arff/output/arff.arff";

    File[] getAllFiles(File folder) {
        return folder.listFiles();
    }

    void setUpFile() {
        deleteFile();
        createFile();
    }

    private void deleteFile() {
        Path path = Paths.get(PATH);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile() {
        try {
            Path path = Paths.get(PATH);
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeToFile(String instance) {
        try {
            Files.write(Paths.get(PATH), instance.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
