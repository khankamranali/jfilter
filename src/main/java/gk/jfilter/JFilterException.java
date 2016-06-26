package gk.jfilter;

/**
 * Runtime exception is thrown when there is json filter parsing error or Bean
 * parsing or filter execution error occurs.
 * 
 * @author Kamran Ali Khan (khankamranali@gmail.com)
 * 
 */
public class JFilterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JFilterException() {
		super();
	}

	public JFilterException(String message) {
		super(message);
	}

	public JFilterException(String message, Throwable cause) {
		super(message, cause);
	}

	public JFilterException(Throwable cause) {
		super(cause);
	}
}
