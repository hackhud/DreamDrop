package ua.hackhud.DreamDrop.Util;

public class RandomUtils {

    public static Number getSkewedRandom(double min, double max, double skew, double step) {
        double r = Math.pow(Math.random(), skew);
        double value = min + (max - min) * r;
        double rounded = Math.round(value / step) * step;
        rounded = Math.max(min, Math.min(rounded, max));
        return step == 1.0 ? (int) rounded : Math.round(rounded * 10.0) / 10.0;
    }

}
