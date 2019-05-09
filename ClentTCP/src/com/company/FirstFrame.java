
package com.company;


import javax.swing.*;
import java.net.*;
import java.awt.*;
import java.io.*;
import java.util.Date;

public class FirstFrame extends JFrame {

    JLabel label;
    JButton button;
    CommonResource commonResource;

    public FirstFrame() {
        super("First Frame");
        commonResource = new CommonResource();
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        createComponents();

        setVisible(true);
    }

    public void createComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        button = new JButton("Get Data");
        label = new JLabel(new Date() + "");
        button.addActionListener((event) -> {
            new Thread(() -> {
                try {
                    String url ="https://nbu.uz/exchange-rates/json/";
                    URL urlObject = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String tmp;

                    while ((tmp = reader.readLine())!= null)
                    {
                        commonResource.buffer.append(tmp);
                    }
                    reader.close();

                    new SecondFrame(this, commonResource);
                    setVisible(false);

                } catch (Exception e) {
                    System.out.println(e.getClass());
                }
            }).start();
        });

        mainPanel.add(label);
        mainPanel.add(button);
        add(mainPanel);
    }

}