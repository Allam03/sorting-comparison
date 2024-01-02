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

    public void compareRuntime(String method) {
        switch (method) {
            case "Counting":
                countingSortCompareRuntime();
                break;
            case "Bubble":
                bubbleSortCompareRuntime();
                break;
            case "Quick":
                quickSortCompareRuntime();
                break;
            default:
                break;
        }
    }

    private void countingSortCompareRuntime() {
        time = System.currentTimeMillis();
        Sorting.countingSort(randomArray);
        time = System.currentTimeMillis() - time;
        comparisonStats[0] = (int) time;

        time = System.currentTimeMillis();
        Sorting.countingSort(inverselySortedArray);
        time = System.currentTimeMillis() - time;
        comparisonStats[1] = (int) time;

        time = System.currentTimeMillis();
        Sorting.countingSort(sortedArray);
        time = System.currentTimeMillis() - time;
        comparisonStats[2] = (int) time;
    }

    private void bubbleSortCompareRuntime() {
        time = System.currentTimeMillis();
        Sorting.bubbleSort(randomArray, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[0] = (int) time;

        time = System.currentTimeMillis();
        Sorting.bubbleSort(inverselySortedArray, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[1] = (int) time;

        time = System.currentTimeMillis();
        Sorting.bubbleSort(sortedArray, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[2] = (int) time;
    }

    private void quickSortCompareRuntime() {
        time = System.currentTimeMillis();
        Sorting.quickSort(randomArray, 0, length - 1, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[0] = (int) time;

        time = System.currentTimeMillis();
        Sorting.quickSort(inverselySortedArray, 0, length - 1, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[1] = (int) time;

        time = System.currentTimeMillis();
        Sorting.quickSort(sortedArray, 0, length - 1, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        comparisonStats[2] = (int) time;
    }

    public void compareSwaps(String method) {
        swaps_comparisons[0] = 0;
        swaps_comparisons[1] = 0;
        isSwap = true;
        switch (method) {
            case "Counting":
                for (int i = 0; i < 3; i++)
                    comparisonStats[i] = 0;
                break;

            case "Bubble":
                bubbleSortCompareSwapsComparisons();
                break;

            case "Quick":
                quickSortCompareSwapsComparisons();
                break;

            default:
                break;
        }
    }

    public void compareComparisons(String method) {
        swaps_comparisons[0] = 0;
        swaps_comparisons[1] = 0;
        isSwap = false;
        switch (method) {
            case "Counting":
                for (int i = 0; i < 3; i++)
                    comparisonStats[i] = 0;
                break;

            case "Bubble":
                bubbleSortCompareSwapsComparisons();
                break;

            case "Quick":
                quickSortCompareSwapsComparisons();
                break;

            default:
                break;
        }
    }

    private void bubbleSortCompareSwapsComparisons() {
        int index;
        if (isSwap) {
            index = 0;
        } else {
            index = 1;
        }

        Sorting.bubbleSort(randomArray, swaps_comparisons);
        comparisonStats[0] = swaps_comparisons[index];

        Sorting.bubbleSort(inverselySortedArray, swaps_comparisons);
        comparisonStats[1] = swaps_comparisons[index];

        Sorting.bubbleSort(sortedArray, swaps_comparisons);
        comparisonStats[2] = swaps_comparisons[index];
    }

    private void quickSortCompareSwapsComparisons() {
        int index;
        if (isSwap) {
            index = 0;
        } else {
            index = 1;
        }
        Sorting.quickSort(randomArray, 0, length - 1, swaps_comparisons);
        comparisonStats[0] = swaps_comparisons[index];

        Sorting.quickSort(inverselySortedArray, 0, length - 1, swaps_comparisons);
        comparisonStats[1] = swaps_comparisons[index];

        Sorting.quickSort(sortedArray, 0, length - 1, swaps_comparisons);
        comparisonStats[2] = swaps_comparisons[index];
    }
}