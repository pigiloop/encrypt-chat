package ru.vinhome.lessons.lesson1.filter;

import ru.vinhome.lessons.lesson1.model.Fruit;

public class WeightFilter implements Filter {
    @Override
    public boolean filter(Fruit fruit) {
        return fruit.getWeight() >= 5;
    }
}
