package it.filippocavallari.assignment2.view;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private final TextArea textArea = new TextArea();

    public GUI(){
        JButton nextButton = new JButton("Next");
        JButton stopButton = new JButton("Stop");

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

}
