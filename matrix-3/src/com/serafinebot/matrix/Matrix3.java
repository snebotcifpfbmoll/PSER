package com.serafinebot.matrix;

import java.util.*;

public class Matrix3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Files de la matriu: ");
        int fmat = Integer.parseInt(sc.nextLine());
        System.out.print("Columnes de la matriu: ");
        int cmat = Integer.parseInt(sc.nextLine());

        int[][] mat = new int[fmat][cmat];
        int[] vec = new int[fmat * cmat];

        int ivec = 0;
        for (int i = 0; i < fmat; i++) {
            for (int j = 0; j < cmat; j++) {
                mat[i][j] = (int) (Math.random() * 10 + 1);
                vec[ivec++] = mat[i][j];
            }
        }

        imprimirMatriu(mat);
        imprimirVector(vec);

        System.out.print("Fila: ");
        int fil = Integer.parseInt(sc.nextLine());
        System.out.print("Columna: ");
        int col = Integer.parseInt(sc.nextLine());
        System.out.print("Valor: ");
        int val1 = Integer.parseInt(sc.nextLine());

        mat[fil][col] = val1;
        vec[fil * cmat + col] = val1;

        imprimirMatriu(mat);
        imprimirVector(vec);

        System.out.print("Index: ");
        int index = Integer.parseInt(sc.nextLine());
        System.out.print("Valor: ");
        int val2 = Integer.parseInt(sc.nextLine());

        int cvec = index % cmat;
        int fvec = index / cmat;

        vec[index] = val2;
        mat[fvec][cvec] = val2;

        imprimirMatriu(mat);
        imprimirVector(vec);
    }

    public static void imprimirVector(int[] vec) {
        for (int val : vec) {
            System.out.print(val + ", ");
        }

        System.out.println();
    }

    public static void imprimirMatriu(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            System.out.print("[");
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + ",");
            }
            System.out.println("]");
        }
    }
}
