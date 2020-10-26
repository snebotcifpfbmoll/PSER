package com.serafinebot.matrix;

import java.util.*;

public class Matrix2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // matriu a vector
        {
            System.out.print("Files de la matriu: ");
            int fmat = Integer.parseInt(sc.nextLine());
            System.out.print("Columnes de la matriu: ");
            int cmat = Integer.parseInt(sc.nextLine());

            int[][] mat = new int[fmat][cmat];
            int[] vec = new int[fmat * cmat];

            for (int i = 0; i < fmat; i++) {
                for (int j = 0; j < cmat; j++) {
                    mat[i][j] = (int) (Math.random() * 10 + 1);
                }
            }

            int ivec = 0;
            for (int i = 0; i < fmat; i++) {
                for (int j = 0; j < cmat; j++) {
                    vec[ivec++] = mat[i][j];
                }
            }

            imprimirMatriu(mat);
            imprimirVector(vec);
        }

        {
            System.out.print("Files de la matriu: ");
            int fmat = Integer.parseInt(sc.nextLine());
            System.out.print("Columnes de la matriu: ");
            int cmat = Integer.parseInt(sc.nextLine());

            int[][] mat = new int[fmat][cmat];
            int[] vec = new int[fmat * cmat];

            for (int i = 0; i < vec.length; i++) {
                vec[i] = (int) (Math.random() * 10 + 1);
            }

            int ivec = 0;
            for (int i = 0; i < fmat; i++) {
                for (int j = 0; j < cmat; j++) {
                    mat[i][j] = vec[ivec++];
                }
            }

            imprimirVector(vec);
            imprimirMatriu(mat);
        }
    }

    public static void imprimirVector(int[] vec) {
        for (int val : vec) {
            System.out.print(val + ", ");
        }

        System.out.println();
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
