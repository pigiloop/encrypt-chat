package ru.vinhome.lessons.lesson1.filter;

import ru.vinhome.lessons.lesson1.model.Fruit;

@FunctionalInterface
public interface Filter {

    Filter NONE_FILTER = new Filter() {
        @Override
        public boolean filter(Fruit fruit) {
            return true;
        }
    };

    Filter GREEN_FILTER = new Filter() {
        @Override
        public boolean filter(Fruit fruit) {
            return fruit.getColor().equalsIgnoreCase("green");
        }
    };

    Filter WEIGHT_FILTER = new Filter() {
        @Override
        public boolean filter(Fruit fruit) {
            return fruit.getWeight() >= 5;
        }
    };

    boolean filter(Fruit fruit);

    default int getWeight() {
        return 1;
    }

    static String getColor() {
        return "green";
    }
}
