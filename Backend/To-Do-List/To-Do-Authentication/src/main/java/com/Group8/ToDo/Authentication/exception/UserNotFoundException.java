package com.Group8.ToDo.Authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "User Not Found In Data Base")
public class UserNotFoundException extends Exception{
}
