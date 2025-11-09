package ru.vinhome.repository;

import java.sql.SQLException;

public interface UserRepository {
    public boolean emailExist(String email) throws SQLException, InterruptedException;
}
