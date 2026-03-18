package ru.vinhome.lessons.lesson2;

import java.util.ArrayList;
import java.util.List;

public class GenericLesson {

    public static void main(String[] args) {
        List<Number> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(1.0);

    }


    //Object -> ZOO -> Cat
    //              -> Dog
    private static <S> List<? super S> sumList(List<? extends S> producerZoo, List<? super S> consumerSuper) {
        consumerSuper.addAll(producerZoo);
        return consumerSuper;
    }
}
