package com.spbsu.pattern;

/**
 * Created by Sergey Afonin on 26.03.2017.
 */
public class Message {

    private String message;
    private Boolean isValid;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getValid() {
        return isValid;
    }
}
