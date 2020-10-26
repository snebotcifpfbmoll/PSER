package com.serafinebot.matrix;

import java.util.Scanner;

public class Matrix5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Files de la matriu: ");
        int fmat = Integer.parseInt(sc.nextLine());
        System.out.print("Columnes de la matriu: ");
        int cmat = Integer.parseInt(sc.nextLine());

        int[][] mat = new int[fmat][cmat];
        int[][] res = new int[fmat][cmat];
        int[][] conv_mat = {{1, 1, 1}, {0, 2, 0}, {-1, -1, -1}};

        int K = 0;
        for (int i = 0; i < conv_mat.length; i++) {
            for (int j = 0; j < conv_mat[i].length; j++) {
                K += conv_mat[i][j];
            }
        }

        for (int i = 0; i < fmat; i++) {
            for (int j = 0; j < cmat; j++) {
                mat[i][j] = (int) (Math.random() * 9 + 1);
            }
        }

        // convulacio
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (i == 0 || i == mat.length - 1 || j == 0 || j == mat[i].length - 1) {
                    res[i][j] = mat[i][j];
                    continue;
                }

                int sum = 0;
                int tmp_i = i - 1;
                int tmp_j = j - 1;
                for (int k = 0; k < conv_mat.length; k++) {
                    for (int l = 0; l < conv_mat[k].length; l++) {
                        //System.out.println("tmp_i: " + tmp_i + "; tmp_j: " + tmp_j + "; k: " + k + "; l: " + l);
                        sum += mat[tmp_i][tmp_j++] * conv_mat[k][l];
                    }

                    tmp_i += 1;
                    tmp_j = j - 1;
                }

                res[i][j] = sum / K;
            }
        }

        System.out.println("matriu original");
        imprimirMatriu(mat);
        System.out.println("\nmatriu resultant");
        imprimirMatriu(res);

        // vecotres
        int sconv = 3;
        int[] vec = new int[fmat * cmat];
        int[] vec_res = new int[fmat * cmat];
        int[] vec_conv = new int[sconv * sconv];

        int ivec = 0;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                vec[ivec++] = mat[i][j];
            }
        }

        ivec = 0;
        for (int i = 0; i < conv_mat.length; i++) {
            for (int j = 0; j < conv_mat[i].length; j++) {
                vec_conv[ivec++] = conv_mat[i][j];
            }
        }

        for (int i = 0; i < fmat; i++) {
            for (int j = 0; j < cmat; j++) {
                int index = i * cmat + j;
                if (i == 0 || i == mat.length - 1 || j == 0 || j == mat[i].length - 1) {
                    vec_res[index] = vec[index];
                    continue;
                }

                int sum = 0;
                int tmp_i = i - 1;
                int tmp_j = j - 1;
                for (int k = 0; k < sconv; k++) {
                    for (int l = 0; l < sconv; l++) {
                        sum += vec[tmp_i * cmat + tmp_j] * vec_conv[k * sconv + l];
                        tmp_j += 1;
                    }

                    tmp_i += 1;
                    tmp_j = j - 1;
                }

                vec_res[index] = sum / K;
            }
        }

        imprimirVector(vec);
        imprimirVector(vec_res);
    }

    public static void imprimirVector(int[] vec) {
        for (int i = 0; i < vec.length; i++) {
            System.out.print(vec[i] + (i == vec.length - 1 ? "" : ","));
        }

        System.out.println();
    }

    public static void imprimirMatriu(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            System.out.print("[");
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + (j == mat[i].length -1 ? "" : ","));
            }
            System.out.println("]");
        }
    }
}
