package com.secondshelf.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String msg){
        super(msg);
    }
    ResourceAlreadyExistsException(){

    }

}
