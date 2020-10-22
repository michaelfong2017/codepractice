package com.michael.test.old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_2020_09_C {

    static class FastReader {
        BufferedReader bufferedReader;
        StringTokenizer stringTokenizer;
        public FastReader() {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }

        String next(){
            while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()){
                try {
                    stringTokenizer = new StringTokenizer(bufferedReader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return stringTokenizer.nextToken();
        }

        int nextInt(){
            return Integer.parseInt(next());
        }
    }

    public static int[] parents = new int[200005];
    public static int[] sizes = new int[200005];

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        int N = fastReader.nextInt();
        int Q = fastReader.nextInt();

        // N sets
        for (int i=0;i<N;i++) {
            parents[i] = i;
            sizes[i] = 1;
        }

        long start = System.nanoTime();

        for (int i=0;i<Q;i++) {
            int t = fastReader.nextInt();
            int u = fastReader.nextInt();
            int v = fastReader.nextInt();
            if (t==1) {
                System.out.println(findParent(u)==findParent(v)?1:0);
            }
            else {
                int parent_u = findParent(u);
                int parent_v = findParent(v);
                if (sizes[parent_u] < sizes[parent_v]) {
                    parents[parent_u] = parent_v;
                    sizes[parent_v] = parent_u;
                }
                else {
                    parents[parent_v] = parent_u;
                }
            }

        }

        long end = System.nanoTime();
    }

    static int findParent(int node) {
        if (parents[node] == node) {
            return node;
        }
        else {
            parents[node] = findParent(parents[node]);
            return parents[node];
        }
    }
}
