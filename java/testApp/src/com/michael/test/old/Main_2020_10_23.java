//package com.michael.test;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.*;
//
//
//public class Main {
//    static class FastReader {
//        BufferedReader bufferedReader;
//        StringTokenizer stringTokenizer;
//        public FastReader() {
//            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        }
//
//        String next(){
//            while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()){
//                try {
//                    stringTokenizer = new StringTokenizer(bufferedReader.readLine());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return stringTokenizer.nextToken();
//        }
//
//        int nextInt(){
//            return Integer.parseInt(next());
//        }
//
//        long nextLong(){
//            return Long.parseLong(next());
//        }
//    }
//
//    public static void main(String[] args) {
//        FastReader fastReader = new FastReader();
//        int T = fastReader.nextInt();
//
//        for (int i=0;i<T;i++){
//            long a = fastReader.nextLong();
//            long b = fastReader.nextLong();
//
//            if (Long.bitCount(a)!=Long.bitCount(b)){
//                System.out.println("Case "+(i+1)+": "+ -1);
//                continue;
//            }
//            if (a==b){
//                System.out.println("Case "+(i+1)+": "+ 0);
//                continue;
//            }
//
//            String b_a = Long.toBinaryString(a);
//            String b_b = Long.toBinaryString(b);
//
//            List<Integer> b_a_even = new ArrayList<>();
//            List<Integer> b_b_even = new ArrayList<>();
//            List<Integer> b_a_odd = new ArrayList<>();
//            List<Integer> b_b_odd = new ArrayList<>();
//
//            for (int j=b_a.length()-1;j>=0;j--){
//                if ((b_a.length()-1-j)%2==0){
//                    //even
//                    if (b_a.charAt(j)=='1'){
//                        b_a_even.add(b_a.length()-1-j);
//                    }
//                }
//                else{
//                    //odd
//                    if (b_a.charAt(j)=='1'){
//                        b_a_odd.add(b_a.length()-1-j);
//                    }
//                }
//            }
//            for (int j=b_b.length()-1;j>=0;j--){
//                if ((b_b.length()-1-j)%2==0){
//                    //even
//                    if (b_b.charAt(j)=='1'){
//                        b_b_even.add(b_b.length()-1-j);
//                    }
//                }
//                else{
//                    //odd
//                    if (b_b.charAt(j)=='1'){
//                        b_b_odd.add(b_b.length()-1-j);
//                    }
//                }
//            }
//
//            if (b_a_even.size()!=b_b_even.size() || b_a_odd.size()!=b_b_odd.size()){
//                System.out.println("Case "+(i+1)+": "+ -1);
//                continue;
//            }
//
//            int count=0;
//
//            for (int j=0;j<b_a_even.size();j++){
//                count+=Math.abs(b_a_even.get(j)-b_b_even.get(j))/2;
//            }
//            for (int j=0;j<b_a_odd.size();j++){
//                count+=Math.abs(b_a_odd.get(j)-b_b_odd.get(j))/2;
//            }
//
//
//            System.out.println("Case "+(i+1)+": "+ count);
//
//        }
//    }
//}


package com.michael.test.old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main_2020_10_23 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        int T = Integer.parseInt(stringTokenizer.nextToken());

        for (int i=0;i<T;i++) {
            stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            long a = Long.parseLong(stringTokenizer.nextToken());
            long l = Long.parseLong(stringTokenizer.nextToken());
            long r = Long.parseLong(stringTokenizer.nextToken());

            long count=0;

            if (a>r){
                System.out.println(0);
                continue;
            }
            if (a==r){
                System.out.println(1);
                continue;
            }
            if (l==r && a<l){
                long x = l/a;
                long end = x*a + Math.min(a, x) - 1;
                if (end>=r){
                    count++;
                }
            }
            else if (l<=a && a<r){
                long y = r/a;

                if (y-1 <= a){
                    count+= (y-1)*y/2;
                }
                else{
                    count+= a*(a+1)/2 + (y-1-a)*a;
                }

                long end = a*y + Math.min(a, y) - 1;
                count += r-a*y+1;
                if (end < r){
                    count -= r-end;
                }

            }

            else if (a<l && l<r){
                long x = l/a;
                long y = r/a;


                if (x==y){
                    long end = a*x+Math.min(a,x)-1;

                    if (end<l){

                    }
                    else {
                        count+=end-l+1;
                    }
                    if (end>r){
                        count = count - end + r;
                    }
                }
                else {
                    long end = a*x+Math.min(a,x)-1;

                    if (end<l){

                    }
                    else {
                        count+=end-l+1;
                    }


                    x++;
                    if (y-1 <= a){
                        count+= (x+y-1)*(y-x)/2;
                    }
                    else{
                        count+= (x+a)*(a-x+1)/2 + (y-1-a)*a;
                    }


                    end = a*y + Math.min(a, y) - 1;
                    count += r-a*y+1;
                    if (end < r){
                        count -= r-end;
                    }
                }
            }

            System.out.println(count);
        }

    }
}

