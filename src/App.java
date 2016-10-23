import javax.management.Query;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by rfenechr1 on 18/10/2016.
 */

public class App {
    public JPanel panelMain;
    public JButton searchButton;
    public JRadioButton stopWordRemoval;
    public JRadioButton stemming;
    public JTextField searchfield;
    public String query = "";
    public ArrayList<String> input = new ArrayList<String>();
    public static Map<Integer, String> titleList = new HashMap();
    public static Map<Integer, String> authorList = new HashMap();
    public static Map<String, Integer> dictionary = new HashMap();
    public static Map<String, postList>  postings = new HashMap();

    public App() {
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String value = searchfield.getText();
               ArrayList<String> split = new ArrayList<String>();
                StringTokenizer input = new StringTokenizer(value);
                while(input.hasMoreElements())
                {
                    split.add(input.nextElement().toString());
                }
                String fin = "";
                if(stopWordRemoval.isSelected() && stemming.isSelected())
                {
                    try {
                        for(String s : split) {
                            String test = s.trim();
                            String que = stopWords(test);
                            String stremmed = strem(que);
                            fin += stremmed + " ";
                            query += fin.trim();
                        }
                    }
                    catch(IOException ioe) {}

                    /*Re run compilation but include stop words HERE*/
                    JOptionPane.showMessageDialog(null,"Document lists have been changed to contain stop words");
                }else if(stopWordRemoval.isSelected() && !stemming.isSelected())
                {
                    try {
                        for(String s : split) {
                            String test = s.trim();
                            String que = stopWords(test);
                            fin += que + " ";
                            query += fin.trim();
                        }
                    }
                    catch(IOException ioe) {}

                    /*Re run compilation but remove stop words HERE*/
                    JOptionPane.showMessageDialog(null,"Document lists have been changed to not contain stop words");
                }

                if(stemming.isSelected() && !stopWordRemoval.isSelected())
                {
                    for(String s : split) {
                        String test = strem(s);
                        fin += test + " ";
                        query += fin.trim();
                    }

                    /*Re run compilation but stem the words HERE*/
                    JOptionPane.showMessageDialog(null,"Document lists have had their words stemmed");
                }
                if(!stemming.isSelected() && !stopWordRemoval.isSelected()) {

                    try{
                        for(String s : split) {
                            fin += s + " ";
                        }
                        getTitles();
                        DictionaryHashMap(split);
                        System.out.println(dictionary);
                        //PostingHashMap(split);
                        //System.out.println(postings.toString());

                    }
                   catch (IOException ioe)  {
                       System.out.println(ioe);
                   }


                }

                query = searchfield.getText();
                JOptionPane.showMessageDialog(null,"This is the result of your search: '"+fin.trim()+"'");
            }
        });
    }

    public String stopWords(String list) throws IOException, IndexOutOfBoundsException
    {
        Scanner scan2 = null;
        String ret = "";
        String current = System.getProperty("user.dir");
        try {
            scan2 = new Scanner(new BufferedReader(new FileReader(current + "\\src\\common_words")));
            String st;
            do {
                st = scan2.next();
                if (list.trim().equalsIgnoreCase(st))
                {
                   list = list.replace(st, "");

                }
                else list = list;
            } while (scan2.hasNext());
        } finally {
            if (scan2 != null)
            {
                scan2.close();
            }
        }
        return list;
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

    public static void main(String args[]) throws IOException {
        String query1 = null;
        App gui = new App();
        JFrame frame = new JFrame("App");
        frame.setContentPane(gui.panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void getTitles() throws FileNotFoundException {
        Scanner Scan ;
        String current = System.getProperty("user.dir");
        Scan = new Scanner(new BufferedReader(new FileReader(current + "\\Title.txt")));
        while(Scan.hasNextLine())
        {
            int ID = Integer.parseInt(Scan.nextLine());
            String Title = Scan.nextLine();

            titleList.put(ID, Title );

        }
    }

    public static void getAuthors() throws FileNotFoundException {
        Scanner Scan;
        String current = System.getProperty("user.dir");
        Scan = new Scanner(new BufferedReader(new FileReader(current + "\\Author.txt")));
        while(Scan.hasNextLine())
        {
            int ID = Integer.parseInt(Scan.nextLine());
            String author = Scan.nextLine();
            authorList.put(ID, author );
        }
    }

    public static void DictionaryHashMap(ArrayList<String> query) throws FileNotFoundException
    {
        Scanner Sc;
        String current = System.getProperty("user.dir");
        Sc = new Scanner(new BufferedReader(new FileReader(current + "\\Dictionary.txt")));
        for(String r : query)
        {
            while(Sc.hasNext())
            {
                String word = Sc.next();
                if(word.equalsIgnoreCase(r)){
                   int freq = Integer.parseInt(Sc.next());
                   dictionary.put(word, freq);
                    break;
                }
            }
        }
    }

    public static void PostingHashMap(ArrayList<String> query) throws FileNotFoundException
    {
      //  System.out.println("hello");
        postList post = new postList();
        Scanner sc;
        String current = System.getProperty("user.dir");
        sc = new Scanner(new BufferedReader(new FileReader(current + "\\PostList.txt")));
        System.out.println("hello");
        for(String r : query)
        {
            while(sc.hasNext())
            {
                String word = sc.next();
                if(word.equalsIgnoreCase(r)){
                        String docLine = sc.nextLine();
                        String IDs = docLine.replace("Document ID", "");
                        List<String> arrayList = new ArrayList<String>    (Arrays.asList(IDs.split(" ")));
                        System.out.println(arrayList);
                        for(String fav:arrayList){
                            post.DocumentsOccured.add(Integer.parseInt(fav));
                        }

                        String termLine = sc.nextLine();
                        String freq = termLine.replace("TermFreq", "");
                        List<String> arrayList2 = new ArrayList<String>    (Arrays.asList(freq.split(" ")));
                        for(String fav:arrayList2) {
                            post.termFreq.add(Integer.parseInt(fav.trim()));
                            System.out.println(fav);
                        }
                    postings.put(word, post);

                }

            }
        }


    }




}
