package models;

import utility.Validatable;

public class Coordinates implements Validatable {
    private Double x; //Поле не может быть null
    private long y; //Максимальное значение поля: 34

    public Coordinates(Double x, long y) {
        this.x = x;
        this.y = y;
    }

    public Integer getNum() {
        return x.intValue() + Math.toIntExact(y);
    }

    public Coordinates(String s) {
        try {
            try {this.x = s.split(" ; ")[0].equals("null") ? null : Double.parseDouble(s.split(" ; ")[0]); } catch (NumberFormatException e) {}
            try {this.y = Long.parseLong(s.split(";")[1]);} catch (NumberFormatException e) {}
        } catch (ArrayIndexOutOfBoundsException e) {}
    }

    @Override
    public boolean validate() {
        if (x == null || y >= 34) return false;
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates that = (Coordinates) obj;
        return x.equals(that.x) && y == that.y;
    }

    @Override
    public int hashCode() {
        return (int) (x.hashCode() + y);
    }

    @Override
    public String toString() {
        return x + ";" + y;
    }

}
