package com.cs304.AlgorithmAnalysis;

import java.util.Arrays;
import java.util.Scanner;

public class ConnectedGraph {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter no of vertices");
        int n = in.nextInt();
        int[][] W = new int[n][n];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; i++) {
                if (i == j)
                    W[i][j] = 0;
                else
                    System.out.println("Enter path from vertex " + i+1 + " to vertex" + j+1);

            }
        }
        System.out.println(Arrays.toString(floyd(n, W)));
    }

    public static int[][] floyd(int n, int[][] W) {
        int[][] P = new int[n][n];
        int[][] D = new int[n][n];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; i <= n; i++) {

                P[i][j] = 0;
            }
        }
        D = W;
        for (int k = 0; k <= n; k++) {
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= n; j++) {
                    if (D[i][k] + D[k][j] < D[i][j]) {
                        P[i][j] = k;
                        D[i][j] = D[i][k] + D[k][j];
                    }
                }
            }
        }
        return P;
    }
//
//    public static boolean  isConnected(int[][] P) {
//
//    }
}