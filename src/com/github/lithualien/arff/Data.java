package com.github.lithualien.arff;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Data {
    private Instances data;
    private FileManagement fileManagement = new FileManagement();

    void setData(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArffLoader.ArffReader arffReader = new ArffLoader.ArffReader(reader);
            data = arffReader.getData();
            data.setClassIndex(data.numAttributes() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getInstances(File folder, String emotion) {
         File[] files = fileManagement.getAllFiles(folder);
         fileManagement.setUpFile();
         for(int i = 0; i < files.length - 1; i++) {
             setData(files[i].toString());
             if(getEmotion().equals(emotion)) {
                 if(i == 0) {
                     fileManagement.writeToFile("\n" + getData());
                 } else {
                     fileManagement.writeToFile("\n" + getDataAttribute().toString());
                 }
                 System.out.println(files[i].toString());
             }
         }
    }

    private Instance getDataAttribute() {
        return data.get(0);
    }

    Instances getData() {
        return data;
    }

    private int getIndexValue() {
        return data.classIndex();
    }

    int getNumberOfEmotions() {
         return data.attribute(getIndexValue()).numValues() - 1;
    }

    String getEmotionByIndex(int index) {
         return data.attribute(getIndexValue()).value(index);
    }

    String getEmotion() {
        return data.get(0).stringValue(data.classIndex());
    }
}
