package ru.vinhome.service;

import ru.vinhome.model.User;

public interface UserService extends CrudService<User, Long>, IUserService, TableManagement{
}
