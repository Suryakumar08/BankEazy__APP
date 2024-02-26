package exception;

public class CustomBankException extends Exception {
	private static final long serialVersionUID = 1L;
	public static final String NULL_MESSAGE = "Null Encountered";
	public static final String EMPTY_INPUT = "Empty Input";
	public static final String GIVE_PROPER_INPUT = "Please, Provide Proper Input!";
	public static final String ERROR_OCCURRED = "ERROR OCCURRED! Please try again after sometime!";
	public static final String INVALID_PASSWORD = "Invalid password! Give strong password!!!";
	public static final String INVALID_MOBILE = "Invalid mobile number!";
	
	public CustomBankException(String message) {
		super(message);
	}
	
	public CustomBankException(String message, Throwable cause) {
		super(message, cause);
	}
}
