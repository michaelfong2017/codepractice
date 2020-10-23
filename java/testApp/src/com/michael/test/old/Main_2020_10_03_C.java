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
//
//        long[][] hap = new long[N+1][3];
//        for (int i=1;i<=N;i++) {
//            long a = fastReader.nextLong();
//            long b = fastReader.nextLong();
//            long c = fastReader.nextLong();
//
//            if (i==1) {
//                hap[i][0] = a;
//                hap[i][1] = b;
//                hap[i][2] = c;
//            }
//            else {
//                hap[i][0] = Math.max(a + hap[i-1][1], a + hap[i-1][2]);
//                hap[i][1] = Math.max(b + hap[i-1][0], b + hap[i-1][2]);
//                hap[i][2] = Math.max(c + hap[i-1][0], c + hap[i-1][1]);
//            }
//        }
//
//        System.out.println(Math.max(Math.max(hap[N][0], hap[N][1]), hap[N][2]));
//    }
//}


//package com.michael.test;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.*;
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
//        int M = fastReader.nextInt();
//
//        Map<Integer, Map<Integer, Integer>> dis = new HashMap<>();
//        for (int i=0;i<=N;i++){
//            dis.put(i, new HashMap<>());
//            dis.get(i).put(i, 0);
//        }
//        Map<Integer, Map<Integer, Integer>> dis_reverse = new HashMap<>();
//        for (int i=0;i<=N;i++){
//            dis_reverse.put(i, new HashMap<>());
//            dis_reverse.get(i).put(i, 0);
//        }
//
//        for (int i=0;i<M;i++) {
//            int x = fastReader.nextInt();
//            int y = fastReader.nextInt();
//
//            // suppose (x, y) connects set A and set B
//            HashSet<Integer> A = new HashSet<>();
//            HashSet<Integer> B = new HashSet<>();
//            B.addAll(dis.get(y).keySet());
//            A.addAll(dis_reverse.get(x).keySet());
//
//            for (int a : A) {
//                for (int b : B) {
//                    int original = 0;
//                    if (dis.get(a).containsKey(b)) original = dis.get(a).get(b);
//                    dis.get(a).put(b, Math.max(original, dis_reverse.get(x).get(a) + 1 + dis.get(y).get(b)));
//
//                    if (dis_reverse.get(b).containsKey(a)) original = dis_reverse.get(b).get(a);
//                    dis_reverse.get(b).put(a, Math.max(original, dis.get(y).get(b) + 1 + dis_reverse.get(x).get(a)));
//                }
//            }
//
//        }
//
//        int max = 0;
//        for (int key : dis.keySet()){
//            int temp = Collections.max(dis.get(key).values());
//            if (max < temp){
//                max = temp;
//            }
//        }
//        System.out.println(max);
//
//
//    }
//}
