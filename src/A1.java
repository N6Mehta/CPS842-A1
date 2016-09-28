/*
  Created by Nitish on 9/21/2016.
 */
import java.io.*;
import java.util.*;

public class A1 {
    public static void main(String args[]) throws IOException {
        Map map1 = new HashMap();
        Map<String, List<String>> map2 = new HashMap<>();
       List<String> val = new ArrayList<String>();
        Scanner scan = null;
        Scanner scan2 = null;
        Scanner scan3 = null;
        //Scan through cacm.all file for words and count words FOR DICTIONARY
        try {
            scan = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Ni2\\IdeaProjects\\CPS842 A1\\cacm\\cacm.all")));
            String next;
            String n;
            do {
                next = scan.next();
                //if next valye is .W or .T take the values after it
                if (next.equals(".W") || (next.equals(".T"))) {

                    next = scan.next();
                    //while next value is not .B
                    while (!(next.equals(".B"))) {
                        n = next.replaceAll("[^a-zA-z]", "").toLowerCase();
                        if (map1.get(n) == null) map1.put(n, 1);
                        else {
                            int newWord = Integer.valueOf(String.valueOf(map1.get(n)));
                            newWord++;
                            map1.put(n, newWord);
                        }
                        next = scan.next();
                    }
                }
            } while (scan.hasNext());
        } finally {
            if (scan != null) {
                scan.close();
            }
        }


        //Scan through common_words file to remove common words
        try {
            scan2 = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Ni2\\IdeaProjects\\CPS842 A1\\cacm\\common_words")));
            String st;
            do {
                st = scan2.next();
                if (map1.get(st) != null) {
                    map1.remove(st);
                }

            } while (scan2.hasNext());
        } finally {
            if (scan2 != null) {
                scan2.close();
            }
        }
            //Write hashmap to Dictionary.txt file
            PrintStream print = new PrintStream(new FileOutputStream("Dictionary.txt", true), true);

            List<String> listVal = new ArrayList<String>(map1.keySet());
            Collections.sort(listVal);
            for (int i = 0; i < listVal.size(); i++) {
                print.println(listVal.get(i) + " " + map1.get(listVal.get(i)));
            }

    }


}
