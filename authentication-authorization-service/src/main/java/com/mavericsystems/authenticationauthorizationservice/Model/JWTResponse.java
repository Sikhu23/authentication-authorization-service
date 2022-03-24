package com.mavericsystems.authenticationauthorizationservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTResponse {

    private String jwtToken;
    private UserWithOutPassword userWithOutPassword;

}
