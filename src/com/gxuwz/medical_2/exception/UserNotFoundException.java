package com.gxuwz.medical_2.exception;
/**
 * 
 * @author 演示
 *
 */
public class UserNotFoundException extends Exception {

	public UserNotFoundException(String message) {
		super(message);
		
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
