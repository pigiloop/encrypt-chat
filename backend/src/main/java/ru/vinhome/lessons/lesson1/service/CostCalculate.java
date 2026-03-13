package ru.vinhome.lessons.lesson1.service;

import ru.vinhome.lessons.lesson1.filter.Filter;
import ru.vinhome.lessons.lesson1.filter.GreenFilter;
import ru.vinhome.lessons.lesson1.filter.NoneFilter;
import ru.vinhome.lessons.lesson1.filter.WeightFilter;
import ru.vinhome.lessons.lesson1.model.Apple;
import ru.vinhome.lessons.lesson1.model.Fruit;
import ru.vinhome.lessons.lesson1.model.Orange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CostCalculate {

    public static void main(String[] args) {
        final var fruits = new ArrayList<Fruit>() {{
            add(new Apple("Apple", 10, "Green", 20));
            add(new Orange("Orange", 5, "Yellow", 100));
        }};

        System.out.println(calculate(fruits, fruit -> true));

        System.out.println(calculate(fruits, fruit -> fruit.getColor().equalsIgnoreCase("green")));
        System.out.println(calculate(fruits, fruit -> fruit.getWeight() >= 5));
    }

    public static double calculate(List<Fruit> fruits, Predicate<Fruit> filter) {
        // [] [] [] [] [] [] -> | | | | | | | | -> list | obj | sum
        return fruits.stream()
                .filter(filter::test)
                .mapToDouble(fruit -> fruit.getWeight() * fruit.getPrice())
                .sum();
    }
}
