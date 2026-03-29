package app.exceptions;

import io.javalin.http.HttpStatus;

public class PropertyException extends ApiException
{
    public PropertyException(String message)
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public PropertyException(String message, Throwable cause)
    {
        super(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), message, cause);
    }
}
