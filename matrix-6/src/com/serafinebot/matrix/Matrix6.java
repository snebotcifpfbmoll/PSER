package com.serafinebot.matrix;

import java.util.Scanner;

public class Matrix6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Files de la imatge: ");
        int fil = Integer.parseInt(sc.nextLine());
        System.out.print("Columnes de la imatge: ");
        int col = Integer.parseInt(sc.nextLine());

        int srgb = 4;
        int svec = fil * col * srgb;
        int[] vec = new int[svec];

        for (int i = 0; i < svec; i++) {
            vec[i] = (int) (Math.random() * 0xff + 1);
        }

        // convulacio
        int[][] conv = {{1, 1, 1}, {0, 2, 0}, {-1, -1, -1}};
        int[] res = new int[svec];

        // calcular K
        int K = 0;
        for (int i = 0; i < conv.length; i++) {
            for (int j = 0; j < conv[i].length; j++) {
                K += conv[i][j];
            }
        }

        for (int i = 0; i < svec; i++) {
            if (i / (col * srgb) == 0 || i / (col * srgb) == fil - 1 || (i / srgb) % col == 0 || (i / srgb) % col == col - 1) {
                res[i] = vec[i];
                continue;
            }

            int sum = 0;
            int tmp_i = i - ((col + 1) * srgb);
            for (int j = 0; j < conv.length; j++) {
                for (int k = 0; k < conv[j].length; k++) {
                    sum += vec[tmp_i] * conv[j][k];
                    tmp_i += srgb;
                }

                tmp_i += (col - conv[j].length) * srgb;
            }
            res[i] = sum / K;
        }

        imprimirVector(vec);
        imprimirVector(res);

        System.out.println("Matriu original");
        imprimirVectorMat(vec, fil, col, srgb);

        System.out.println("Matriu resultant");
        imprimirVectorMat(res, fil, col, srgb);
    }

    public static void imprimirVectorMat(int[] vec, int fil, int col, int pro) {
        int ivec = 0;
        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print("[");
                for (int k = 0; k < pro; k++) System.out.print(vec[ivec++] + (k == pro - 1 ? "" : ","));
                System.out.print("]");
            }

            System.out.println();
        }
    }

    public static void imprimirVector(int[] vec) {
        for (int i = 0; i < vec.length; i++) {
            System.out.print(vec[i] + (i == vec.length - 1 ? "" : ","));
        }

        System.out.println();
    }
}
