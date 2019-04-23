package com.github.lithualien.gui;

import com.github.lithualien.arff.Data;
import com.github.lithualien.arff.SaveDataToList;

import javax.swing.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private JPanel mainPanel;

    // TextFields
    private JTextField inputDirectory = new JTextField();

    // Buttons
    private JButton selectDirectory = new JButton("DIRECTORY");
    private JButton unpause = new JButton("UNPAUSE");
    private JButton indication = new JButton("FIND ATTRIBUTES");
    private JButton pause = new JButton("PAUSE");
    private JButton sosStop = new JButton("STOP");
    private JButton start = new JButton("START");
    private DefaultListModel<String> model = new DefaultListModel();
    private JList<String> listAttributes = new JList<>(model);
    private List<SaveDataToList> allAttributes = new ArrayList<>();
    private List<SaveDataToList> selectedAttributes = new ArrayList<>();
    private JScrollPane listScroller = new JScrollPane(listAttributes);
    private File file = new File("C:/Users/Admin/Desktop/arff/arff");
    private Data data = new Data();
    private boolean running = true;
    private JLabel timer = new JLabel("00 : 00");
    private long startTime, pauseStartTime;
    private int min = 0, pauseDuration;

    // Progress bar
    private JProgressBar status = new JProgressBar();

    public void executeProgram() {
        setUpGUI();
        setIndications();
        setUnpause();
        setPause();
        setSosStop();
        setStart();
    }

    private void setUpGUI() {
        setMainPanel();
        addToMainPanel();
        setInputDirectory();
        setJTextField();
        setJButton();
        setJProgressBar();
        setJList();
        setJLabel();
    }

    private void setMainPanel() {
        add(mainPanel); // setting up the mainPanel for usage.
        setVisible(true);
        mainPanel.setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
    }

    private void addToMainPanel() {
        mainPanel.add(selectDirectory); // adding components to the mainPanel;
        mainPanel.add(unpause);
        mainPanel.add(inputDirectory);
        mainPanel.add(pause);
        mainPanel.add(indication);
        mainPanel.add(sosStop);
        mainPanel.add(status);
        mainPanel.add(listAttributes);
        mainPanel.add(listScroller);
        mainPanel.add(start);
        mainPanel.add(timer);
    }

    private void setJTextField() {
        inputDirectory.setBounds(20, 20, 300, 30); // setting up TextField properties.
    }

    private void disableGUI() {
        selectDirectory.setEnabled(false);
        unpause.setEnabled(false);
        indication.setEnabled(false);
        pause.setEnabled(false);
        sosStop.setEnabled(false);
        start.setEnabled(false);
    }

    private void enableGUI() {
        selectDirectory.setEnabled(true);
        unpause.setEnabled(true);
        indication.setEnabled(true);
        pause.setEnabled(true);
        sosStop.setEnabled(true);
        start.setEnabled(true);
    }

    private void setJButton() {
        selectDirectory.setBounds(330, 20, 130, 30);
        unpause.setBounds(320, 60, 140, 30);
        indication.setBounds(20, 60, 140, 30);
        pause.setBounds(170, 60, 140, 30);
        sosStop.setBounds(20, 100, 215, 30);
        start.setBounds(245, 100, 215, 30);

    }

    private void setJList() {
        listAttributes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listAttributes.setLayoutOrientation(JList.VERTICAL);
        listAttributes.setVisibleRowCount(-1);
        listScroller.setViewportView(listAttributes);
        listScroller.setBounds(20, 140, 440, 280);
    }

    private void setJLabel() {
        timer.setBounds(400, 430, 40, 30);
    }

    private void setInputDirectory() {
        selectDirectory.addActionListener(e ->
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(file);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            int returnVal = fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                inputDirectory.setText(file.toString());
            }
        });
    }

    private void setJProgressBar() {
        status.setBounds(20, 430, 370, 30);
    }

    private void setIndications() {
        indication.addActionListener(e ->
        {   model.removeAllElements();
            allAttributes.clear();
            allAttributes = data.getAttributes(file);
            setAllAttributes();
        });
    }
    private void setAllAttributes() {
        disableGUI();
        for (SaveDataToList allAttribute : allAttributes) {
            model.addElement(allAttribute.getAttribute());
        }
        enableGUI();
    }

    private void setUnpause() {
        unpause.addActionListener(e ->
                data.resumeJob());
    }

    private void setPause() {
        pause.addActionListener(e ->
                data.pause());
    }

    private void setSosStop() {
        sosStop.addActionListener(e ->
                data.stop());
    }

    private void setStart() {
        start.addActionListener(e -> {
            selectedAttributes.clear();
            getSelectedItems();
            data.getInstances(file, selectedAttributes);
            running = true;
            startTime = System.nanoTime();
            createFirstThread();
        });

    }

    private void getSelectedItems() {
        int[] selectedIndex = listAttributes.getSelectedIndices();
        List<String> temp = listAttributes.getSelectedValuesList();

        for(int i = 0; i < temp.size(); i++) {
            SaveDataToList tempor = new SaveDataToList(selectedIndex[i], temp.get(i));
            selectedAttributes.add(tempor);
        }
    }


    private void createFirstThread() {
        Thread t1 = new Thread(() -> {
            while(running) {
                setProgressBar(data.maxFiles(), data.currentPos() + 1);
                running = data.getRunning();
                setTimer();
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
    }

    private void setProgressBar(int max, int current) {
        try {
            status.setMaximum(max);
            status.setValue(current);
        }
        catch (Exception e) { e.printStackTrace(); }
    }


    private void setTimer() {
        if(data.getPause()) {


        } else {
            startTime += (data.getPauseEnd() - data.getStartTime());
            long a = (System.nanoTime() - startTime) / 1000000000;
            data.setToZero();
            if(a % 60 == 0 && a > 0)
            {
                min++;
                startTime = System.nanoTime();
                timer.setText(String.format("%02d : %02d", min, a));
            }
            timer.setText(String.format("%02d : %02d", min, a));
        }
    }


}
