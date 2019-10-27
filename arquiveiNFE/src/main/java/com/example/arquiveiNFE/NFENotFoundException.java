package com.example.arquiveiNFE;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NFENotFoundException extends RuntimeException {
    public NFENotFoundException(String message) {
        super(message);
    }
}
