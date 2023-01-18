package com.Group8.ToDo.Authentication.service;

import com.Group8.ToDo.Authentication.exception.UserAlreadyRegistered;
import com.Group8.ToDo.Authentication.exception.UserNotFoundException;
import com.Group8.ToDo.Authentication.model.User;
import com.Group8.ToDo.Authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiesImpl implements UserService {
    private UserRepository repository;
    @Autowired
    public UserServiesImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User saveuserdeatilstodatabase(User user) throws UserAlreadyRegistered{

        if (repository.findById(user.getUserEmailId()).isPresent()) {
            throw new UserAlreadyRegistered();
        }
         repository.save(user);
        System.out.println(user);
        return user;
    }

    @Override
    public User resetPassword(String userEmailId, String userName,User user) throws UserNotFoundException {
        if(repository.findById(userEmailId).isEmpty())
        {
            throw new UserNotFoundException();
        }
            User userObj=repository.findByUserEmailIdAndUserName(userEmailId, userName);
            if(userObj==null)
            {
                throw new UserNotFoundException();
            }
            userObj.setUserPassword(user.getUserPassword());
            return repository.save(userObj);

    }

    @Override
    public User findByUserEmailIdAndUserPassword(String userEmailId, String userPassword) throws UserNotFoundException {

        User user=repository.findByUserEmailIdAndUserPassword(userEmailId,userPassword);

        if(user==null)
        {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User updateUser(String userEmailId, User user) throws UserNotFoundException {
        if(repository.findById(userEmailId).isPresent())
        {
            User user1=repository.findById(userEmailId).get();
            user1.setUserName(user.getUserName().substring(0, 1).toUpperCase()+user.getUserName().substring(1));
            user1.setUserPassword(user.getUserPassword());
            repository.save(user1);
            return user1;
        }
        throw new UserNotFoundException();
    }
}
