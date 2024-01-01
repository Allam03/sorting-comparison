import java.util.Arrays;

public class ComparisonStats {
    private int[] randomArray, sortedArray, inverselySortedArray, array;
    private int[] compareStatsInt;
    private long[] compareStatsLong, swaps_comparisons;
    private long time;

    public ComparisonStats(int[] randomArray, long[] swaps_comparisons) {
        this.swaps_comparisons = swaps_comparisons;
        this.randomArray = randomArray;
        sortedArray = randomArray.clone();
        Arrays.sort(sortedArray);
        inverselySortedArray = sortedArray.clone();
        Sorting.reverse(inverselySortedArray);
        compareStatsInt = new int[3];
        compareStatsLong = new long[3];
    }

    public int[] getCompareStatsInt() {
        return compareStatsInt;
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
        array = randomArray.clone();
        time = System.currentTimeMillis();
        Sorting.countingSort(array);
        time = System.currentTimeMillis() - time;
        compareStatsLong[0] = (int) time;

        array = inverselySortedArray.clone();
        time = System.currentTimeMillis();
        Sorting.countingSort(array);
        time = System.currentTimeMillis() - time;
        compareStatsLong[1] = (int) time;

        array = sortedArray.clone();
        time = System.currentTimeMillis();
        Sorting.countingSort(array);
        time = System.currentTimeMillis() - time;
        compareStatsLong[2] = (int) time;

        for (int i = 0; i < 3; i++) {
            compareStatsInt[i] = (int) compareStatsLong[i];
        }
    }

    private void bubbleSortCompareRuntime() {
        array = randomArray.clone();
        time = System.currentTimeMillis();
        Sorting.bubbleSort(array, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        compareStatsLong[0] = (int) time;

        array = inverselySortedArray.clone();
        time = System.currentTimeMillis();
        Sorting.bubbleSort(array, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        compareStatsLong[1] = (int) time;

        array = sortedArray.clone();
        time = System.currentTimeMillis();
        Sorting.bubbleSort(array, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        compareStatsLong[2] = (int) time;

        for (int i = 0; i < 3; i++) {
            compareStatsInt[i] = (int) compareStatsLong[i];
        }
    }

    private void quickSortCompareRuntime() {
        array = randomArray.clone();
        time = System.currentTimeMillis();
        Sorting.quickSort(array, 0, array.length - 1, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        compareStatsLong[0] = (int) time;

        array = inverselySortedArray.clone();
        time = System.currentTimeMillis();
        Sorting.quickSort(array, 0, array.length - 1, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        compareStatsLong[1] = (int) time;

        array = sortedArray.clone();
        time = System.currentTimeMillis();
        Sorting.quickSort(array, 0, array.length - 1, swaps_comparisons);
        time = System.currentTimeMillis() - time;
        compareStatsLong[2] = (int) time;

        for (int i = 0; i < 3; i++) {
            compareStatsInt[i] = (int) compareStatsLong[i];
        }
    }
}