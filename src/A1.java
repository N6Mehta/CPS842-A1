/*
  Created by Nitish on 9/21/2016.
  NITISH PRO-TIP: USE HASH-MAPS. WHAT YOU HAVE IS WRONG.
  Hash-map <word, object> where Object is all things related to that word (i.e. Frequency, Document where it is found)
 */
import java.io.*;
import java.util.*;

public class A1 {
    public static void main(String args[]) throws IOException{
       Map map1 = new HashMap();
       Scanner scan = null;
        try {
            scan = new Scanner(new BufferedReader(new FileReader("C:\\Users\\NituPC\\IdeaProjects\\CPS842-A1\\cacm\\cacm.all"))) ;
            String st;
            st = scan.next();
            String next;

            do{
                next = scan.next();

                if (next.equals(".W"))
                {
                    next = scan.next();
                    String n = next.replaceAll("[^a-zA-Z\\s]", "").toLowerCase();
                    //System.out.println(n);
                    while(!(next.equals(".B"))) {
                        if (map1.get(n) == null) map1.put(n, 1);
                        else {
                            int newWord = Integer.valueOf(String.valueOf(map1.get(n)));
                            newWord++;
                            map1.put(n, newWord);
                        }
                        next = scan.next();
                    }  //st = next;
                }
            }while(scan.hasNext());
        }finally {
            if (scan != null) {
                scan.close();
            }
        }

        PrintStream print = new PrintStream(new FileOutputStream("Document.txt", true), true);

        List<String> listVal = new ArrayList<String>(map1.keySet());
        Collections.sort(listVal);
        for (int i = 0; i < listVal.size(); i++) {
            print.println(listVal.get(i) + " " + map1.get(listVal.get(i)));
        }

    }

    public static void text(String message, String name) throws IOException {
        PrintStream print = new PrintStream(new FileOutputStream(name, true), true);
        print.println(message);
        print.close();
    }
}
