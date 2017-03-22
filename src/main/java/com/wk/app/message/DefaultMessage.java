package com.wk.app.message;

/**
 * @author andrey.trotsenko
 */
public class DefaultMessage<T> {
    private ErrorDescription errorDescription;
    private T body;

    public DefaultMessage() {
    }

    public DefaultMessage(T body, ErrorDescription errorDescription) {
        this.errorDescription = errorDescription;
        this.body = body;
    }

    public ErrorDescription getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(ErrorDescription errorDescription) {
        this.errorDescription = errorDescription;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
