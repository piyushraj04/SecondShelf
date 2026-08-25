package com.secondshelf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseStructure<T>{
    private int statusCode;
    private String message;
    private T data;
}
