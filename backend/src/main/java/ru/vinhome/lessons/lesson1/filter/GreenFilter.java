package ru.vinhome.lessons.lesson1.filter;

import ru.vinhome.lessons.lesson1.model.Fruit;

public class GreenFilter implements Filter {
    @Override
    public boolean filter(Fruit fruit) {
        return fruit.getColor().equalsIgnoreCase("green");
    }
}
