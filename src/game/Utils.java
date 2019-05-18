package game;

import java.util.Random;

public class Utils {
    private static Random random = new Random();

    public static int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static double randomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static int percentOfInt(int percent, int number){
        return percent / number * 100;
    }
}
