package ru.vinhome.service;

import java.sql.SQLException;

public interface TableManagement {

    void createTable() throws SQLException, InterruptedException;

    void dropTable() throws SQLException, InterruptedException;
}
