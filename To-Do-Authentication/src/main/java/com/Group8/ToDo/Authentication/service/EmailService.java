package com.Group8.ToDo.Authentication.service;

import com.Group8.ToDo.Authentication.model.User;

import org.springframework.stereotype.Service;


@Service
public interface EmailService {
    public String loginUser(User user);

}
