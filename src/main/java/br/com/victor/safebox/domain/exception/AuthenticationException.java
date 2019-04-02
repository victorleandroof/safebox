package br.com.victor.safebox.domain.exception;

public class AuthenticationException extends  RuntimeException {

    public AuthenticationException(String message){
        super(message);
    }
}
