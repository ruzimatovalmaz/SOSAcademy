package com.company;

import javax.swing.*;
import java.awt.*;

public class F1 extends JFrame {

    JTextField textField;
    JButton btnSubmit;

    public F1() {
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        createComponents();

        setVisible(true);
    }

    public void createComponents() {
        textField = new JTextField();
        btnSubmit = new JButton("Submit");
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(textField);
        panel.add(btnSubmit);
        btnSubmit.addActionListener((e)->{
            new Thread(()->{
                int size =0;
                String tmp = textField.getText();
                size = Integer.parseInt(String.valueOf(tmp));
                new F2(this, size);
                setVisible(false);
            }).start();
        });
        add(panel);
    }


}
