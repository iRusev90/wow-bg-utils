package bg.wow.exception;

public abstract class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 3236626576514448364L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message, Object... args) {
		super(String.format(message, args));
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public String getError() {
		return this.getClass().getSimpleName();
	}
}