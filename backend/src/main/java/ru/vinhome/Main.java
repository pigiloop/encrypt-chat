package ru.vinhome;

public class Main {

    private Main() { }

    public static void main(String[] args) throws Exception {

        try (var wrapperJettyServer = new WrapperJettyServer()) {
            wrapperJettyServer.start();
            wrapperJettyServer.getServer().join();
        }
    }
}
