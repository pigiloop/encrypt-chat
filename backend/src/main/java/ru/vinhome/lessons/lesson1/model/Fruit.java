package ru.vinhome.lessons.lesson1.model;

import java.util.Objects;

public abstract class Fruit {

    private String name;

    private int weight;

    private String color;

    private double price;

    public Fruit(String name, int weight, String color, double price) {
        this.name = name;
        this.weight = weight;
        this.color = color;
        this.price = price;
    }

    public abstract String info();


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Fruit{" +
               "name='" + name + '\'' +
               ", weight=" + weight +
               ", color='" + color + '\'' +
               ", price=" + price +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fruit fruit = (Fruit) o;
        return weight == fruit.weight && Double.compare(price, fruit.price) == 0 && Objects.equals(name, fruit.name) && Objects.equals(color, fruit.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, color, price);
    }
}
