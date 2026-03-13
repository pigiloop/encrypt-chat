package ru.vinhome.lessons.lesson1.filter;

import ru.vinhome.lessons.lesson1.model.Fruit;

public class NoneFilter implements Filter {
    @Override
    public boolean filter(Fruit fruit) {
        return true;
    }
}
