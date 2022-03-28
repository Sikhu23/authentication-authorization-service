package com.mavericsystems.authenticationauthorizationservice.Controller;


import com.mavericsystems.authenticationauthorizationservice.Const.ConstFile;
import com.mavericsystems.authenticationauthorizationservice.Model.LoginRequest;
import com.mavericsystems.authenticationauthorizationservice.Model.UserDto;
import com.mavericsystems.authenticationauthorizationservice.Model.UserWithOutPassword;
import com.mavericsystems.authenticationauthorizationservice.Feign.FeignUser;
import com.mavericsystems.authenticationauthorizationservice.Model.JWTRequest;
import com.mavericsystems.authenticationauthorizationservice.Model.JWTResponse;
import com.mavericsystems.authenticationauthorizationservice.Repository.AuthorisationRepo;
import com.mavericsystems.authenticationauthorizationservice.Service.UserService;
import com.mavericsystems.authenticationauthorizationservice.Utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@CrossOrigin(value="*")
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserService userService;

    @Autowired
    FeignUser feignUser;

    @Autowired
    AuthorisationRepo authorisationRepo;

    @CrossOrigin(value = "*")
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                     loginRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            System.out.println("I am here   ");
            throw new Exception(ConstFile.errorCode, ex);
        }

        final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
        final String token = jwtUtility.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new JWTResponse(token, feignUser.getUserDetailsByEmail(loginRequest.getEmail())));
    }

    @CrossOrigin(value = "*")
    @PostMapping("/signup")
    public ResponseEntity<JWTResponse> signup(@RequestBody UserDto userDto) throws Exception {
        JWTRequest jwtRequest = new JWTRequest();
        jwtRequest.setEmail(userDto.getEmail());
        jwtRequest.setPassword(userDto.getPassword());

        authorisationRepo.save(jwtRequest);
        UserWithOutPassword userWithOutPassword = feignUser.createUser(userDto);
        final UserDetails userDetails = new User(userDto.getEmail(),userDto.getPassword(), new ArrayList<>());
        final String token = jwtUtility.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new JWTResponse(token,userWithOutPassword));
    }
}
