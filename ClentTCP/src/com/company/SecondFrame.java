
package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.json.*;
public class SecondFrame extends JFrame {

    FirstFrame firstFrameRef;
    CommonResource commonResourceRef;
    JButton btnReturn;
    JLabel label;
    JButton [] buttons;

    public SecondFrame(FirstFrame firstFrame, CommonResource commonResource) {
        super("Second Frame");
        firstFrameRef =firstFrame;
        commonResourceRef = commonResource;
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        createComponents();

        setVisible(true);
    }

    public void createComponents()
    {
        HandlePress handlePress = new HandlePress();
        int size = 24;
        buttons = new JButton[size];
        label = new JLabel("Currencies:");
        btnReturn = new JButton("Return");
        JPanel innerPanel = new JPanel(new GridLayout(size,1));
        JPanel mainPanel = new JPanel(new BorderLayout());

        try {
            JSONArray jsonArray = new JSONArray(commonResourceRef.buffer.toString());

            for (int i = 0; i < size; i++) {
                JSONObject obj = new JSONObject(jsonArray.getJSONObject(i).toString());
                buttons[i] = new JButton(obj.getString("title") + " ,"+ obj.getString("code"));
                buttons[i].addActionListener(handlePress);
                innerPanel.add(buttons[i]);
            }

        }catch (Exception e)
        {
            System.out.println(e.getClass());
        }
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        mainPanel.add(label,BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(btnReturn,BorderLayout.SOUTH);
        add(mainPanel);
    }

    class HandlePress implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            new Thread(()->{
                JButton tmp = (JButton)e.getSource();

                for(int i =0 ; i< buttons.length;i++)
                {
                    if(buttons[i] == tmp)
                    {
                        commonResourceRef.selectedID = i;
                        break;
                    }
                }
                try {
                    new ThirdFrame(SecondFrame.this, commonResourceRef);
                } catch (Exception e1) {
                    System.out.println(e.getClass());
                }
                setVisible(false);
            }).start();

        }
    }

}