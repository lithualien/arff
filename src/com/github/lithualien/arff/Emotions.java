package com.github.lithualien.arff;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Emotions {

    private Data data = new Data();
    private FileManagement fileManagement = new FileManagement();
    private List<String> emotions = new ArrayList();

     public List<String> getEmotionList(File folder) {
         addEmotionsToList(fileManagement.getAllFiles(folder));
        return emotions;
     }

     private void addEmotionsToList(File[] files) {
         data.setData(files[0].toString());
         for(int i = 0; i <= data.getNumberOfEmotions(); i++) {
             emotions.add(data.getEmotionByIndex(i));
         }
     }
}
