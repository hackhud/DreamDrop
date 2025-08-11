package ua.hackhud.DreamDrop.Util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GradientUtil {

    public static String setHexName(String hex1, String hex2, String name) {
        RGB color1 = hexToRgb(hex1);
        RGB color2 = hexToRgb(hex2);

        StringBuilder gradientText = new StringBuilder();
        int length = name.length();

        int nonSpaceCount = 0;
        for (int i = 0; i < length; i++) {
            if (name.charAt(i) != ' ') nonSpaceCount++;
        }

        int nonSpaceIndex = 0;
        for (int i = 0; i < length; i++) {
            char currentChar = name.charAt(i);
            if (currentChar == ' ') {
                gradientText.append(currentChar);
            } else {
                double ratio = (nonSpaceCount > 1) ? (double) nonSpaceIndex / (nonSpaceCount - 1) : 0;
                int red = (int) Math.round(color1.r * (1.0 - ratio) + color2.r * ratio);
                int green = (int) Math.round(color1.g * (1.0 - ratio) + color2.g * ratio);
                int blue = (int) Math.round(color1.b * (1.0 - ratio) + color2.b * ratio);
                String hexColor = String.format("#%02X%02X%02X", red, green, blue);
                gradientText.append(hexColor).append(currentChar);
                nonSpaceIndex++;
            }
        }

        return gradientText.toString();
    }

    public static String stripHexColors(String input) {
        return input.replaceAll("#[A-Fa-f0-9]{6}", "");
    }

    private static RGB hexToRgb(String hex) {
        hex = hex.replace("#", "");
        if (hex.length() == 3) {
            hex = "" + hex.charAt(0) + hex.charAt(0)
                    + hex.charAt(1) + hex.charAt(1)
                    + hex.charAt(2) + hex.charAt(2);
        }

        int num = Integer.parseInt(hex, 16);
        int r = (num >> 16) & 0xFF;
        int g = (num >> 8) & 0xFF;
        int b = num & 0xFF;
        return new RGB(r, g, b);
    }

    private static class RGB {
        int r, g, b;

        RGB(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }
}
