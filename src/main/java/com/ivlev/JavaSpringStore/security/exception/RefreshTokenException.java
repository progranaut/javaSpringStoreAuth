package com.ivlev.JavaSpringStore.security.exception;

import javax.security.auth.login.CredentialNotFoundException;
import java.sql.Ref;
import java.text.MessageFormat;

public class RefreshTokenException extends RuntimeException{
    public RefreshTokenException(String token, String message) {
        super(MessageFormat.format("Ошибка при попытке обновить токен: {0} : {1}", token, message));
    }

    public RefreshTokenException(String message) {
        super(message);
    }
}
