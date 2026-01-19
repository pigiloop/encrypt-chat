package ru.vinhome;

/**
 * Класс Main служит точкой входа для запуска сервера
 */
public class StartClass {

    /**
     * Приватный конструктор.
     * Создан для того чтобы не создавался конструктор по умолчанию.
     *
     */
    private StartClass() { }

    /**
     * Точка входа в класс Main. Стартует сервер.
     * Создан как пример
     * @param args входные аргументы, в программе никак не используются
     * @exception Exception срабатывает в случае любого исключения
     */
    public static void main(String[] args) throws Exception {

        try (var wrapperJettyServer = new WrapperJettyServer()) {
            wrapperJettyServer.start();
            wrapperJettyServer.getServer().join();
        }
    }
}
