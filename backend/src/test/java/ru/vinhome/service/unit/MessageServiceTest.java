package ru.vinhome.service.unit;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.service.UserServiceImpl;

public class MessageServiceTest {


    @Mock
    private JdbcUserRepositoryImpl jdbcUserRepository;

    @InjectMocks
    private UserServiceImpl userService;

    public void findById() {

    }
}
