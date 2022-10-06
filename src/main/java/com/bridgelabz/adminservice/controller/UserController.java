package com.bridgelabz.adminservice.controller;
import com.bridgelabz.adminservice.dto.ChangePasswordDTO;
import com.bridgelabz.adminservice.dto.LoginDTO;
import com.bridgelabz.adminservice.dto.ResponseDTO;
import com.bridgelabz.adminservice.dto.UserDTO;
import com.bridgelabz.adminservice.service.IUserService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    //Autowired IUserService to inject its dependency here
    @Autowired
    private IUserService userService;

    //Ability to call api to register user
    //localhost:8081/user/add
    @PostMapping("/add")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        ResponseDTO responseDTO = new ResponseDTO("User Record created successfully", userService.registerUser(userDTO));
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }

    //Ability to call api to retrieve all user records
    //localhost:8081/user/getAll
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllRecords(){
        ResponseDTO dto = new ResponseDTO("All records get successfully",userService.getAllRecords());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //Ability to call api to get user record by id
    //localhost:8081/user/get/{id}
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getRecord(@PathVariable Integer id){
        ResponseDTO dto = new ResponseDTO("Record get successfully",userService.getRecord(id));
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //Ability to call api to get token if forgot password
    //localhost:8081/user/getToken/{email}
    @GetMapping("/getToken/{email}")
    public ResponseEntity<ResponseDTO> getToken(@PathVariable String email){
        ResponseDTO dto = new ResponseDTO("Token for mail id sent on mail successfully",userService.getToken(email));
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //Ability to call api to get user record by token
    //localhost:8081/user/getByToken/{token}
    @GetMapping("/getByToken/{token}")
    public ResponseEntity<ResponseDTO> getRecordByToken(@PathVariable String token){
        ResponseDTO dto = new ResponseDTO("Record get successfully",userService.getRecordByToken(token));
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //Ability to call api to update user record by id
    //localhost:8081/user/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateRecord(@PathVariable Integer id, @Valid @RequestBody UserDTO userdto){
        ResponseDTO dto = new ResponseDTO("Record updated successfully",userService.updateRecord(id,userdto));
        return new ResponseEntity<>(dto,HttpStatus.ACCEPTED);
    }
    //Ability to call api to status record by id
    //localhost:8084/order/status/{id}
    @PutMapping("/status/{id}")
    public ResponseEntity<ResponseDTO> StatusRecord(@PathVariable Integer id, @Valid @RequestBody UserDTO userdto) {
        ResponseDTO dto = new ResponseDTO("Order Record canceled successfully", userService.statusRecord(id, userdto));
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }
    //Ability to call api to login user
    //localhost:8081/user/login
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@Valid @RequestBody LoginDTO logindto){
        ResponseDTO dto = new ResponseDTO("User logged in successfully",userService.userLogin(logindto));
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //Ability to call api to change password
    //localhost:8081/user/changepassword
    @PutMapping("/changepassword")
    public ResponseEntity<ResponseDTO> changePassword(@Valid @RequestBody ChangePasswordDTO passwordDTO){
        ResponseDTO dto = new ResponseDTO("Password changed successfully",userService.changePassword(passwordDTO));
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
