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
            scan = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Ni2\\IdeaProjects\\CPS842 A1\\cacm\\cacm.all"))) ;
            String st;
            st = scan.next();
            String next;

            do{
                next = scan.next();
                if (next.equals(".W")) {
                    st = scan.next();
                    //String word = st;
                    if(map1.get(st) == null) map1.put(st, 1);
                    else {
                        int newWord = Integer.valueOf(String.valueOf(map1.get(st)));
                        newWord ++ ;
                        map1.put(st, newWord);
                    }
                   /** String[] words = st.split(" ");
                    for (int i = 0; i < words.length; i++) {
                        if (map1.get(words[i]) == null) {
                            map1.put(words[i], 1);
                        } else {
                            int newWord = Integer.valueOf(String.valueOf(map1.get(words[i])));
                            newWord++;
                            map1.put(words[i], newWord);
                        }
                    }
                    builder.append(System.lineSeparator());**/
                }
                st = next;
            }while(scan.hasNext());
        }finally {
            if (scan != null) {
                scan.close();
            }
        }
        Map<String, String> fin = new TreeMap<String, String>(map1);
        for(Object key : fin.keySet()) {
            System.out.println("word: " + key + "\tCounts: " + map1.get(key));
        }


/**
        Scanner scan = null;
        HashMap<String, Integer> map = new HashMap<String,Integer>();
        try {
            scan = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Ni2\\IdeaProjects\\CPS842 A1\\cacm\\cacm.all")));
            String st;
            st = scan.next();
            String next;
            do {
                next = scan.next();
                if((next.equals(".I"))) {
                   // System.out.println(st);
                    map.put(st,)
                    text(st,"Dictionary.txt");
                }
                st= next;
            }while(scan.hasNext());
        }
        finally{
            if(scan != null){
                scan.close();
            }
        }**/

    }

    public static void text(String message, String name) throws IOException {
        PrintStream print = new PrintStream(new FileOutputStream(name, true), true);
        print.println(message);
        print.close();
    }
}
