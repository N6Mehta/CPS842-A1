/**
 * Created by Ni2 on 9/29/2016.
 */
import java.io.*;
import java.util.*;

public class postList {
    List<Integer> DocumentsOccured = new ArrayList<>();
    List<Integer> termFreq = new ArrayList<>();
    List<Integer> pos = new ArrayList<>();

    //String word;

    public String toString(){
        return "\nDocumentID " + printID() + "\n" + "TermFreq" + printFrequencies() + "\n" + "position" + printPositions();
    }

    public String printID()
    {
        String s = "";
        for(int value : DocumentsOccured)
        {
            s = s + " " + value;
        }
        return "" + s + "";
    }

    public String printFrequencies()
    {
        String s = "";
        for(int value : termFreq)
        {
            s = s + " " + value;
        }
        return "" + s + "";
    }

    public String printPositions()
    {
        String s = "";
        for(int value : pos)
        {
            s = s + " " + value;
        }
        return "" + s + "";
    }
}
