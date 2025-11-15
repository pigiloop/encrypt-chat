package ru.vinhome;

public class Main {
    public static void main(String[] args) throws Exception {
        try (var wrapperJettyServer = new WrapperJettyServer()) {
            System.out.println("Before start server");
            wrapperJettyServer.start();
            System.out.println("After start server");
            System.out.println("Before join server");
            wrapperJettyServer.getServer().join();
        }
    }
}
