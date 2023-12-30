import java.util.Random;

public class ArrayGenerator {
    public static int[] generateRandom(int length){
        Random rand = new Random();
        //rand.setSeed(123);
        int output[] = new int[length];
        for (int i = 0; i < output.length; i++){
            output[i] = rand.nextInt(length) + 1;
        }
        return output;
    }
}
