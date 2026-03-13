package ru.vinhome.lessons.lesson1.model;

public class Orange extends Fruit {

    public Orange(String name, int weight, String color, double price) {
        super(name, weight, color, price);
    }

    @Override
    public String info() {
        return "Hello im orange 🍊";
    }
}
