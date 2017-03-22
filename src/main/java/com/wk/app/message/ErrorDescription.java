package com.wk.app.message;

/**
 * @author andrey.trotsenko
 */
public class ErrorDescription {
    private String userMessage;

    public ErrorDescription() {
    }

    public ErrorDescription(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
