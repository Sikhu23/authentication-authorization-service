package com.assessment.auth.Repository;

import com.assessment.auth.Model.JWTRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends CrudRepository<JWTRequest,String> {


    public JWTRequest findByemail(String email);
}
