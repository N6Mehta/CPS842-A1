/*
  Created by Nitish on 9/21/2016.
 */
import java.io.*;
import java.util.*;

public class A1 {
    public static String cacm = "C:\\Users\\Ni2\\IdeaProjects\\CPS842 A1\\cacm\\cacm.all";
    public static void main(String args[]) throws IOException {

        Map<String, postList> list = new TreeMap<String, postList>();
        postList post = new postList();

        Map<String, Integer> mapp = new HashMap();
        //Map<String, List<Integer>> map2 = new HashMap<>();
        //List<Integer> val = new ArrayList<Integer>();
        Scanner scan = null;
        Scanner scan2 = null;

        int ID= 0;
        int count = 0;
        int pos = 0;
        try {
            scan = new Scanner(new BufferedReader(new FileReader(cacm)));
            String next;
            String n;
            do {
                next = scan.next();
                if(next.equals(".I")){
                    next = scan.next();

                    ID = Integer.parseInt(next);

                }
                //if next value is .W or .T take the values after it
                else if (next.equals(".W") || (next.equals(".T"))) {
                    next = scan.next();
                    //while next value is not .B
                    while (!(next.equals(".B"))) {
                        n = next.replaceAll("[!&-+.'^:,/()$<>;\\[\\]%*\"]", " ").toLowerCase();
                        if (mapp.get(n) == null){
                            mapp.put(n, 1);
                        }
                        else {
                            int newWord = Integer.valueOf(String.valueOf(mapp.get(n)));
                            newWord++;
                            mapp.put(n, newWord);
                        }
                        if(list.get(n)== null){
                            postList temp = new postList();
                            temp.DocumentsOccured.add(ID);
                            temp.termFreq.add(count);
                            temp.pos.add(pos);
                            list.put(n,temp);

                        }
                        else {
                            postList temp = new postList();
                            temp.DocumentsOccured = list.get(n).DocumentsOccured;
                            temp.termFreq = list.get(n).termFreq;
                            temp.pos = list.get(n).pos;
                            temp.DocumentsOccured.add(ID);
                            temp.termFreq.add(count);
                            temp.termFreq.add(pos);
                            list.put(n, temp);
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

        /**
        //Scan through cacm.all file for words and count words FOR DICTIONARY
        try {
            scan = new Scanner(new BufferedReader(new FileReader(cacm)));
            String next;
            String n;
            do {
                next = scan.next();
                if(next.equals(".I")){
                    next = scan.next();
                    ID = next;

                }
                //if next value is .W or .T take the values after it
                else if (next.equals(".W") || (next.equals(".T"))) {
                    next = scan.next();
                    //while next value is not .B
                    while (!(next.equals(".B"))) {
                        n = next.replaceAll("[-+.'^:,/()$<>;\\[\\]%*\"]", "").toLowerCase();
                        if (map1.get(n) == null){
                            map1.put(n, 1);
                            count = 1;
                            mapp.put(ID, count);
                        }
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
        }**/
        //Scan through common_words file to remove common words
        try {
            scan2 = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Ni2\\IdeaProjects\\CPS842 A1\\cacm\\common_words")));
            String st;

            do {
                st = scan2.next();
                if (mapp.get(st) != null)
                {
                    mapp.remove(st);
                }

            } while (scan2.hasNext());
        } finally {
            if (scan2 != null)
            {
                scan2.close();
            }
        }
            //Write hashmap to Dictionary.txt file
            PrintWriter print = new PrintWriter(new FileOutputStream("Dictionary.txt", true), true);
            List<String> listVal = new ArrayList<String>(mapp.keySet());
            Collections.sort(listVal);
            for (int i = 0; i < listVal.size(); i++) {
                print.println(listVal.get(i) + " " + mapp.get(listVal.get(i)));
            }

            //Write hashmap to pistlist.txt file
           PrintWriter print2 = new PrintWriter(new FileOutputStream("PostList.txt", true), true);
            for(Map.Entry<String,postList> entry : list.entrySet()){
                print2.write(entry.getKey() + "\n" + entry.getValue().toString());
                print2.write("\n");

            }
/**
          List<String> listVal2 = new ArrayList<String>(list.keySet());
          Collections.sort(listVal2);
          for (int i = 0; i < listVal2.size(); i++) {
            print2.println(listVal2.get(i) + " " + list.get(listVal2.get(i)));
          }**/
    }
}
