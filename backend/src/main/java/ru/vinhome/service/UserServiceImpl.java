package ru.vinhome.service;

import ru.vinhome.model.User;
import ru.vinhome.repository.JdbcUserRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserServiceImpl implements CrudService<User, Long>, TableManagement, IUserService {

    private JdbcUserRepositoryImpl jdbcUserRepository = null;

    public UserServiceImpl(JdbcUserRepositoryImpl userRepository) {
        this.jdbcUserRepository = userRepository;
    }


    @Override
    public ArrayList<User> findAll() throws SQLException, InterruptedException {
        return jdbcUserRepository.findAll();
    }

    @Override
    public User findById(Long id) throws SQLException, InterruptedException {
        return jdbcUserRepository.findById(id);
    }

    @Override
    public User findByUsername(String userName) throws SQLException, InterruptedException {
        return jdbcUserRepository.findByUsername(userName);
    }

    @Override
    public int save(User obj) throws SQLException, InterruptedException {
        return jdbcUserRepository.save(obj);
    }

    @Override
    public int update(Long id, User obj) throws SQLException, InterruptedException {
        return jdbcUserRepository.update(id, obj);
    }

    @Override
    public int delete(Long id) throws SQLException, InterruptedException {
        return jdbcUserRepository.delete(id);
    }

    @Override
    public void createTable() throws SQLException, InterruptedException {
        jdbcUserRepository.createTable();
    }

    @Override
    public void dropTable() throws SQLException, InterruptedException {
        jdbcUserRepository.dropTable();
    }

    @Override
    public boolean emailExist(String email) throws SQLException, InterruptedException {
        return jdbcUserRepository.emailExist(email);
    }
}
