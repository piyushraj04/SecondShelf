package com.secondshelf.exception;

public class InactiveUserException extends RuntimeException{
    public InactiveUserException(String msg){
        super(msg);
    }
}
