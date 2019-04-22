package com.github.lithualien.gui;

import com.github.lithualien.arff.Emotions;

import javax.swing.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private JPanel mainPanel;

    // TextFields
    private JTextField inputDirectory = new JTextField();

    // Buttons
    private JButton selectDirectory = new JButton("Direktorija");
    private JButton engagePauseAndUnpause = new JButton("Pause/Unpause");
    private JButton indication = new JButton("Rasti pozymius");
    private JButton startDecrypting = new JButton("Atkoduoti");
    private JCheckBox[] jCheckBox;
   // private JButton sosStop = new JButton("SOS STOP");

    private File file = new File("C:/Users/Admin/Desktop/arff/arff");
    private List<String> emotionList = new ArrayList<>();
    private Emotions emotions = new Emotions();

    // Progress bar
    private JProgressBar codingStatus = new JProgressBar();

    private int i = 0;
    public void executeProgram() {
        setUpGUI();
        setStartDecrypting();
        setIndications();
    }

    private void setUpGUI() {
        setMainPanel();
        addToMainPanel();
        setInputDirectory();
        setJTextField();
        setJButton();
        setJProgressBar();
    }

    private void setMainPanel() {
        add(mainPanel); // setting up the mainPanel for usage.
        setVisible(true);
        mainPanel.setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 220);
        setResizable(false);
    }

    private void addToMainPanel() {
        mainPanel.add(selectDirectory); // adding components to the mainPanel;
        mainPanel.add(engagePauseAndUnpause);
        mainPanel.add(inputDirectory);
        mainPanel.add(startDecrypting);
        mainPanel.add(indication);
        //mainPanel.add(sosStop);
        mainPanel.add(codingStatus);
    }

    private void addToMainPanelArrayMembers() {
        System.out.println(i);
        mainPanel.add(jCheckBox[i]);
    }

    private void setJCheckBox(String name) {
        jCheckBox[i].setBounds(i * 40 + 20, 110, 140, 30);
        jCheckBox[i].setName(name);
        System.out.println(name + " " + i);

    }

    private void setJTextField() {
        inputDirectory.setBounds(20, 20, 300, 30); // setting up TextField properties.
    }

    private void setJButton() {
        selectDirectory.setBounds(330, 20, 130, 30); // setting up JButton properties.
        //engagePauseAndUnpause.setBounds(320, 60, 140, 30);
        indication.setBounds(20, 60, 140, 30);
        //startDecrypting.setBounds(170, 60, 140, 30);
       // sosStop.setBounds(20, 100, 440, 30);

    }

    private void setInputDirectory() {
        selectDirectory.addActionListener(e -> // inputDirectory button action listener
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
        codingStatus.setBounds(20, 150, 440, 30);
    }

    private void setIndications() {
        indication.addActionListener(e ->
        {
            emotionList = emotions.getEmotionList(file);
        });
    }

    private void printList() {
        jCheckBox = new JCheckBox[emotionList.size()];
        for(i = 0; i < emotionList.size(); i++) {
            addToMainPanelArrayMembers();
            setJCheckBox(emotionList.get(i));
        }
    }

    private void startEncrypting() {
        createFirstThread();
    }

    private void setStartDecrypting() {
        startDecrypting.addActionListener(e ->
        {
            startDecrypting();
        });
    }

    private void startDecrypting() {
        createFirstThread();
        createDecryptingThread();
    }

    private void createFirstThread() {
        Thread t1 = new Thread(() -> {

        });
        t1.start();
    }

    private void createDecryptingThread() {
        Thread t2 = new Thread(() -> {
        });
        t2.start();
    }

    private void setProgressBar() {
        try {

        }
        catch (Exception e) { }
    }
}
