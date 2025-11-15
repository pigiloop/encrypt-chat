package ru.vinhome;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculationTest {

    @Test
    @Feature("Math operations")
    @Story("Addition")
    @Description("Тест сложения двух положительных чисел")
    @DisplayName("Сложение двух положительных чисел")
    void additionOfTwoPositiveNumbers() {
        Assertions.assertEquals(20, Calculation.plus(10, 10), "addition result is invalid");
        Allure.attachment("Пример вложения", "Это пример вложенного файла");
    }

    @Test
    @Feature("Math operations")
    @Story("Addition")
    @Description("Тест сложения двух отрицательных чисел")
    void additionOfTwoNegativeNumbers() {
        Assertions.assertEquals(-20, Calculation.plus(-10, -10), "addition result is invalid");
    }

    @Test
    @Feature("Math operations")
    @Story("Subtraction")
    @Description("Тест вычитания двух положительных чисел")
    void subtractionOfTwoPositiveNumbers() {
        Assertions.assertEquals(20, Calculation.minus(40, 20), "subtraction result is invalid");
    }

    @Test
    @Feature("Math operations")
    @Story("Subtraction")
    @Description("Тест вычитания двух отрицательных чисел")
    void subtractionOfTwoNegativeNumbers() {
        Assertions.assertEquals(-20, Calculation.minus(-40, -20), "subtraction result is invalid");
    }
}
