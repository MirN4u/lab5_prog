package models;

public enum Color {
    RED,
    BLUE,
    YELLOW,
    ORANGE;

    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var colorType : values()) {
            nameList.append(colorType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}