package com.mypackage;

import java.util.Arrays;
import java.util.Random;

public class SortingViz {
    public static void swap(int[] array, int i, int j) throws InterruptedException {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void countingSort(int[] array, Visualization viz, int delay) throws InterruptedException {
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
            viz.updatePlot(sortedArray);
            Thread.sleep(delay);
        }
    }

    public static void bubbleSort(int array[], Visualization viz, int delay) throws InterruptedException {
        int swaps = 0;
        int prevSwaps = 0;

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    swaps++;
                    viz.updatePlot(array);
                    Thread.sleep(delay);
                }
            }
            if (prevSwaps == swaps)
                break;
            prevSwaps = swaps;
        }
    }

    public static void quickSort(int[] array, int lowIndex, int highIndex, Visualization viz, int delay)
            throws InterruptedException {
        if (lowIndex < highIndex) {
            int pivotIndex = partition(array, lowIndex, highIndex, viz, delay);
            quickSort(array, lowIndex, pivotIndex - 1, viz, delay);
            quickSort(array, pivotIndex + 1, highIndex, viz, delay);
        }
    }

    private static int partition(int[] array, int lowIndex, int highIndex, Visualization viz, int delay)
            throws InterruptedException {
        Random rand = new Random();
        int pivotIndex = lowIndex + rand.nextInt(highIndex - lowIndex + 1);
        swap(array, pivotIndex, highIndex);
        viz.updatePlot(array);
        Thread.sleep(delay);
        int pivot = array[highIndex];
        int i = lowIndex - 1;
        for (int j = lowIndex; j < highIndex; j++) {
            if (array[j] < pivot) {
                i++;
                swap(array, i, j);
                viz.updatePlot(array);
                Thread.sleep(delay);
            }
        }
        swap(array, i + 1, highIndex);
        viz.updatePlot(array);
        Thread.sleep(delay);
        return i + 1;
    }
}