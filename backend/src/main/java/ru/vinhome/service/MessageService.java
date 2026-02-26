package ru.vinhome.service;

import ru.vinhome.controller.dto.MessageCreateRequest;
import ru.vinhome.controller.dto.MessageUpdateRequest;
import ru.vinhome.model.Message;

public interface MessageService
        extends CrudService<Message, MessageCreateRequest, MessageUpdateRequest, Integer>, TableManagement {

}
