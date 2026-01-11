package ru.vinhome.config;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import ru.vinhome.repository.JdbcUserRepositoryImpl;
import ru.vinhome.service.UserService;
import ru.vinhome.service.UserServiceImpl;

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {

        // USER
        // User dao
        final var jdbcUserRepository = new JdbcUserRepositoryImpl();
        // User service
        final var userService = new UserServiceImpl(jdbcUserRepository);
        bind(userService).to(UserService.class);
    }
}
