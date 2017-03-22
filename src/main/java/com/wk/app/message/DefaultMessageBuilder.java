package com.wk.app.message;

/**
 * @author andrey.trotsenko
 */
public class DefaultMessageBuilder {
    public static <T> DefaultMessage buildDefaultMessage(T body) {
        //noinspection unchecked
        return new DefaultMessage(body, null);
    }

    public static <T> DefaultMessage buildErrorDefaultMessage(String userMessage) {
        //noinspection unchecked
        return new DefaultMessage(null, new ErrorDescription(userMessage));
    }
}
