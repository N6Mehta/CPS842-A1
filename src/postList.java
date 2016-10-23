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
        return DocumentsOccured + " " + termFreq + " " + pos;
    }


}
