package io.testproject.exception;

public class CustomException {

	private String message;
	
	public CustomException() {
		
	}	

	public CustomException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CustomException [message=" + message + "]";
	}	
}
