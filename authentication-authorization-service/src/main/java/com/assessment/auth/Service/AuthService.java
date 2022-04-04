package com.assessment.auth.Service;


import com.assessment.auth.Feign.FeignUser;
import com.assessment.auth.Model.JWTRequest;
import com.assessment.auth.Model.UserDto;
import com.assessment.auth.Model.UserWithOutPassword;
import com.assessment.auth.Repository.AuthRepo;
import com.assessment.auth.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FeignUser feignUser;

    @Autowired
    private AuthRepo authRepo;

public UserWithOutPassword signup(UserDto userDto){
    JWTRequest jwtRequest =new JWTRequest(userDto.getEmail(),userDto.getPassword());
    authRepo.save(jwtRequest);

    feignUser.createUser(userDto);



    return new UserWithOutPassword(userDto.getUserID(),userDto.getFirstName(),userDto.getMiddleName(),
            userDto.getLastName(),userDto.getPhoneNumber(),userDto.getDateOfBirth(),userDto.getGender(),userDto.getAddress(),
            userDto.getEmployeeNumber(),userDto.getBloodGroup(),userDto.getEmail());





}

public String login(JWTRequest jwtRequest){
    JWTRequest jwtRequest1 = authRepo.findByemail(jwtRequest.getEmail());
    if(jwtRequest1.getPassword().equals(jwtRequest.getPassword())){
        return jwtUtil.generateToken(jwtRequest.getEmail());

    }
    else{
        return "User not regsitered ";
    }
}



}
