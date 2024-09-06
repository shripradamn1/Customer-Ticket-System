package com.spring_boot.CSTS.Exception;

public class SupportAgentNotFoundException extends RuntimeException {
    public SupportAgentNotFoundException(String message){
        super(message);
    }
}
