package com.bridgelabz.adminservice.service;

import com.bridgelabz.adminservice.dto.ChangePasswordDTO;
import com.bridgelabz.adminservice.dto.LoginDTO;
import com.bridgelabz.adminservice.dto.UserDTO;
import com.bridgelabz.adminservice.entity.User;
import org.apache.catalina.connector.Response;

import java.util.List;

public interface IUserService {
    public String registerUser(UserDTO userdto);

    public List<User> getAllRecords();

    public User getRecord(Integer id);

    public String getToken(String email);

    public User getRecordByToken(String token);

    public User updateRecord(Integer id, UserDTO dto);
    public User statusRecord(Integer id, UserDTO dto);
    public User userLogin(LoginDTO logindto);

    public User changePassword(ChangePasswordDTO dto);
}
