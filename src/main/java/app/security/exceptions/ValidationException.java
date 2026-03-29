package app.security.exceptions;

import app.exceptions.ApiException;
import io.javalin.http.HttpStatus;

public class ValidationException extends ApiException
{
    public ValidationException(String message)
    {
        super(HttpStatus.BAD_REQUEST.getCode(), message);
    }
}
