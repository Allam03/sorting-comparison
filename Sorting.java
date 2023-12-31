import java.util.Arrays;

public class Sorting {
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void countingSort(int[] array) {
        int max = Arrays.stream(array).max().orElse(0);
        int min = Arrays.stream(array).min().orElse(0);

        int range = max - min + 1;
        int[] count = new int[range];

        for (int i = 0; i < array.length; i++) {
            count[array[i] - min]++;
        }

        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }

        int[] sortedArray = new int[array.length];
        int index;
        
        for (int i = array.length - 1; i >= 0; i--) {
            index = array[i] - min;
            sortedArray[count[index] - 1] = array[i];
            count[index]--;
        }
        System.arraycopy(sortedArray, 0, array, 0, array.length);
    }

    public static void bubbleSort(int array[]) {
        int swaps = 0;
        int prevSwaps = 0;

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    swaps++;
                }
            }
            if (prevSwaps == swaps)
                break;
            prevSwaps = swaps;
        }

    }

    public static void quickSort(int[] array, int lowIndex, int highIndex) {
        if (lowIndex < highIndex) {
            int pivotIndex = partition(array, lowIndex, highIndex);
            quickSort(array, lowIndex, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, highIndex);
        }
    }
    
    private static int partition(int[] array, int lowIndex, int highIndex) {
        int pivot = array[highIndex];
        int i = lowIndex - 1;
        for (int j = lowIndex; j < highIndex; j++) {
            if (array[j] < pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, highIndex);
        return i + 1;
    }    
}