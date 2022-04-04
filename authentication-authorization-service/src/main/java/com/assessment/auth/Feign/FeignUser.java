package com.assessment.auth.Feign;

import com.assessment.auth.Model.UserDto;
import com.assessment.auth.Model.UserWithOutPassword;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "USER-SERVICE")
public interface FeignUser {
    @PostMapping("/users")
    UserWithOutPassword createUser(UserDto userDto);


    @GetMapping("/users/getUserByEmail/{emailId}")
    UserWithOutPassword getUserDetailsByEmail(@PathVariable("emailId") String emailId);
}
