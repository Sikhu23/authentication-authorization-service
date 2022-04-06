package com.assessment.auth.Controller;

import com.assessment.auth.Model.AuthDTO;
import com.assessment.auth.Model.JWTRequest;
import com.assessment.auth.Model.UserDto;
import com.assessment.auth.Model.UserWithOutPassword;
import com.assessment.auth.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.auth.Util.JwtUtil;

import javax.validation.Valid;

@RestController
public class AuthRestController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthService authService;

	@PostMapping("/auth/login")
	public ResponseEntity<AuthDTO> login(@RequestBody JWTRequest jwtRequest) {



		return new ResponseEntity<>(authService.login(jwtRequest), HttpStatus.OK);
	}

	@PostMapping("/auth/signup")
	public ResponseEntity<AuthDTO> register(@Valid @RequestBody UserDto userDto) {


		return new ResponseEntity<>(authService.signup(userDto), HttpStatus.OK);
	}

}
