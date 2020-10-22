package com.michael.test.old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class FastReader {
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

    long nextLong(){
        return Long.parseLong(next());
    }
}

class FenwickTree {
    // starts at index 1
    long[] fTree;
    public FenwickTree(int size){
        fTree = new long[size+1];
    }

    public long getFirstKSum(int index){
        long sum=0;
        while(index>0){
            sum += fTree[index];
            index = index - (index & (-index));
        }
        return sum;
    }

    public void updateKthValue(int n, int index, long val){
        index = index + 1;
        while(index<=n){
            fTree[index]+=val;
            index = index + (index & (-index));
        }
    }

    public void constructFTree(long[] arr, int n){
        for (int i=1; i<=n; i++) fTree[i] = 0;
        for (int i=0; i<n; i++) updateKthValue(n, i, arr[i]);
    }
}

public class Main_2020_09_D {
    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        int N = fastReader.nextInt();
        int Q = fastReader.nextInt();
        long a[] = new long[N];
        for (int i=0;i<N;i++){
            a[i] = fastReader.nextLong();
        }

        FenwickTree tree = new FenwickTree(N);
        tree.constructFTree(a, N);

        for (int i=0;i<Q;i++){
            int t = fastReader.nextInt();
            if (t==0){
                int p = fastReader.nextInt();
                long x = fastReader.nextLong();
                tree.updateKthValue(N, p, x);

            }
            else if (t==1){
                int l = fastReader.nextInt();
                int r = fastReader.nextInt();
                long sum = tree.getFirstKSum(r) - tree.getFirstKSum(l);
                System.out.println(sum);
            }
        }
    }
}
