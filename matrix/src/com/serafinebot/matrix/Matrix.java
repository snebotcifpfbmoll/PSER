package com.serafinebot.matrix;

import java.util.*;

public class Matrix {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nombre de columnes de la matriu 1: ");
        int cmat_1 = Integer.parseInt(sc.nextLine());
        System.out.print("Nombre de files de la matriu 1: ");
        int fmat_1 = Integer.parseInt(sc.nextLine());

        System.out.print("Nombre de columnes de la matriu 2: ");
        int cmat_2 = Integer.parseInt(sc.nextLine());
        System.out.print("Nombre de files de la matriu 2: ");
        int fmat_2 = Integer.parseInt(sc.nextLine());

        if (cmat_1 == fmat_2) {
            System.out.println("Dimensions correctes!");

            int[][] mat_1 = new int[fmat_1][cmat_1];
            int[][] mat_2 = new int[fmat_2][cmat_2];

            for (int i = 0; i < fmat_1; i++) {
                for (int j = 0; j < cmat_1; j++) {
                    mat_1[i][j] = (int) (Math.random() * 10 + 1);
                }
            }

            for (int i = 0; i < fmat_2; i++) {
                for (int j = 0; j < cmat_2; j++) {
                    mat_2[i][j] = (int) (Math.random() * 10 + 1);
                }
            }

            imprimirMatriu(mat_1);
            imprimirMatriu(mat_2);

            int[][] res = new int[fmat_1][cmat_2];

            for (int i = 0; i < res.length; i++) {
                for (int j = 0; j < res[i].length; j++) {
                    for (int k = 0; k < mat_1[i].length; k++) {
                        res[i][j] += mat_1[i][k] * mat_2[k][j];
                    }
                }
            }

            imprimirMatriu(res);
        } else {
            System.out.println("Dimensions incorrectes");
        }
    }

    public static void imprimirMatriu(int[][] mat) {
        System.out.println("[");
        for (int i = 0; i < mat.length; i++) {
            System.out.print("[");
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + ",");
            }
            System.out.println("]");
        }
        System.out.println("]");
    }
}
