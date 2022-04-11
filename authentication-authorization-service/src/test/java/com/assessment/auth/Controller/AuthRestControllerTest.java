package com.assessment.auth.Controller;

import com.assessment.auth.Enum.Gender;
import com.assessment.auth.Model.AuthDTO;
import com.assessment.auth.Model.JWTRequest;
import com.assessment.auth.Model.UserDto;
import com.assessment.auth.Model.UserWithOutPassword;
import com.assessment.auth.Service.AuthService;
import com.assessment.auth.Util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(AuthRestController.class)
class AuthRestControllerTest {

    @MockBean
    AuthService authService;

    @MockBean
    JwtUtil jwtUtil;

    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private UserWithOutPassword createOneUser() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        UserWithOutPassword user1 = new UserWithOutPassword();

        user1.setUserID("1");
        user1.setFirstName("Natsu");
        user1.setMiddleName("S");
        user1.setLastName("D");
        user1.setPhoneNumber("9090909090");
        user1.setEmail("sikhu@mail.com");
        user1.setDateOfBirth(c);
        user1.setEmployeeNumber("12345");
        user1.setBloodGroup("O+");
        user1.setGender((Gender.MALE));
        user1.setAddress("Pune");
        return user1;
    }

    private UserDto createOneUserToPost() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        UserDto user1 = new UserDto();
        user1.setUserID("1");
        user1.setPassword("123");
        user1.setFirstName("Natsu");
        user1.setMiddleName("S");
        user1.setLastName("D");
        user1.setPhoneNumber("9090909090");
        user1.setEmail("sikhu@mail.com");
        user1.setDateOfBirth(c);
        user1.setEmployeeNumber("12345");
        user1.setBloodGroup("O+");
        user1.setGender((Gender.MALE));
        user1.setAddress("Pune");
        return user1;

    }

    @Test
    void login() throws Exception {
        JWTRequest jwtRequest = new JWTRequest("sikhu@mail.com","123");


        UserWithOutPassword userDTO = createOneUser();
        String dummyToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        AuthDTO authDTO=new AuthDTO(dummyToken,userDTO);


        Mockito.when(authService.login(jwtRequest)).thenReturn(authDTO);
        mockMvc.perform(post("/auth/login")
                        .content(asJsonString(jwtRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.is(dummyToken)));

    }

    @Test
    void register() throws Exception {

        UserDto user = createOneUserToPost();

        UserWithOutPassword userDTO = createOneUser();
        String dummyToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        AuthDTO authDTO=new AuthDTO(dummyToken,userDTO);

        Mockito.when(authService.signup(user)).thenReturn(authDTO);
        mockMvc.perform(post("/auth/signup")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.is(dummyToken)));

    }
}