import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter length of array: ");
        int length = input.nextInt();
        int[] array;
        int[] randomArray = ArrayGenerator.generateRandom(length);
        int choice = -1;
        
        while (choice != 0) {
            System.out.println("Enter 1 for Counting sort" +
                    "\nEnter 2 for Bubble Sort" +
                    "\nEnter 3 for Quick Sort" +
                    "\nEnter 0 to exit");
            choice = input.nextInt();
            System.out.println();
            switch (choice) {
                case 1: {
                    array = randomArray.clone();
                    long time = System.nanoTime();
                    Sorting.countingSort(array);
                    time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);
                    System.out.println("Took " + time + "ms to run\n");
                    break;
                }
                case 2: {
                    array = randomArray.clone();
                    long time = System.nanoTime();
                    Sorting.bubbleSort(array);
                    time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);

                    array = randomArray.clone();
                    SortingStats.bubbleSort(array);
                    System.out.println("Took " + time + "ms to run\n");
                    break;
                }
                case 3: {
                    array = randomArray.clone();
                    long time = System.nanoTime();
                    Sorting.quickSort(array, 0, length - 1);
                    time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - time);
                    System.out.println("Took " + time + "ms to run\n");
                    break;
                }
                case 0:{
                    break;
                }
                default: {
                    System.out.println("Invalid input, try again\n");
                    input.nextLine();
                }
            }
        }
        input.close();
    }
}