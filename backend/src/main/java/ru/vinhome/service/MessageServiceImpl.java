package ru.vinhome.service;

import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcUserRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class MessageServiceImpl implements CrudService<User, Long>, TableManagement {

    private final JdbcUserRepositoryImpl jdbcMessageRepository;

    public MessageServiceImpl(JdbcUserRepositoryImpl jdbcUserRepository) {
        this.jdbcMessageRepository = jdbcUserRepository;
    }

    @Override
    public List<User> findAll() throws SQLException, InterruptedException {
        return jdbcMessageRepository.findAll();
    }

    @Override
    public User findById(Long id) throws SQLException, InterruptedException {
        return jdbcMessageRepository.findById(id);
    }

    @Override
    public int save(User obj) throws SQLException, InterruptedException {
        return jdbcMessageRepository.save(obj);
    }

    @Override
    public void update(Long id, User obj) throws SQLException, InterruptedException {
        jdbcMessageRepository.update(id, obj);
    }

    @Override
    public void delete(Long id) throws SQLException, InterruptedException {
        jdbcMessageRepository.delete(id);
    }

    @Override
    public void createTable() throws SQLException, InterruptedException {
        jdbcMessageRepository.createTable();
    }

    @Override
    public void dropTable() throws SQLException, InterruptedException {
        jdbcMessageRepository.dropTable();
    }
}
