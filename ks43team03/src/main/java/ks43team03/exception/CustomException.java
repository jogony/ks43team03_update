package ks43team03.exception;

public class CustomException extends RuntimeException{
	
	
	public CustomException(ErrorMessage message) {
		super(message.getMessage());
	}
	
	public CustomException(String message) {
		super(message);
	}
}
