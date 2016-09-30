/**
 * Created by rfenechr1 on 29/09/2016.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**MAKE DUMMY FILES*/

public class Test
{
    /**START*/
    //random change
    public static void main(String args[])
    {
        Test T = new Test();
        while(true)
        {
            T.looper();
        }
    }

    private void looper()
    {
        //Set up reader
        Scanner sc = new Scanner(System.in);

        ArrayList timeArray = new ArrayList();

        //Ask for user input
        System.out.println("Please type in the word you want to look up within the documents:");

        while (sc.hasNext())
        {
            String user_input = sc.nextLine();

            //Sanitize input
            user_input = user_input.toLowerCase();

            //if user types in ZZEND in an attempt to quit
            if(user_input.equals("zzend"))
            {
                endProgram(timeArray);
            }

            //check to see if use has given useful input
            if(!check(user_input)){break;}

            //Get current time and store it in T1
            float t1 = System.currentTimeMillis();

            //Try and look up the word in the dictionary
            try {
                lookup(user_input);
            } catch (FileNotFoundException e) {
                throw new ExceptionInInitializerError(e);
            }

            //get current time calculate total time, output it and store it
            float time = t1 - System.currentTimeMillis();
            System.out.println("This query took "+time+" milliseconds to resolve.");
            timeArray.add(time);

            //ask again and loop
            System.out.println("Please type in the word you want to look up within the documents:");
        }
    }

    private void lookup(String word) throws FileNotFoundException
    {
        System.out.println("Searching Dictionary...");

        //open Dictionary and look for word
        File dictionary = new File("C:\\Users\\rfenechr1\\IdeaProjects\\CPS842-A1\\Dictionary.txt");

        Scanner looker = new Scanner(dictionary);

        int totalOccurrence = 0;
        int link = 0;

        while(looker.hasNextLine())
        {
            if(looker.equals(word))
            {
                System.out.println("Found it!");
                totalOccurrence = looker.nextInt();
                System.out.println(totalOccurrence);

                //store link
                link = looker.nextInt();

                //now that we have the link GOTO PostingList
                try {
                    lookupPosting(link);
                } catch (FileNotFoundException e) {
                    throw new ExceptionInInitializerError(e);
                }
            }
        }
    }

    private void lookupPosting(int link) throws FileNotFoundException
    {
        System.out.println("Searching Postings List...");
        File postList = new File("C:\\Users\\rfenechr1\\IdeaProjects\\CPS842-A1\\PostList.txt");

        Scanner looker = new Scanner(postList);
    }

    /**NOT DONE ... Regular expression special character check is still fucked*/
    private boolean check(String word)
    {
        String space = " ";
        System.out.println("Checking...");

        if(word.contains(space))
        {
            System.out.println("Space detected! Please check your input and make sure you are only typing one word per query");
            return false;
        }


        if(word.matches("[!-+.'^:@,/()$<>;\\[\\]%*\"]"))
        {
            System.out.println("Special character detected please remove any and all characters other then try again");
            return false;
        }

        System.out.println("Looks Good...");
        return true;
    }

    /**done (I think)*/
    private void endProgram(ArrayList<Float> time)
    {
        int average = 0;
        int questions = 1;

        //if nothing was asked of the program or it was all junk imput
        if(time.isEmpty())
        {
            System.out.println("No queries occurred to calculate average time with...");
            System.out.println("GoodBye.");
            System.exit(0);
        }

        /**add up all of the numbers in the array list then divide by size*/
        for(int i = 0; i < time.size(); i++)
        {
            average += time.get(i);
            questions = i;
        }

        /**END*/
        System.out.println("The average search time per word is"+(average/questions));
        System.out.println("GoodBye.");
        System.exit(0);
    }

}
