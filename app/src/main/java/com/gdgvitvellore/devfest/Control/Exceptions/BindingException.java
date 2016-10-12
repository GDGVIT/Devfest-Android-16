package com.gdgvitvellore.devfest.Control.Exceptions;

/**
 * Created by shalini on 31-08-2015.
 */
public class BindingException extends Exception {
    public String exceptionMessage;
    public String EXCEPTION_TYPE = "Binding Exception:";

    public BindingException(String detailMessage) {
        super(detailMessage);
        exceptionMessage = detailMessage;
    }

    @Override
    public String getMessage() {

        return EXCEPTION_TYPE + exceptionMessage;
    }


    public void setMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
