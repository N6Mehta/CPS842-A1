/**
 * Created by rfenechr1 on 29/09/2016.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Test
{
    public static void main(String args[])
    {
        Test T = new Test();
        while(true)
        {
            T.looper();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    //main runner for code will pass on user input to searchers
    ////////////////////////////////////////////////////////////////////////////////////////
    private void looper()
    {
        //Set up reader
        Scanner sc = new Scanner(System.in);

        //create array that holds the time for each search to take place
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

            //get current time calculate total time, output it and store it for average calculation
            float time = t1 - System.currentTimeMillis();
            System.out.println("This query took "+time+" milliseconds to resolve.");
            timeArray.add(time);

            //ask again and loop
            System.out.println("Please type in the word you want to look up within the documents:");
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    //will look in the Dictionary.txt file and look for the word
    ////////////////////////////////////////////////////////////////////////////////////////
    private void lookup(String word) throws FileNotFoundException
    {
        System.out.println("Searching Dictionary...");

        //set up Dictionary scanner
        File dictionary = new File("C:\\Users\\rfenechr1\\IdeaProjects\\CPS842-A1\\Dictionary.txt");
        Scanner looker = new Scanner(dictionary);

        int totalOccurrence = 0;
        String link;

        //while we have not found the word yet
        while(looker.hasNextLine())
        {
            //if we found it
            if(looker.equals(word))
            {
                //print out its global occurrence number
                System.out.println("Found it!");
                totalOccurrence = looker.nextInt();
                System.out.println("Appeared throughout all the documents "+totalOccurrence+" times");

                //now that we know the word exists pass it to the posting list
                try {
                    lookupPosting(word);
                } catch (FileNotFoundException e) {
                    throw new ExceptionInInitializerError(e);
                }
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    //For each document,all the positions the term occurs in that document,
    ////////////////////////////////////////////////////////////////////////////////////////
    private void lookupPosting(String link) throws FileNotFoundException
    {
        System.out.println("Searching Postings List...");

        //setup Posting list's scanner and file
        File postList = new File("C:\\Users\\rfenechr1\\IdeaProjects\\CPS842-A1\\PostList.txt");
        Scanner looker = new Scanner(postList);

        //setup document scanner and file
        File cacm = new File("C:\\Users\\rfenechr1\\IdeaProjects\\CPS842-A1\\cacm\\cacm.all");
        Scanner docCrawler = new Scanner(cacm);

        int docCounter = 0;
        String title;
        String docOccurances;

        //go through the posting list
        while(looker.hasNextLine())
        {
            //found word in posting list
            if(looker.equals(link))
            {
                System.out.println("Found it!");

                //print doc#
                System.out.println("Doc # "+docCounter);

                //go through cacm
                while(docCrawler.hasNextLine())
                {
                    //stop at each .I junction and look at its ID
                    if(docCrawler.equals(".I"))
                    {
                        //if we found the right doc ID
                        if(docCrawler.nextInt() == docCounter)
                        {
                            //move down 2 lines to the title itself
                            title = docCrawler.nextLine();//skip .T
                            title = docCrawler.nextLine();//grab title

                            //Print title
                            System.out.println(title);
                        }
                    }
                }
                //print the next line in the Posting being the occurances in the doc
                System.out.println("This word occurs in the document at these locations");
                System.out.println(looker.nextLine());
                break;
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    //checks String its passed to sanitize it and check for any input errors
    ////////////////////////////////////////////////////////////////////////////////////////
    private boolean check(String word)
    {
        String space = " ";
        System.out.println("Checking...");

        //look for spaces
        if(word.contains(space))
        {
            System.out.println("Space detected! Please check your input and make sure you are only typing one word per query");
            return false;
        }

        //look for unrecognized characters
        if(word.matches("[!-+.'^:@,/()$<>;\\[\\]%*\"]"))
        {
            System.out.println("Special character detected please remove any and all characters other then try again");
            return false;
        }

        System.out.println("Looks Good...");
        return true;
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    //Will be called once the user attempts to terminate the program via ZZEND command
    ////////////////////////////////////////////////////////////////////////////////////////
    private void endProgram(ArrayList<Float> time)
    {
        int average = 0;
        int questions = 1;

        //if nothing was asked of the program or it was all junk input
        if(time.isEmpty())
        {
            System.out.println("No queries occurred to calculate average time with...");
            System.out.println("GoodBye.");
            System.exit(0);
        }

        //add up all the times that have been collected
        for(int i = 0; i < time.size(); i++)
        {
            average += time.get(i);
            questions = i;
        }

        ///print average time and exit
        System.out.println("The average search time per word is"+(average/questions));
        System.out.println("GoodBye.");
        System.exit(0);
    }

}
