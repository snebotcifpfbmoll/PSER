package com.serafinebot.matrix;

import java.util.Scanner;

public class Matrix4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Files de la matriu: ");
        int fmat = Integer.parseInt(sc.nextLine());
        System.out.print("Columnes de la matriu: ");
        int cmat = Integer.parseInt(sc.nextLine());
        System.out.print("Profunditat de la matriu: ");
        int pmat = Integer.parseInt(sc.nextLine());

        int[][][] mat = new int[fmat][cmat][pmat];
        int[] vec = new int[fmat * cmat * pmat];

        int ivec = 0;
        for (int i = 0; i < fmat; i++) {
            for (int j = 0; j < cmat; j++) {
                for (int k = 0; k < pmat; k++) {
                    mat[i][j][k] = (int) (Math.random() * 10 + 1);
                    vec[ivec++] = mat[i][j][k];
                }
            }
        }

        imprimirMatriu3D(mat);
        imprimirVector(vec);

        System.out.print("Fila: ");
        int fil = Integer.parseInt(sc.nextLine());
        System.out.print("Columna: ");
        int col = Integer.parseInt(sc.nextLine());
        System.out.print("Profunditat: ");
        int pro = Integer.parseInt(sc.nextLine());
        System.out.print("Valor: ");
        int val1 = Integer.parseInt(sc.nextLine());

        ivec = (fil * cmat * pmat) + (col * pmat) + pro;
        mat[fil][col][pro] = val1;
        vec[ivec] = val1;

        imprimirMatriu3D(mat);
        imprimirVector(vec);

        System.out.print("Index: ");
        int index = Integer.parseInt(sc.nextLine());
        System.out.print("Valor: ");
        int val2 = Integer.parseInt(sc.nextLine());

        int fvec = index / (cmat * pmat);
        int cvec = (index % (cmat * pmat)) / pmat;
        int pvec = index % pmat;

        System.out.println("fvec: " + fvec + "; cvec: " + cvec + "; pvec: " + pvec);

        vec[index] = val2;
        mat[fvec][cvec][pvec] = val2;

        imprimirMatriu3D(mat);
        imprimirVector(vec);
    }

    public static void imprimirVector(int[] vec) {
        for (int val : vec) {
            System.out.print(val + ",");
        }

        System.out.println();
    }

    public static void imprimirMatriu3D(int[][][] mat3d) {
        for (int[][] mat2d : mat3d) {
            System.out.print("[");
            for (int[] vec : mat2d) {
                System.out.print("[");
                for (int val : vec) {
                    System.out.print(val + ",");
                }
                System.out.print("]");
            }
            System.out.println("]");
        }
    }
}
