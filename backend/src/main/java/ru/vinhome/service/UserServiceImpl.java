package ru.vinhome.service;

import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcUserRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements CrudService<User, Long>, TableManagement {

    private final JdbcUserRepositoryImpl jdbcUserRepository;

    public UserServiceImpl(JdbcUserRepositoryImpl jdbcUserRepository) {
        this.jdbcUserRepository = jdbcUserRepository;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User findById(Long id) throws SQLException, InterruptedException {
        return jdbcUserRepository.findById(id);
    }

    @Override
    public int save(User obj) throws SQLException, InterruptedException {
        return jdbcUserRepository.save(obj);
    }

    @Override
    public void update(Long id, User obj) throws SQLException, InterruptedException {
        jdbcUserRepository.update(id, obj);
    }

    @Override
    public void delete(Long id) throws SQLException, InterruptedException {
        jdbcUserRepository.delete(id);
    }

    @Override
    public void createTable() throws SQLException, InterruptedException {
        jdbcUserRepository.createTable();
    }

    @Override
    public void dropTable() throws SQLException, InterruptedException {
        jdbcUserRepository.dropTable();
    }
}
