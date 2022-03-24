package com.mavericsystems.authenticationauthorizationservice.Repository;

import com.mavericsystems.authenticationauthorizationservice.Model.JWTRequest;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AuthorisationRepo extends JpaRepository<JWTRequest,Integer> {

    JWTRequest findByEmail(String email);
}
