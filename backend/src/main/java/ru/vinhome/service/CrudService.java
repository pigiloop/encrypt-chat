package ru.vinhome.service;

import java.sql.SQLException;
import java.util.List;

public interface CrudService<T, I> {

    List<T> findAll() throws SQLException, InterruptedException;

    T findById(I id) throws SQLException, InterruptedException;

    int save(T obj) throws SQLException, InterruptedException;

    int update(I id, T obj) throws SQLException, InterruptedException;

    int delete(I id) throws SQLException, InterruptedException;
}
