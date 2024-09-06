package com.spring_boot.CSTS.Exception;

public class TeamNotFoundException extends RuntimeException{
    public TeamNotFoundException(String message){
        super(message);
    }
}
