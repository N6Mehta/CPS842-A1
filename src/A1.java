/*
  Created by Nitish on 9/21/2016.
 */
import java.io.*;
import java.util.*;

public class A1 {

    //public  String cacm = current + "\\cacm.all";

    public static void main(String args[]) throws IOException {
        String current = System.getProperty("user.dir");
        Map<String, postList> list = new TreeMap<String, postList>();
        postList post = new postList();

        Map<String, Integer> mapp = new HashMap();
        Scanner scan = null;
        Scanner scan2 = null;

        int ID= 0;
        int pos = 0;
        String between = "";
        String poster = "";
        try {
            scan = new Scanner(new BufferedReader(new FileReader(current + "\\src\\cacm.all")));
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
                    while (!(next.equals(".B")))  {
                        between = next + scan.nextLine();
                        //if word does not exist in dictionary hashmap
                        n = next.replaceAll("[!&-+.'^:,/()$<>;\\[\\]%*\"]", "").toLowerCase();
                        if(n.length() > 1 && !n.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")) {
                            if (mapp.get(n) == null) {
                                mapp.put(n, 1);
                            }
                            //else if word does exist in dictionary hashmap
                            else {
                                int newWord = Integer.valueOf(String.valueOf(mapp.get(n)));
                                newWord++;
                                mapp.put(n, newWord);
                            }
                        }
                        poster = between.replaceAll("[!&-+.'^:,/()$<>;\\[\\]%*\"]", "").toLowerCase();
                        StringTokenizer st = new StringTokenizer(poster);
                            //if word does not exist in postings list tree map
                        while(st.hasMoreElements()) {
                            String nexterm = st.nextElement().toString();
                            int counter = 1 ;
                            if (nexterm.length() > 1 && !nexterm.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")) {
                                if (list.get(nexterm) == null) {
                                    postList temp = new postList();
                                    temp.DocumentsOccured.add(ID);
                                    temp.termFreq.add(counter);
                                    temp.pos.add(pos);
                                    list.put(nexterm, temp);
                                }
                                //else if word does exist in postings list tree map
                                else if (list.containsKey(nexterm)) {

                                    if(list.get(nexterm).DocumentsOccured.contains(ID) == true) {
                                        for(int i = 0; i < list.get(nexterm).DocumentsOccured.size(); i++){
                                           // for(int j = 0; j < list.get(nexterm).termFreq.size(); j++) {
                                               // System.out.println(list.get(nexterm).DocumentsOccured.get(i));
                                                if(list.get(nexterm).DocumentsOccured.get(i) == ID)
                                               {
                                                   int temp = list.get(nexterm).termFreq.get(i) + 1;
                                                   // System.out.println(temp);
                                                    list.get(nexterm).termFreq.set(i, temp);
                                                    list.get(nexterm).pos.add(pos);
                                                }
                                            }
                                    }
                                    else if(list.get(nexterm).DocumentsOccured.contains(ID) == false )
                                    {
                                        int temp = 1;
                                        //System.out.println(temp);
                                        list.get(nexterm).DocumentsOccured.add(ID);
                                        list.get(nexterm).termFreq.add(temp);
                                        list.get(nexterm).pos.add(pos);
                                    }
                                }
                            }
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
            scan2 = new Scanner(new BufferedReader(new FileReader(current + "\\src\\common_words")));
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

        try {
            scan2 = new Scanner(new BufferedReader(new FileReader(current + "\\src\\common_words")));
            String st;

            do {
                st = scan2.next();
                if (list.get(st) != null)
                {
                    list.remove(st);
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

            //Write hashmap to postlist.txt file
           PrintWriter print2 = new PrintWriter(new FileOutputStream("PostList.txt", true), true);
            for(Map.Entry<String,postList> entry : list.entrySet()){
                print2.write(entry.getKey() + " " + entry.getValue().toString());
                print2.write("\n");

            }

    }





}
