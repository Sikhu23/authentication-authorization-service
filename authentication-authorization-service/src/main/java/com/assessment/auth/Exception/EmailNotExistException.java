package com.assessment.auth.Exception;

public class EmailNotExistException extends RuntimeException{

    public EmailNotExistException(String msg){
        super(msg);
    }
}
