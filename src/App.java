package com.codebind;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rfenechr1 on 18/10/2016.
 */
public class App {
    private JPanel panelMain;
    private JButton searchButton;
    private JRadioButton stopWordRemoval;
    private JRadioButton stemming;
    private JTextField searchfield;
    private JButton documentCacheChangeButton;
    public boolean stopped,stemmed  = false;

    public App() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"This is the result of your search: '"+searchfield.getText()+"'");
            }
        });
        documentCacheChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(stopWordRemoval.isSelected() && !stopped)
                {
                    /*Re run compilation but include stop words HERE*/
                    JOptionPane.showMessageDialog(null,"Document lists have been changed to contain stop words");
                    stopped = true;
                }else if(stopWordRemoval.isSelected() && stopped)
                {
                    /*Re run compilation but remove stop words HERE*/
                    JOptionPane.showMessageDialog(null,"Document lists have been changed to not contain stop words");
                    stopped = false;
                }


                if(stemming.isSelected() && !stemmed)
                {
                    /*Re run compilation but stem the words HERE*/
                    JOptionPane.showMessageDialog(null,"Document lists have had their words stemmed");
                    stemmed = true;
                }else if(stemming.isSelected() && stemmed)
                {
                    /*Re run compilation but do not stem the words HERE*/
                    JOptionPane.showMessageDialog(null,"Document lists did not have their words stemmed");
                    stemmed = false;
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
