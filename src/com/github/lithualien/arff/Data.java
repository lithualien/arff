package com.github.lithualien.arff;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Data {
    private Instances data;
    private FileManagement fileManagement = new FileManagement();
    private final Object syncObject = new Object();
    private boolean paused = false;
    private boolean stop = false;
    private File[] files;
    private int i;
    private List<SaveDataToList> atrributes = new ArrayList<>();
    private boolean running = true;

    private void setData(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            ArffLoader.ArffReader arffReader = new ArffLoader.ArffReader(reader);
            data = arffReader.getData();
            data.setClassIndex(data.numAttributes() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String tail( File file ) {
        LineIterator lineIterator = null;
        try {
            lineIterator = FileUtils.lineIterator(file,"UTF-8");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String lastLine="";
        while (lineIterator.hasNext()){
            lastLine=  lineIterator.nextLine();
        }
        return lastLine;
    }

    public void getInstances(File folder, List<SaveDataToList> data) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable runnable = () -> {

            files = fileManagement.getAllFiles(folder);
            fileManagement.setUpFile();
            running = true;
            StringBuilder tempor = new StringBuilder();

            for (i = 0; i < files.length - 1; i++) {
                /*synchronized (syncObject) {
                    if (paused) {
                        try {
                            syncObject.wait();
                        }
                        catch (InterruptedException exc) {
                            exc.printStackTrace();
                        }
                    }
                }*/
                //setData(files[i].toString());
                /*if (i == 0) {
                    for (SaveDataToList temp : data) {
                       tempor.append("\n").append(temp.getAttribute());
                    }
                    tempor.append("\n\n@data\n\n");
                }
                else {
                    for (SaveDataToList temp : data) {
                        tempor.append(this.data.get(0).value(temp.getPos())).append(" ");
                    }
                    tempor.append("\n");*/
                    tail(files[i]);

               /* }
                if (stop) {
                    break;
                }*/
            }
            //fileManagement.writeToFile(tempor.toString());
            running = false;
        };
        executorService.submit(runnable);
    }


    public List<SaveDataToList> getAttributes(File folder) {

        files = fileManagement.getAllFiles(folder);

        setData(files[0].toString());

        for(int i = 0; i < getIndexValue(); i++) {
            SaveDataToList temp = new SaveDataToList(
                    i,
                    getData(i)
            );
            atrributes.add(temp);
        }
        return atrributes;
    }

    public void pause() {
        paused = true;
    }

    public void resumeJob() {
        synchronized(syncObject) {
            paused = false;
            syncObject.notify();
        }
    }

    private int getIndexValue() {
        return data.classIndex();
    }

    private String getData(int i) {
        return data.attribute(i).toString();
    }

    public int maxFiles() {
        return files.length;
    }

    public int currentPos() {
        return i;
    }

    public void stop() {
        stop = true;
    }

    public boolean getRunning() {
        return running;
    }
}
