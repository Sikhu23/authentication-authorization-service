package com.assessment.auth.Exception;

import feign.FeignException;

public class FeignExcep extends FeignException {
    FeignExcep(){
        super(400,"Email already Exists");
    }
}
