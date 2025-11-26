package ru.vinhome;

import ru.vinhome.service.MessageServiceImpl;
import ru.vinhome.service.UserServiceImpl;

public class Main {

    private Main() { }

    public static void main(String[] args) throws Exception {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createTable();

        MessageServiceImpl messageService = new MessageServiceImpl();
        messageService.createTable();


        try (var wrapperJettyServer = new WrapperJettyServer()) {
            wrapperJettyServer.start();
            wrapperJettyServer.getServer().join();
        }
    }
}
