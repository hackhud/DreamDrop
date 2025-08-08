package ua.hackhud.DreamDrop.Entity;

import ua.hackhud.DreamDrop.Enum.AttributeType;

public class AttributeValue {
    private double value;
    private final boolean isPercent;

    public AttributeValue(double value, boolean isPercent) {
        this.value = value;
        this.isPercent = isPercent;
    }

    public double getValue() {
        return value;
    }

    public void multiply(double factor) {
        this.value *= factor;
    }

    public void add(double delta) {
        this.value += delta;
    }

    public void set(double value) {
        this.value = value;
    }

    public String toString(AttributeType type) {
        String valueStr;
        if (isPercent) {
            valueStr = (value == (int)value) ?
                    String.format("%d%%", (int)value) :
                    String.format("%.1f%%", value);
        } else {
            valueStr = (value == (int)value) ?
                    String.format("%d", (int)value) :
                    String.format("%.1f", value);
        }

        return type.color + (value >= 0 ? "+" : "") + valueStr + " " + type.lore;
    }
}
