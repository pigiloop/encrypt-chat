package ru.vinhome;


/**
 * Класс утилит для простых арифметических операций с целыми числами.
 *
 * <p>Предназначен для выполнения базовых математических вычислений: сложение и вычитание. Каждый
 * метод принимает два аргумента типа {@code int} и возвращает результат типа {@code long}.
 * Использование типа {@code long} защищает от переполнения при работе с большими числами.
 *
 * <p>Пример использования:
 *
 * <pre>
 *     long sum = Calculate.plus(100, 200);    // 300
 *     long diff = Calculate.minus(100, 50);   // 50
 * </pre>
 */
public class Calculation {

    private Calculation() { }

    /**
     * Выполняет сложение двух целых чисел.
     *
     * @param leftOperand первый аргумент (слагаемое)
     * @param rightOperand второй аргумент (слагаемое)
     * @return сумма аргументов в виде {@code long} (если результат выходит за пределы int,
     *     переполнения не произойдет)
     */
    public static long plus(final int leftOperand, final int rightOperand) {
        return leftOperand + rightOperand;
    }

    /**
     * Выполняет вычитание второго целого числа из первого.
     *
     * @param leftOperand уменьшаемое
     * @param rightOperand вычитаемое
     * @return разность аргументов в виде {@code long} (если результат выходит за пределы int,
     *     переполнения не произойдет)
     */
    public static long minus(final int leftOperand, final int rightOperand) {
        return leftOperand - rightOperand;
    }
}
