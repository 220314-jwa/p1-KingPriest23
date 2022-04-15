package Exceptions;

public class UsernameAlreadyExistsException extends Exception {
	
	public UsernameAlreadyExistsException() {
		super("The username already exists.");
	}
}
