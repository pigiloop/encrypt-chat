package ru.vinhome.service;

import ru.vinhome.model.User;

import java.sql.SQLException;

public interface IUserService {
    boolean emailExist(String email) throws SQLException, InterruptedException;

    User findByUsername(String username) throws SQLException, InterruptedException;
}
