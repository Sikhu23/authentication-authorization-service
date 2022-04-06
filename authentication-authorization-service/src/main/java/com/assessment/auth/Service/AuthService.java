package com.assessment.auth.Service;


import com.assessment.auth.Enum.BloodGroup;
import com.assessment.auth.Exception.*;
import com.assessment.auth.Feign.FeignUser;
import com.assessment.auth.Model.AuthDTO;
import com.assessment.auth.Model.JWTRequest;
import com.assessment.auth.Model.UserDto;
import com.assessment.auth.Model.UserWithOutPassword;
import com.assessment.auth.Repository.AuthRepo;
import com.assessment.auth.Util.JwtUtil;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FeignUser feignUser;

    @Autowired
    private AuthRepo authRepo;

public AuthDTO signup(UserDto userDto){

    boolean flag=false;
    String bg = userDto.getBloodGroup();
    BloodGroup[] bgs= BloodGroup.values();
    for(BloodGroup bloodGroup:bgs){
        if(String.valueOf(bloodGroup.getGroup()).equals(bg)){
            flag=true;
            break;

        }
    }

if(flag) {


    try {
        feignUser.getUserDetailsByEmail(userDto.getEmail());
        throw new EmailAlreadyExistsException("Email ALready Exists");


    } catch (FeignException e) {


        AuthDTO authDTO = new AuthDTO();
        System.out.println(new BCryptPasswordEncoder().encode(userDto.getPassword()));


        feignUser.createUser(userDto);
        JWTRequest jwtRequest = new JWTRequest(userDto.getEmail(), new BCryptPasswordEncoder().encode(userDto.getPassword()));
        authRepo.save(jwtRequest);
        authDTO.setUser(feignUser.getUserDetailsByEmail(userDto.getEmail()));
        authDTO.setToken(jwtUtil.generateToken(jwtRequest.getEmail()));

        return authDTO;


//        return new UserWithOutPassword(userDto.getUserID(), userDto.getFirstName(), userDto.getMiddleName(),
//                userDto.getLastName(), userDto.getPhoneNumber(), userDto.getDateOfBirth(), userDto.getGender(), userDto.getAddress(),
//                userDto.getEmployeeNumber(), userDto.getBloodGroup(), userDto.getEmail());

    }
}
else{
    throw new EnumException("Blood Group is Enum and cant have value as "+userDto.getBloodGroup() );
    }
}



public AuthDTO login(JWTRequest jwtRequest){
    JWTRequest jwtRequest1 = authRepo.findByemail(jwtRequest.getEmail());
    if(jwtRequest1==null){
        throw new EmailNotExistException("Email Doesnot Exists");
    }
    if(new BCryptPasswordEncoder().matches(jwtRequest.getPassword(),jwtRequest1.getPassword())){

            AuthDTO authDTO=new AuthDTO();
            authDTO.setToken(jwtUtil.generateToken(jwtRequest.getEmail()));
            authDTO.setUser(feignUser.getUserDetailsByEmail(jwtRequest.getEmail()));
            return  authDTO;



    }
    else{
        throw new PasswordWrongException("Password doesnt match");
    }

}



}
