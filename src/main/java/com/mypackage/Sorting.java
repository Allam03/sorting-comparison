package com.mypackage;

import java.util.Arrays;
import java.util.Random;

public class Sorting {
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void reverse(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            swap(array, i, array.length - i - 1);
        }
    }

    public static void countingSort(int[] array) {
        int max = Arrays.stream(array).max().orElse(0);
        for (int i = 0; i < array.length; i++) {
            max = Math.max(max, array[i]);
        }

        int[] countArray = new int[max + 1];
        for (int i = 0; i < array.length; i++) {
            countArray[array[i]]++;
        }
        for (int i = 1; i <= max; i++) {
            countArray[i] += countArray[i - 1];
        }

        int[] sortedArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            sortedArray[countArray[array[i]] - 1] = array[i];
            countArray[array[i]]--;
        }
    }

    public static void bubbleSort(int array[], int[] swaps_comparisons) {
        int prevSwaps = 0;
        resetSwapsComparisons(swaps_comparisons);

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                swaps_comparisons[1]++;
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    swaps_comparisons[0]++;
                }
            }
            if (prevSwaps == swaps_comparisons[0])
                break;
            prevSwaps = swaps_comparisons[0];
        }
    }

    public static void quickSort(int[] array, int lowIndex, int highIndex, int[] swaps_comparisons) {
        if (lowIndex < highIndex) {
            int pivotIndex = partition(array, lowIndex, highIndex, swaps_comparisons);
            quickSort(array, lowIndex, pivotIndex - 1, swaps_comparisons);
            quickSort(array, pivotIndex + 1, highIndex, swaps_comparisons);
        }
    }

    private static int partition(int[] array, int lowIndex, int highIndex, int[] swaps_comparisons) {
        Random rand = new Random();
        int pivotIndex = lowIndex + rand.nextInt(highIndex - lowIndex + 1);
        swap(array, pivotIndex, highIndex);
        swaps_comparisons[0]++;
        int pivot = array[highIndex];
        int i = lowIndex - 1;

        for (int j = lowIndex; j < highIndex; j++) {
            swaps_comparisons[1]++;
            if (array[j] < pivot) {
                i++;
                swap(array, i, j);
                swaps_comparisons[0]++;
            }
        }
        swap(array, i + 1, highIndex);
        swaps_comparisons[0]++;
        return i + 1;
    }

    private static void resetSwapsComparisons(int[] array){
        array[0] = 0;
        array[1] = 0;
    }
}