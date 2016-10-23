/*
  Created by Nitish on 9/21/2016.
 */
import java.io.*;
import java.util.*;

public class A1 {

    //public  String cacm = current + "\\cacm.all";

    public static void main(String args[]) throws IOException  {
        String current = System.getProperty("user.dir");
        Map<String, postList> list = new TreeMap<String, postList>();
        Map<String, Integer> mapp = new HashMap();
        Map<Integer,String> authorList = new HashMap();
        Map<Integer, String> titleList = new HashMap();
        Scanner scan = null;
        Scanner scan2 = null;
        int ID= 0;
        int pos = 0;
        String authorLines = "";
        String titleLines = "";
        String betweenLines = "";
        String poster = "";
        try {
            scan = new Scanner(new BufferedReader(new FileReader(current + "\\src\\cacm.all")));
            String next = "";
            String n;
            do {
                pos = 0;
                next = scan.next();
                if(next.equals(".I")){
                    next = scan.next();
                    pos++;
                    ID = Integer.parseInt(next);

                }
                else if (next.equals(".T")) {
                    next = scan.next();
                    while(!(next.equals(".W")) && (!(next.equals(".B"))) && (!(next.equals(".A"))))
                    {
                        titleLines = next ;
                        if(titleList.get(ID) == null) {
                            titleList.put(ID, titleLines);
                        }
                        else {
                           titleLines =  titleList.get(ID)  + " " + next;
                            titleList.put(ID, titleLines);
                        }
                        next = scan.next();
                    }

                }
               // next = scan.next();
                //if next value is .W take the values after it
                if (next.equals(".W")) {// || (next.equals(".T"))) {
                    next = scan.next();
                    pos++;
                    //while next value is not .B
                    while (!(next.equals(".B")))  {
                        betweenLines = next + scan.nextLine();
                        //if word does not exist in dictionary hashmap
                        n = next.replaceAll("[!&--+.'^:,/()$<>;\\[\\]%*\"]", "").toLowerCase(); //[!&--+.'^:,/()$<>;\[\]%*"]
                        n = strem(n);
                        if(n.length() >= 1 && !n.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")) { //^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$
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
                        poster = betweenLines.replaceAll("[!&--+.'^:,/()$<>;\\[\\]%*\"]", " ").toLowerCase();
                        StringTokenizer st = new StringTokenizer(poster);
                            //if word doesnot exist in postings list tree map
                        while((!(st.equals(".B"))) && st.hasMoreElements()) {
                            String nexterm = st.nextElement().toString();
                            pos++;
                            int counter = 1 ;
                            nexterm = strem(nexterm);
                            if (nexterm.length() >= 1 && !nexterm.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")) {
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
                                                if(list.get(nexterm).DocumentsOccured.get(i) == ID)
                                               {
                                                   int temp = list.get(nexterm).termFreq.get(i) + 1;
                                                    list.get(nexterm).termFreq.set(i, temp);
                                                    list.get(nexterm).pos.add(pos);
                                                }
                                            }
                                    }
                                    else if(list.get(nexterm).DocumentsOccured.contains(ID) == false )
                                    {
                                        int temp = 1;
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
                else if(next.equals(".A")) {
                    next = scan.next();
                    while ((!(next.equals(".N"))) && (!(next.equals(".K")))){
                        authorLines = next ;
                        if(authorList.get(ID) == null) {
                            authorList.put(ID, authorLines);
                        }
                        else {
                            authorLines =  authorList.get(ID)  + " " + next;
                            authorList.put(ID, authorLines);
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
           stopWords(scan2,current + "\\src\\common_words", mapp);
           stopWords(scan2,current + "\\src\\common_words", list);

            //Write hashmap to Dictionary.txt file
            PrintWriter print = new PrintWriter(new FileOutputStream("Dictionary.txt", true), true);
            List<String> listVal = new ArrayList<String>(mapp.keySet());
            Collections.sort(listVal);
            for (int i = 0; i < listVal.size(); i++) {
                print.println(listVal.get(i) + " " + mapp.get(listVal.get(i)));
            }


        //Write hashmap to Author.txt file

        PrintWriter printer = new PrintWriter(new FileOutputStream("Author.txt", true), true);
        List<Integer> listVale = new ArrayList<Integer>(authorList.keySet());
        Collections.sort(listVale);
        for (int i = 0; i < listVale.size(); i++) {
            printer.println(listVale.get(i) + "\n" + authorList.get(listVale.get(i)));
        }

        //Write hashmap to title.txt file
        PrintWriter printer2 = new PrintWriter(new FileOutputStream("Title.txt", true), true);
        List<Integer> listValue = new ArrayList<Integer>(titleList.keySet());
        Collections.sort(listValue);
        for (int i = 0; i < listValue.size(); i++) {
        printer2.println(listValue.get(i) + "\n" + titleList.get(listValue.get(i)));
        }

            //Write hashmap to postlist.txt file
           PrintWriter print2 = new PrintWriter(new FileOutputStream("PostList.txt", true), true);
            for(Map.Entry<String,postList> entry : list.entrySet()){
                print2.write(entry.getKey() + " " + entry.getValue().toString());
                print2.write("\n");

            }
    }

    public static void stopWords(Scanner scan2, String file, Map list) throws IOException, IndexOutOfBoundsException
    {
        try {
            scan2 = new Scanner(new BufferedReader(new FileReader(file)));
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

    }

    public static String StepOne(String word)
    {
        String stemFinal = "";
        String temp = "";
        if(word.endsWith("sses"))
        {
            stemFinal = word.substring(0, word.length() - 4) + "ss";
            return stemFinal;
        }
        else if (word.endsWith("ies"))
        {
            stemFinal = word.substring(0, word.length() - 3) + "i";
            return stemFinal;
        }
        else if(word.endsWith("ed"))
        {
            temp = word.substring(0, word.length() - 2);
            if(temp.endsWith("at")) {
                stemFinal = temp.substring(0, temp.length() - 2) + "ate";
                return stemFinal;
            }
            if(temp.endsWith("bl")) {
                stemFinal = temp.substring(0, temp.length() - 2) + "ble";
                return stemFinal;
            }
            if (temp.endsWith("iz")) {
                stemFinal = temp.substring(0, temp.length() - 2) + "ize";
                return stemFinal;
            }
            else return temp;
        }
        else return word;
    }

    public static String StepTwo(String word)
    {
        String StemFinal = "";
        if(word.endsWith("y")){
            StemFinal = word.substring(0, word.length()- 1) + "i";
            return StemFinal;
        }
        else return word;
    }

    public static String StepThree(String word)
    {
        String StemFinal = "";
        if(word.endsWith("ational"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("ational", "ate");
            return StemFinal;
        }
        else if(word.endsWith("tional"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("tional", "tion");
            return StemFinal;
        }
        else if(word.endsWith("izer"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("izer", "ize");
            return StemFinal;
        }
        else if(word.endsWith("bli"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("bli", "ble");
            return StemFinal;
        }
        else if(word.endsWith("alli"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("alli", "al");
            return StemFinal;
        }
        else if(word.endsWith("entli"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("entli", "ent");
            return StemFinal;
        }
        else if(word.endsWith("eli"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("eli", "e");
            return StemFinal;
        }
        else if(word.endsWith("ousli"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("ousli", "ous");
            return StemFinal;
        }
        else if(word.endsWith("ization"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("ization", "ize");
            return StemFinal;
        }
        else if(word.endsWith("ation"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("ation", "ate");
            return StemFinal;
        }
        else if(word.endsWith("ator"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("ator", "ate");
            return StemFinal;
        }
        else if(word.endsWith("alism"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("alism", "al");
            return StemFinal;
        }
        else if(word.endsWith("iveness"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("iveness", "ive");
            return StemFinal;
        }
        else if(word.endsWith("fulness"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("fulness", "ful");
            return StemFinal;
        }
        else if(word.endsWith("aliti"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("aliti", "al");
            return StemFinal;
        }
        else if(word.endsWith("iviti"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("ivity", "ive");
            return StemFinal;
        }
        else if(word.endsWith("biliti"))
        {
            StemFinal = word;
            StemFinal = StemFinal.replace("biliti", "ble");
            return StemFinal;
        }
        else return word;
    }

    public static String strem(String word)
    {

        String one = "";
        String two = "";
        String three = "";

        one = StepOne(word);
        two =  StepTwo(one);
        three  = StepThree(two);

        return three;
    }


}
