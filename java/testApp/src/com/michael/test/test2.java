package com.michael.test;

import java.util.*;


class test2 {
    public static void main (String [] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[] A = new int[N];
        for (int i=0;i<N;i++) {
            A[i] = scanner.nextInt();
        }

        TreeMap<Integer, Integer> treeMap = new TreeMap();
        for (int i=0;i<N;i++) {
            treeMap.put(i, A[i]);
        }

        long sum=0;

        long start = System.nanoTime();

        for (int i=0;i<M;i++) {
            Map.Entry<Integer, Integer> maxEntry = treeMap.entrySet()
                    .stream()
                    .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                    .get();
            int maxKey = maxEntry.getKey();
            int maxValue = maxEntry.getValue();

            treeMap.put(maxKey, maxValue / 2);
        }

        for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {

            sum += entry.getValue().intValue();
        }

        long end = System.nanoTime();

        System.out.println(sum);
    }
}
