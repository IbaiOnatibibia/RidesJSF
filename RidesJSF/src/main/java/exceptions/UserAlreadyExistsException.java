package exceptions;

public class UserAlreadyExistsException extends Exception{

	public UserAlreadyExistsException() {
		super();
	}
	
	public UserAlreadyExistsException(String s) {
		super(s);
	}
}