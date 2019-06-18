package com.lpy.sparsearray;

import org.junit.Test;

import java.io.*;

/**
 * @author lipengyu
 * @date 2019/6/14 10:57
 */
public class SparseArray {

    @Test
    public void sparseArray() {
        // 创建一个原始的二维数组 11 * 11
        // 0: 表示没有棋子 1 表示 黑子 2 表示 蓝子
        int[][] chessArray1 = new int[11][11];
        chessArray1[1][2] = 1;
        chessArray1[2][3] = 2;
        chessArray1[4][5] = 2;
        // 输出原始的二维数组
        System.out.println("原始的二维数组~~");
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(chessArray1[i][j] + "\t");
            }
            System.out.println();
        }

        // 将二维数组 转 稀疏数组的思路
        // 1. 先遍历二维数组 得到非0数据的个数
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArray1[i][j] != 0) {
                    sum++;
                }
            }
        }

        // 2. 创建对应的稀疏数组
        int[][] sparseArray = new int[sum + 1][3];
        // 给稀疏数组赋值
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = sum;

        // 遍历二维数组，将非0的值存放到sparseArr中
        int count = 0;//count 用于记录是第几个非0数据
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArray1[i][j] != 0) {
                    count++;
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = chessArray1[i][j];
                }
            }
        }

        // 输出稀疏数组的形式
        System.out.println();
        System.out.println("得到稀疏数组为~~~~");

        for (int i = 0; i < sparseArray.length; i++) {
            System.out.println(sparseArray[i][0] + "\t" + sparseArray[i][1] + "\t" + sparseArray[i][2] + "\t");
        }
        System.out.println();

        //讲稀疏数组写入文件
        writeArray("resource/sparsearray.map.data", sparseArray);

        System.out.println("从文件中读取稀疏数组");
        // 相对路径要注意下
        int[][] ints = readArray("resource/sparsearray.map.data", count);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(sparseArray[i][0] + "\t" + sparseArray[i][1] + "\t" + sparseArray[i][2] + "\t");
        }

        //将稀疏数组 --> 恢复成 原始的二维数组
		/*
		 *  1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的 chessArr2 = int [11][11]
			2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可。
		 */

        //1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组

        int[][] chessArray2 = new int[sparseArray[0][0]][sparseArray[0][1]];

        //2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可
        for (int i = 1; i < sparseArray.length; i++) {
            chessArray2[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }

        // 输出恢复后的二维数组
        System.out.println();
        System.out.println("恢复后的二维数组");

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(chessArray2[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void writeArray(String fileName, int[][] array) {
        // try-with-resource
        try (
                FileWriter fw = new FileWriter(fileName);
                BufferedWriter bw = new BufferedWriter(fw)
        ) {
            for (int[] ints : array) {
                for (int anInt : ints) {
                    bw.write(anInt + "\t");
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] readArray(String fileName, int count) {
        int[][] sparseArray = new int[count + 1][3];
        // try-with-resource
        try (
                FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr)
        ) {
            int newCount= 0;
            String line;
            while((line = br.readLine()) != null) {
                String[] split = line.split("\t");
                sparseArray[newCount][0] = Integer.valueOf(split[0]);
                sparseArray[newCount][1] = Integer.valueOf(split[1]);
                sparseArray[newCount][2] = Integer.valueOf(split[2]);
                newCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sparseArray;
    }
}
