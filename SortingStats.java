
public class SortingStats {
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void bubbleSort(int array[], int[] stats, Visualization viz, int delay) {
        int prevSwaps = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                stats[1]++;
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    stats[0]++;
                    viz.updateData(array);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (prevSwaps == stats[0])
                    break;
                prevSwaps = stats[0];
        }
    }
}