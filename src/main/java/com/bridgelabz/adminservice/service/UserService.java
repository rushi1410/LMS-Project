package com.bridgelabz.adminservice.service;

import com.bridgelabz.adminservice.dto.ChangePasswordDTO;
import com.bridgelabz.adminservice.dto.LoginDTO;
import com.bridgelabz.adminservice.dto.UserDTO;
import com.bridgelabz.adminservice.entity.User;
import com.bridgelabz.adminservice.exception.LmsException;
import com.bridgelabz.adminservice.repository.UserRepository;
import com.bridgelabz.adminservice.util.EmailSenderService;
import com.bridgelabz.adminservice.util.Jms;
import com.bridgelabz.adminservice.util.TokenUtility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserService{
    //Autowired to inject dependency here
    @Autowired
    UserRepository userRepo;
    @Autowired
    EmailSenderService mailService;
    @Autowired
    TokenUtility utility;


    //Ability to serve controller's add user record api call
    public String registerUser(UserDTO userdto) {
        User newUser = new User(userdto);
        userRepo.save(newUser);
        String token = utility.createToken(newUser.getUserID());
        mailService.sendEmail(userdto.getEmail(), "Account Sign-up successfully. ", "Hello " + newUser.getFirstName() + " Your Account has been created. Your token is " + token + " Keep this token safe to access your account in future. ");
        return token;
    }

    //Ability to serve controller's user login api call
    public User userLogin(LoginDTO logindto) {
        Optional<User> newUser = userRepo.findByMail(logindto.getEmail());
        if (logindto.getEmail().equals(newUser.get().getEmail()) && logindto.getPassword().equals(newUser.get().getPassword())) {
            log.info("SuccessFully Logged In");
            return newUser.get();
        } else {

            throw new LmsException("User doesn't exists");

        }
    }

    //Ability to serve controller's get user record by token api call
    public User getRecordByToken(String token) {
        Integer id = utility.decodeToken(token);
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            throw new LmsException("User Record doesn't exists");
        } else {
            log.info("Record retrieved successfully for given token having id " + id);
            return user.get();
        }
    }

    //Ability to serve controller's get token for changing password api call
    public String getToken(String email) {
        Optional<User> user = userRepo.findByMail(email);
        String token = utility.createToken(user.get().getUserID());
        log.info("Token sent on mail successfully");
        return token;
    }

    //Ability to serve controller's get all user records api call
    public List<User> getAllRecords() {
        List<User> userList = userRepo.findAll();
        log.info("All Record Retrieved Successfully");
        return userList;
    }

    //Ability to serve controller's get user record by id api call
    public User getRecord(Integer id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            throw new LmsException("User Record doesn't exists");
        } else {
            log.info("Record retrieved successfully for id " + id);
            return user.get();
        }
    }
    //Ability to serve controller's cancel order record by id api call
    public User statusRecord(Integer id, UserDTO dto) {
        Optional<User> user = userRepo.findById(id);
        user.get().setStatus(true);
        userRepo.save(user.get());
        mailService.sendEmail(dto.getEmail(), "Status", "Hello,your status");
        return user.get();
    }

    //Ability to serve controller's update user record by id api call
    public User updateRecord(Integer id, UserDTO dto) {
        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            throw new LmsException("User Record doesn't exists");
        } else {
            User newUser = new User(id, dto);
            userRepo.save(newUser);
            log.info("User data updated successfully");
            return newUser;
        }
    }



    //Ability to serve controller's change password api call
    public User changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
        Optional<User> user = userRepo.findByMail(dto.getEmail());
        String generatedToken = utility.createToken(user.get().getUserID());
        mailService.sendEmail(user.get().getEmail(), "Welcome " + user.get().getFirstName(), generatedToken);
        if (user.isEmpty()) {
            throw new LmsException("User doesn't exists");
        } else {
            if (dto.getToken().equals(generatedToken)) {
                user.get().setPassword(dto.getNewPassword());
                userRepo.save(user.get());
                log.info("Password changes successfully");
                return user.get();
            } else {
                throw new LmsException("Invalid token");
            }
        }
    }

}
