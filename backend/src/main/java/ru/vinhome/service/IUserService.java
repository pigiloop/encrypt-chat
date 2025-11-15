package ru.vinhome.service;

import java.sql.SQLException;

public interface IUserService {
    public boolean emailExist(String email) throws SQLException, InterruptedException;
}
