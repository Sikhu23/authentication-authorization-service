package com.mavericsystems.authenticationauthorizationservice.Service;

import com.mavericsystems.authenticationauthorizationservice.Model.JWTRequest;
import com.mavericsystems.authenticationauthorizationservice.Repository.AuthorisationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    AuthorisationRepo authorisationRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        JWTRequest jwtRequest = authorisationRepo.findByEmail(email);

        return new User(jwtRequest.getEmail(), jwtRequest.getPassword(), new ArrayList<>());
    }


}