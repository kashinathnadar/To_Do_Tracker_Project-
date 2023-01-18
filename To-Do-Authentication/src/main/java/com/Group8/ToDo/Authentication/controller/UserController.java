package com.Group8.ToDo.Authentication.controller;

import com.Group8.ToDo.Authentication.exception.UserAlreadyRegistered;
import com.Group8.ToDo.Authentication.exception.UserNotFoundException;
import com.Group8.ToDo.Authentication.model.User;
import com.Group8.ToDo.Authentication.repository.UserRepository;
import com.Group8.ToDo.Authentication.service.EmailService;
import com.Group8.ToDo.Authentication.service.SecurityTokenGenerator;
import com.Group8.ToDo.Authentication.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/to-do/auth")
public class UserController {

   private UserService service;
private EmailService emailService;
    private SecurityTokenGenerator token;
    ResponseEntity responseEntity;
    @Autowired
    public UserController(UserService service, SecurityTokenGenerator token,EmailService emailService) {
        this.service = service;
        this.token = token;
        this.emailService=emailService;
    }
//    Checked
    @PostMapping("/registerUser")
    public ResponseEntity<?> saveUserData(@RequestBody User user) throws UserAlreadyRegistered {
        try {
           return new ResponseEntity(service.saveuserdeatilstodatabase(user), HttpStatus.CREATED);
        }
        catch (UserAlreadyRegistered u)
        {
            throw new UserAlreadyRegistered();
        } catch (Exception e) {
            return new ResponseEntity("Internal server Error ...pls Try Later", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //checked
    @HystrixCommand(fallbackMethod = "fallBackUserLogin")
    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws UserNotFoundException {
        Map<String, String> map = null;
        try {
            User userObj = service.findByUserEmailIdAndUserPassword(user.getUserEmailId(), user.getUserPassword());
            if (userObj!=null) {
                map =token.generateToken(user);
            }
            emailService.loginUser(user);
            System.out.println("Mail send");
            responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
            System.out.println("Login SuccesFully");

        } catch (UserNotFoundException e) {

            throw new UserNotFoundException();
        } catch (Exception e) {

            responseEntity = new ResponseEntity("Server Down pls Take Some time", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
//Checked
    @PutMapping("/updateProfile/{userEmailId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String userEmailId,@RequestBody User user) throws UserNotFoundException
    {
        System.out.println("Check For Update ");
        try {
                System.out.println("User Find ");
                responseEntity = new ResponseEntity(service.updateUser(userEmailId, user), HttpStatus.CREATED);
                System.out.println("User update");
        }catch (UserNotFoundException u)
        {
            responseEntity=new ResponseEntity("User Not Found Exception",HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            responseEntity=new ResponseEntity("Check Url Its Not Correct...",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
//checked
    @PutMapping("/resetPassword/{userEmailId}/{userName}")
    ResponseEntity<?> resetPassword(@PathVariable String userEmailId,@PathVariable String userName,@RequestBody User user) throws UserNotFoundException
    {
      String Name=user.getUserName().substring(0, 1).toUpperCase()+user.getUserName().substring(1);
        System.out.println(userEmailId+"**********************************");
        try
        {
            responseEntity=new ResponseEntity(service.resetPassword(userEmailId, Name, user),HttpStatus.CREATED);
        }catch (UserNotFoundException u)
        {
            responseEntity=new ResponseEntity("User Not Found Exception ",HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            responseEntity=new ResponseEntity("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    ResponseEntity<?> fallBackUserLogin(User user){
        return new ResponseEntity<>("login fail try after some time",HttpStatus.GATEWAY_TIMEOUT);
    }
}

