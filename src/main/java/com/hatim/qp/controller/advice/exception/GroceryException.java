package com.hatim.qp.controller.advice.exception;

public class GroceryException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6200564283461752793L;

    int errorCode;
    String message;

    public GroceryException(int errorCode, String message, Throwable cause) {
	super(message, cause);
	this.errorCode = errorCode;
    }
    
    public GroceryException(int errorCode, String message) {
	super(message);
	this.errorCode = errorCode;
    }

    public int getErrorCode() {
	return errorCode;
    }

}
