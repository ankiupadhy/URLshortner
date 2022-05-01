package com.urlshortner.Exception;


public class InvalidUsernameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1016746765384543957L;


	private String errorCode;
	private String errorMessage;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
    public InvalidUsernameException() {
    	super();
    }
    public InvalidUsernameException(String errorMessage) {
    	this.errorMessage=errorMessage;
    }
    public InvalidUsernameException(String errorMessage,String errorCode) {
    	this.errorCode=errorCode;
    	this.errorMessage=errorMessage;
    }
}
