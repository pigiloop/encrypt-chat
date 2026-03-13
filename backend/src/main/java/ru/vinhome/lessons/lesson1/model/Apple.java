package ru.vinhome.lessons.lesson1.model;

public class Apple extends Fruit {

    public Apple(String name, int weight, String color, double price) {
        super(name, weight, color, price);
    }

    @Override
    public String info() {
        return "Hello im apple🍏 ";
    }

    public static boolean filterFilter(Fruit fruit) {
        return true;
    }
}
