import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void printMatrix(int[][] array, int n){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n;
        System.out.print("Введите размерность матрицы: ");
        n = in.nextInt();
        int matrix_quick[][] = new int[n][n];
        int matrix_shell[][] = new int[n][n];
        int matrix_integrated[][] = new int[n][n];
        Random generator = new Random();
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                matrix_shell[i][j] = generator.nextInt(500);
                matrix_quick[i][j] =  matrix_shell[i][j];
                matrix_integrated[i][j] =  matrix_shell[i][j];
            }
        }

        System.out.println();
        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < n; i++){
            QuickSort.quickSort(matrix_quick[i], 0, n - 1);
        }
        long quickSortTime = System.currentTimeMillis() - startTime1;
        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < n; i++){
            ShellSort.shellSort(matrix_shell[i]);
        }
        long shellSortTime = System.currentTimeMillis() - startTime2;
        long startTime3 = System.currentTimeMillis();
        for (int i = 0; i < n; i++){
            Arrays.sort(matrix_integrated[i]);
        }
        long integratedSortTime = System.currentTimeMillis() - startTime3;
        System.out.println("Время Шелл: " + shellSortTime);
        System.out.println("Время КвикСорт: " + quickSortTime);
        System.out.println("Время встроенная сортировка: " + integratedSortTime);
        System.out.println();

}
}
