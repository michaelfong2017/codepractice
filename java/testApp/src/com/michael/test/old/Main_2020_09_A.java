package com.michael.test.old;

import java.util.*;

public class Main_2020_09_A {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        long K = scanner.nextInt();

        long[] A = new long[N];
        long[] B = new long[M];

        for (int i=0;i<N;i++) {
            if (i==0){
                A[i]=scanner.nextInt();
                continue;
            }
            A[i] = A[i-1]+scanner.nextInt();
        }
        for (int i=0;i<M;i++) {
            if (i==0){
                B[i]=scanner.nextInt();
                continue;
            }
            B[i] = B[i-1]+scanner.nextInt();
        }

        int count=0;
        for (int i=-1;i<N;i++){
            long remaining=K;
            if (i!=-1) remaining = K-A[i];
            if (remaining<0) break;
            int j = Arrays.binarySearch(B, remaining);
            if (j==-1) j = 0;
            else if (j<0) j=j*-1 - 1;
            else j++;
            count = Math.max(i+1+j, count);
        }

        System.out.println(count);
    }
}
