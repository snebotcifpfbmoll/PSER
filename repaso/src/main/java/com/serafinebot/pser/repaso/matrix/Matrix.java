package com.serafinebot.pser.repaso.matrix;

import java.util.Scanner;

public class Matrix {
    public void matrix_1() {
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

    public void matrix_2() {
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

    public void matrix_3() {
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

    public void matrix_4() {
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

    public void matrix_5() {
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

    public void matrix_6() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Files de la imatge: ");
        int fil = Integer.parseInt(sc.nextLine());
        System.out.print("Columnes de la imatge: ");
        int col = Integer.parseInt(sc.nextLine());

        int srgb = 4;
        int svec = fil * col * srgb;
        byte[] vec = new byte[svec];

        for (int i = 0; i < svec; i++) {
            vec[i] = (byte)(Math.random() * 10);
        }

        byte[][] conv = {{1, 1, 1}, {0, 0, 0}, {-1, -1, -1}};
        byte[] res = convolution(vec, col, fil, 4, conv);

        imprimirVector(vec);
        imprimirVector(res);

        System.out.println("Matriu original");
        imprimirVectorMat(vec, fil, col, srgb);

        System.out.println("Matriu resultant");
        imprimirVectorMat(res, fil, col, srgb);
    }

    public static byte[] convolution(byte[] vec, int width, int height, int pro, byte[][] conv) {
        byte[] ret = new byte[vec.length];

        int K = 0;
        for (int i = 0; i < conv.length; i++) {
            for (int j = 0; j < conv[i].length; j++) {
                K += conv[i][j];
            }
        }

        if (K == 0) K = 1;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < pro; k++) {
                    if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                        int index = (i * width * pro) + (j * pro) + k;
                        ret[index] = vec[index];
                        continue;
                    }

                    int sum = 0;
                    int conv_diff = conv.length / 2;
                    int index = ((i - conv_diff) * width * pro) + ((j - conv_diff) * pro) + k;
                    for (int l = 0; l < conv.length; l++) {
                        for (int m = 0; m < conv[l].length; m++) {
                            System.out.printf("%d * %d = %d\n", vec[index], conv[l][m], vec[index] * conv[l][m]);
                            sum += vec[index] * conv[l][m];
                            index += pro;
                        }

                        index += (width - conv[l].length) * pro;
                    }

                    index = (i * width * pro) + (j * pro) + k;
                    ret[index] = (byte)(sum / K);

                    System.out.println();
                }
            }
        }

        return ret;
    }

    public void imprimirVectorMat(byte[] vec, int fil, int col, int pro) {
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

    public void imprimirVector(int[] vec) {
        for (int i = 0; i < vec.length; i++) {
            System.out.print(vec[i] + (i == vec.length - 1 ? "" : ","));
        }
        System.out.println();
    }

    public void imprimirVector(byte[] vec) {
        for (int i = 0; i < vec.length; i++) {
            System.out.print(vec[i] + (i == vec.length - 1 ? "" : ","));
        }
        System.out.println();
    }

    public void imprimirMatriu(int[][] mat) {
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

    public void imprimirMatriu3D(int[][][] mat3d) {
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
