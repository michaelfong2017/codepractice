package com.michael.test;

/* Use the slash-star style comments or the system won't see your
   identification information */
/*
ID: michael422
LANG: JAVA
TASK: ride
*/
import java.io.*;
import java.util.*;

class ride {
    public static void main (String [] args) throws IOException {
        long startTime = System.nanoTime();

        String line1 = "";
        String line2 = "";

        ArrayList<String> myList = new ArrayList<>();
        if (args.length >= 1) {
            myList = readLinesFrom(args[0]);
        }
        else {
            myList = readLinesFrom("ride.in");
        }
        line1 = myList.get(0);
        line2 = myList.get(1);

//        System.out.println(line1);
//        System.out.println(line2);
        int product1 = 1;
        int product2 = 1;
        for (int i=0;i<line1.length();i++) {
            product1 *= line1.charAt(i) - 'A' + 1;
        }
        for (int i=0;i<line2.length();i++) {
            product2 *= line2.charAt(i) - 'A' + 1;
        }

//        System.out.println(product1);
//        System.out.println(product2);

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));
        if (product1 % 47 == product2 % 47) {
            out.println("GO");
        }
        else {
            out.println("STAY");
        }
        out.close();

        long endTime = System.nanoTime();
        System.out.println("That took " + (endTime - startTime) + " nanoseconds");
    }

    private static ArrayList<String> readLinesFrom(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        ArrayList<String> myList = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(line);
            myList.add(stringTokenizer.nextToken());
        }
        return myList;
    }
}
