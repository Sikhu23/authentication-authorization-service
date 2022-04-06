package com.assessment.auth.Exception;

public class PasswordWrongException extends RuntimeException{

    public PasswordWrongException(String msg){
        super(msg);
    }
}
