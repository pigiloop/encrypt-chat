package ru.vinhome.service;

import ru.vinhome.model.Message;
import ru.vinhome.repository.JdbcMessageRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;


public class MessageServiceImpl implements CrudService<Message, Long>, TableManagement {


    private final JdbcMessageRepositoryImpl jdbcMessageRepository;

    public MessageServiceImpl(JdbcMessageRepositoryImpl jdbcMessageRepository) {
        this.jdbcMessageRepository = jdbcMessageRepository;
    }

    public MessageServiceImpl() {
        this.jdbcMessageRepository = new JdbcMessageRepositoryImpl();
    }

    @Override
    public ArrayList<Message> findAll() throws SQLException, InterruptedException {
        return jdbcMessageRepository.findAll();
    }

    @Override
    public Message findById(Long id) throws SQLException, InterruptedException {
        return jdbcMessageRepository.findById(id);
    }

    @Override
    public int save(Message obj) throws SQLException, InterruptedException {
        return jdbcMessageRepository.save(obj);
    }

    @Override
    public int update(Long id, Message obj) throws SQLException, InterruptedException {
        return jdbcMessageRepository.update(id, obj);
    }

    @Override
    public int delete(Long id) throws SQLException, InterruptedException {
        return jdbcMessageRepository.delete(id);
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
