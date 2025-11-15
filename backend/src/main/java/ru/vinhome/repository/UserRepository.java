package ru.vinhome.repository;

import ru.vinhome.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserRepository {
    public boolean emailExist(String email) throws SQLException, InterruptedException;
    public User findByUsername(String username) throws SQLException, InterruptedException;
    public User findById(Long id, Connection connection) throws SQLException, InterruptedException;
}
