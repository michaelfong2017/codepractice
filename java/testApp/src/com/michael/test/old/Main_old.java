package com.michael.test.old;

import java.util.*;


class Main_old {
    public static void main (String [] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[] A = new int[N];
        for (int i=0;i<N;i++) {
            A[i] = scanner.nextInt();
        }

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((x,y)->Integer.compare(y,x));
        for (int i=0;i<N;i++) {
            priorityQueue.add(A[i]);
        }

        long sum=0;

        long start = System.nanoTime();

        for (int i=0;i<M;i++) {
            int max = priorityQueue.poll();
            priorityQueue.add(max/2);
        }

        for (Integer element : priorityQueue) {

            sum += element;
        }

        long end = System.nanoTime();

        System.out.println(sum);
    }
}
