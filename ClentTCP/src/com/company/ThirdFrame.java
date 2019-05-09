

package com.company;

import javax.swing.*;

import org.json.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThirdFrame extends JFrame {

    SecondFrame secondFrame;
    CommonResource commonResource;
    JLabel label;
    JTextField inputArea;
    JTextArea resultArea;
    JButton btnSubmit, btnClear, btnReturn;
    JSONObject jsonObject;

    public ThirdFrame(SecondFrame secondFrame, CommonResource commonResource) throws Exception {
        super("Second Frame");
        this.secondFrame = secondFrame;
        this.commonResource = commonResource;
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        createComponents();

        setVisible(true);
    }

    public void createComponents() throws Exception {

        JSONArray array = new JSONArray(commonResource.buffer.toString());
        jsonObject = new JSONObject(array.getJSONObject(commonResource.selectedID).toString());
        label = new JLabel("Enter in " + jsonObject.getString("title"));
        btnSubmit = new JButton("Submit");
        btnReturn = new JButton("Return");
        btnClear = new JButton("Clear");
        inputArea = new JTextField();
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JPanel mainPanel = new JPanel(new GridLayout(5, 1));
        JPanel innerPanel = new JPanel(new GridLayout(1, 2));
        innerPanel.add(btnSubmit);
        innerPanel.add(btnClear);
        mainPanel.add(label);
        mainPanel.add(inputArea);
        mainPanel.add(innerPanel);
        mainPanel.add(resultArea);
        mainPanel.add(btnReturn);
        add(mainPanel);
        addActions();
    }

    public void addActions()
    {
        btnReturn.addActionListener((event)->{
            secondFrame.setVisible(true);
            dispose();
        });
        btnClear.addActionListener((event)->{
            inputArea.setText(null);
            resultArea.setText(null);
        });
        btnSubmit.addActionListener((event)->{
            try {
                double curr = jsonObject.getDouble("cb_price");
                double val = Double.parseDouble(String.valueOf(inputArea.getText()));

                double res = curr * val;

                resultArea.setText("Result : "+res + " sums");
            }catch (Exception e)
            {
                System.out.println(e.getClass());
            }
        });
    }
}
/**
 * try {
 * JSONArray array = new JSONArray(commonResource.buffer.toString());
 * JSONObject object = new JSONObject(array.getJSONObject(commonResource.selectedID).toString());
 * <p>
 * double val  = object.getDouble("cb_price");
 * label.setText(object.getDouble("cb_price")+" , "+object.getString("title"));
 * } catch (Exception e) {
 * System.out.println(e.getClass());
 * }
 **/