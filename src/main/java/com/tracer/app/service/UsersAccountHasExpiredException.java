package com.tracer.app.service;

public class UsersAccountHasExpiredException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UsersAccountHasExpiredException() {
        super("The users account has expired !");
    }
}
