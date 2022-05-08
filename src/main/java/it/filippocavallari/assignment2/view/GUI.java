package it.filippocavallari.assignment2.view;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private final TextArea textArea = new TextArea();

    private final JButton nextButton = new JButton("Next");
    private final JButton stopButton = new JButton("Stop");
    public GUI(){
        JPanel gridLayout = new JPanel();
        gridLayout.setLayout(new GridLayout(2,1));
        JPanel innerGridLayout = new JPanel(new GridLayout(1,2));
        innerGridLayout.add("Next", nextButton);
        innerGridLayout.add("Stop", stopButton);
        gridLayout.add("TextArea", textArea);
        gridLayout.add("innerGrid", innerGridLayout);
        this.add(gridLayout);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }
}
