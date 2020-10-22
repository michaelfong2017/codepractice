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
//public class Main {
//    public static void main(String[] args) {
//        FastReader fastReader = new FastReader();
//        int N = fastReader.nextInt();
//        long W = fastReader.nextLong();
//        int V = 100000;
//        long[][] knap = new long[N+1][V+1];
//
//        for (int i=0;i<=N;i++){
//            for (int j=1;j<=V;j++){
//                knap[0][j] = 100000000000L;
//            }
//        }
//
//        for (int i=1;i<=N;i++){
//            long w = fastReader.nextLong();
//            int v = fastReader.nextInt();
//
//
//            for (int j=1;j<v;j++){
//                knap[i][j] = knap[i-1][j];
//            }
//            for (int j=v;j<=V;j++){
//                knap[i][j] = Math.min(knap[i-1][j], w+knap[i-1][j-v]);
//            }
//        }
//
//        for (int j=V;j>=0;j--){
//            if (knap[N][j] <= W){
//                System.out.println(j);
//                break;
//            }
//        }
//
//    }
//}
