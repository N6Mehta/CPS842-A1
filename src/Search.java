import java.io.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.*;

/**
 * Created by Ni2 on 10/22/2016.
 */


public class Search {

    public ArrayList<String> userInput = new ArrayList<String>();

    public static void main(String args[]) throws IOException {
        String query1 = null;
        App gui = new App();
        JFrame frame = new JFrame("App");
        frame.setContentPane(gui.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        run(gui);
    }

    public static void run(App gui) {
        ArrayList<String> userInput = new ArrayList<String>();
        gui.searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            String list = gui.query;
                System.out.println(list);
                StringTokenizer input = new StringTokenizer(list);
                while(input.hasMoreElements())
                {
                    userInput.add(input.nextElement().toString());
                }
                System.out.println(userInput);
            }
        });
    }
}
