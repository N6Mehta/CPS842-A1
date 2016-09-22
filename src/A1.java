/**
 * Created by Ni2 on 9/21/2016.
 */
import java.io.*;
import java.util.*;

public class A1 {
    public static void main(String args[]) throws IOException{
        Scanner scan = null;

        try {
            scan = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Ni2\\IdeaProjects\\CPS842 A1\\cacm\\cacm.all")));
            String st;
            st = scan.next();
            String next;
            do {
                next = scan.next();
                if((next.equals(".I"))) {
                   // System.out.println(st);
                    text(st,"Dictionary.txt");
                }
                st= next;
            }while(scan.hasNext());
        }
        finally{
            if(scan != null){
                scan.close();
            }
        }

    }

    public static void text(String message, String name) throws IOException {
        PrintStream print = new PrintStream(new FileOutputStream(name, true), true);
        print.println(message);
        print.close();
    }
}
