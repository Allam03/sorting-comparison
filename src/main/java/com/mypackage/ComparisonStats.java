package com.mypackage;

import java.util.Arrays;

public class ComparisonStats {
    private int[] randomArray, sortedArray, inverselySortedArray;
    private int[] comparisonStats, swaps_comparisons;
    private long time;
    private int length;
    private boolean isSwap;

    public ComparisonStats(int[] randomArray, int[] swaps_comparisons) {
        length = randomArray.length;
        this.swaps_comparisons = swaps_comparisons;
        this.randomArray = randomArray.clone();
        sortedArray = randomArray.clone();
        Arrays.sort(sortedArray);
        inverselySortedArray = sortedArray.clone();
        Sorting.reverse(inverselySortedArray);
        comparisonStats = new int[3];
    }

    public int[] getComparisonStats() {
        return comparisonStats;
    }

    public void runtime(String method) {
        switch (method) {
            case "Counting":
                countingSortRuntime();
                break;
            case "Bubble":
                bubbleSortRuntime();
                break;
            case "Quick":
                quickSortRuntime();
                break;
            default:
                break;
        }
    }

    private void countingSortRuntime() {
        reset(swaps_comparisons);
        time = System.currentTimeMillis();
        Sorting.countingSort(randomArray);
        time = System.currentTimeMillis() - time;
        comparisonStats[0] = (int) time;

        reset(swaps_comparisons);
        time = System.currentTimeMillis();
        Sorting.countingSort(inverselySortedArray);
        time = System.currentTimeMillis() - time;
        comparisonStats[1] = (int) time;
        
        reset(swaps_comparisons);
        time = System.currentTimeMillis();
        Sorting.countingSort(sortedArray);
        time = System.currentTimeMillis() - time;
        comparisonStats[2] = (int) time;
    }

    private void bubbleSortRuntime() {
        reset(swaps_comparisons);
        time = System.currentTimeMillis();
        Sorting.bubbleSort(randomArray, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[0] = (int) time;

        reset(swaps_comparisons);
        time = System.currentTimeMillis();
        Sorting.bubbleSort(inverselySortedArray, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[1] = (int) time;

        reset(swaps_comparisons);
        time = System.currentTimeMillis();
        Sorting.bubbleSort(sortedArray, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[2] = (int) time;
    }

    private void quickSortRuntime() {
        reset(swaps_comparisons);
        time = System.currentTimeMillis();
        Sorting.quickSort(randomArray, 0, length - 1, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[0] = (int) time;

        reset(swaps_comparisons);
        time = System.currentTimeMillis();
        Sorting.quickSort(inverselySortedArray, 0, length - 1, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[1] = (int) time;

        reset(swaps_comparisons);
        time = System.currentTimeMillis();
        Sorting.quickSort(sortedArray, 0, length - 1, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[2] = (int) time;
    }

    public void swaps(String method) {
        isSwap = true;
        switch (method) {
            case "Counting":
                for (int i = 0; i < 3; i++)
                    comparisonStats[i] = 0;
                break;

            case "Bubble":
                bubbleSortStats();
                break;

            case "Quick":
                quickSortStats();
                break;

            default:
                break;
        }
    }

    public void comparisons(String method) {
        isSwap = false;
        switch (method) {
            case "Counting":
                for (int i = 0; i < 3; i++)
                    comparisonStats[i] = 0;
                break;

            case "Bubble":
                bubbleSortStats();
                break;

            case "Quick":
                quickSortStats();
                break;

            default:
                break;
        }
    }

    private void bubbleSortStats() {
        int index;
        if (isSwap) {
            index = 0;
        } else {
            index = 1;
        }

        reset(swaps_comparisons);
        Sorting.bubbleSort(randomArray, swaps_comparisons);
        comparisonStats[0] = swaps_comparisons[index];

        reset(swaps_comparisons);
        Sorting.bubbleSort(inverselySortedArray, swaps_comparisons);
        comparisonStats[1] = swaps_comparisons[index];

        reset(swaps_comparisons);
        Sorting.bubbleSort(sortedArray, swaps_comparisons);
        comparisonStats[2] = swaps_comparisons[index];
    }

    private void quickSortStats() {
        int index;
        if (isSwap) {
            index = 0;
        } else {
            index = 1;
        }
        reset(swaps_comparisons);
        Sorting.quickSort(randomArray, 0, length - 1, swaps_comparisons);
        comparisonStats[0] = swaps_comparisons[index];

        reset(swaps_comparisons);
        Sorting.quickSort(inverselySortedArray, 0, length - 1, swaps_comparisons);
        comparisonStats[1] = swaps_comparisons[index];

        reset(swaps_comparisons);
        Sorting.quickSort(sortedArray, 0, length - 1, swaps_comparisons);
        comparisonStats[2] = swaps_comparisons[index];
    }

    private void reset(int[] array){
        array[0] = 0;
        array[1] = 0;
    }
}