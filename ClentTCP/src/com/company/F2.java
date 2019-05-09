package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class F2 extends JFrame {
    JLabel label;
    JButton[][] buttons;
    int size;
    F1 firstFrame;
    HandlePress handlePress;
    int counter = 0;
    ArrayList<String> shipPlaces;
    Semaphore semaphore;
    public F2(F1 firstFrame, int size) {
        this.firstFrame = firstFrame;
        this.size = size;
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        createComponents();

        setVisible(true);
    }

    public void createComponents() {
        semaphore = new Semaphore(1);
        shipPlaces = new ArrayList<>();
        handlePress = new HandlePress();
        buttons = new JButton[size][size];
        label = new JLabel("Here is playing area:");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel innerLayout = new JPanel(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.CYAN);
                buttons[i][j].addActionListener(handlePress);
                innerLayout.add(buttons[i][j]);
            }
        }
        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(innerLayout, BorderLayout.CENTER);
        add(mainPanel);
    }

    class HandlePress implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println(counter);
            new Thread(() -> {
                try {
                    semaphore.acquire();
                if (counter < 10) {
                    counter++;
                    JButton tmp = (JButton) e.getSource();
                    String id = "";
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            if (tmp == buttons[i][j]) {
                                id = i + ":" + j;
                                shipPlaces.add(id);
                                tmp.setBackground(Color.RED);
                                tmp.removeActionListener(handlePress);
                                break;
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            buttons[i][j].removeActionListener(handlePress);
                        }
                    }
                    label.setText("Buttons: "+shipPlaces+" were pressed");
                }
                semaphore.release();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }).start();
        }
    }
}
