
public class SortingStats {
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void bubbleSort(int array[]) {
        int swaps = 0;
        int prevSwaps = 0;
        int comparisons = 0;
        Visualization viz = new Visualization();
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                comparisons++;
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    swaps++;
                    viz.updateData(array);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (prevSwaps == swaps)
                    break;
                prevSwaps = swaps;
        }
        System.out.println("Number of swaps: " + swaps +
                    "\nNumber of comparisons: " + comparisons);
    }
}