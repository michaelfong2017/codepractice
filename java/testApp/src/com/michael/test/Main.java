package com.michael.test;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();

        boolean answer = false;
        for (int i=0;i<s.length();i++) {
            if (s.charAt(i) == 'A' || s.charAt(i) == 'B') {
                if (i+1 == s.length()) {
                    break;
                }
                if (s.charAt(i) == 'A' && s.charAt(i+1) == 'B') {
                    if (i+2 < s.length()) {
                        if (s.charAt(i+2)=='A') {
                            if (i+3 == s.length()) {
                                break;
                            }
                            answer = isBA(s.substring(i+3)) || isAB(s.substring(i+3));
                        }
                        else {
                            answer = isBA(s.substring(i+2));
                        }
                        break;
                    }
                }
                if (s.charAt(i) == 'B' && s.charAt(i+1) == 'A') {
                    if (i+2 < s.length()) {
                        if (s.charAt(i+2)=='B') {
                            if (i+3 == s.length()) {
                                break;
                            }
                            answer = isBA(s.substring(i+3)) || isAB(s.substring(i+3));
                        }
                        else {
                            answer = isAB(s.substring(i+2));
                        }
                        break;
                    }
                }
            }
        }
        if (answer) {
            System.out.println("YES");
        }
        else {
            System.out.println("NO");
        }
    }

    private static boolean isAB(String s) {
        for (int i=0;i<s.length();i++) {
            if (s.charAt(i)=='A') {
                if (i+1 == s.length()) {
                    break;
                }
                if (s.charAt(i+1) == 'B') {
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean isBA(String s) {
        for (int i=0;i<s.length();i++) {
            if (s.charAt(i)=='B') {
                if (i+1 == s.length()) {
                    break;
                }
                if (s.charAt(i+1) == 'A') {
                    return true;
                }
            }
        }
        return false;
    }
}
