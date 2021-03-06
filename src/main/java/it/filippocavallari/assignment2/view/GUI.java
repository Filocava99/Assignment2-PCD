package it.filippocavallari.assignment2.view;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private final TextArea textArea = new TextArea();

    private final JButton startButton = new JButton("Start");
    private final JButton stopButton = new JButton("Stop");
    private final JFileChooser fileChooser = new JFileChooser();
    public GUI(){
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        JPanel gridLayout = new JPanel();
        gridLayout.setLayout(new GridLayout(2,1));
        JPanel innerGridLayout = new JPanel(new GridLayout(1,2));
        innerGridLayout.add("Next", startButton);
        innerGridLayout.add("Stop", stopButton);
        gridLayout.add("TextArea", textArea);
        gridLayout.add("innerGrid", innerGridLayout);
        this.add(gridLayout);
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }
}
