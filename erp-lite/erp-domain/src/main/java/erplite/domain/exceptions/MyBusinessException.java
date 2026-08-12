package erplite.domain.exceptions;

public class MyBusinessException extends RuntimeException {

    private static final long serialVersionUID = 7996328172000190864L;

	public MyBusinessException(String message) {
        super(message);
    }
}
