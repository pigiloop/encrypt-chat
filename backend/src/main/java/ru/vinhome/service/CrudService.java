package ru.vinhome.service;

import java.sql.SQLException;
import java.util.List;

public interface CrudService<T, I> {

    List<T> findAll() throws SQLException, InterruptedException;

    T findById(I id) throws SQLException, InterruptedException;

    int save(T obj) throws SQLException, InterruptedException;

    void update(I id, T obj) throws SQLException, InterruptedException;

    void delete(I id) throws SQLException, InterruptedException;
}
