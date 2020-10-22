//package com.michael.test.old;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.StringTokenizer;
//
//class FastReader {
//    BufferedReader bufferedReader;
//    StringTokenizer stringTokenizer;
//    public FastReader() {
//        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//    }
//
//    String next(){
//        while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()){
//            try {
//                stringTokenizer = new StringTokenizer(bufferedReader.readLine());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return stringTokenizer.nextToken();
//    }
//
//    int nextInt(){
//        return Integer.parseInt(next());
//    }
//
//    long nextLong(){
//        return Long.parseLong(next());
//    }
//}
//
//public class Knapsack1 {
//    public static void main(String[] args) {
//        FastReader fastReader = new FastReader();
//        int N = fastReader.nextInt();
//        int W = fastReader.nextInt();
//        long[][] knap = new long[N+1][W+1];
//        for (int i=1;i<=N;i++){
//            int w = fastReader.nextInt();
//            long v = fastReader.nextLong();
//
//            for (int j=0;j<w;j++){
//                knap[i][j] = knap[i-1][j];
//            }
//            for (int j=w;j<=W;j++){
//                knap[i][j] = Math.max(knap[i-1][j], v+knap[i-1][j-w]);
//            }
//        }
//
//        System.out.println(knap[N][W]);
//
//    }
//}
