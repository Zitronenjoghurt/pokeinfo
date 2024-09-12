package industries.lemon.pokeinfo.utils;

public class UnitsUtils {
    public static String formatHectogramsMetric(int hectogram) {
        double kilogram = hectogram / 10.0;

        if (kilogram < 1.0) {
            int grams = (int)kilogram * 1000;
            return String.format("%dg", grams);
        } else {
            return String.format("%.1fkg", kilogram);
        }
    }

    public static String formatHectogramsImperial(int hectogram) {
        double pounds = hectogram * 0.220462;
        int wholePounds = (int) pounds;
        int ounces = (int)((pounds - wholePounds) * 16);

        if (wholePounds == 0) {
            return String.format("%doz", ounces);
        } else {
            return String.format("%.1flb", pounds);
        }
    }

    public static String formatDecimetersMetric(int decimeters) {
        if (decimeters < 10) {
            return String.format("%dcm", decimeters * 10);
        } else {
            double meters = decimeters / 10.0;
            return String.format("%.1fm", meters);
        }
    }

    public static String formatDecimetersImperial(int decimeters) {
        int inches = (int)(decimeters * 3.93701);
        int feet = inches / 12;
        int remainingInches = inches % 12;

        if (feet == 0) {
            return String.format("%d\"", remainingInches);
        } else {
            return String.format("%d'%d\"", feet, remainingInches);
        }
    }
}
