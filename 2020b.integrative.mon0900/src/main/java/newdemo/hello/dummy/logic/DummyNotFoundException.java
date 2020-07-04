package newdemo.hello.dummy.logic;

public class DummyNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -3183057250236533850L;

	public DummyNotFoundException() {
		super();
	}

	public DummyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DummyNotFoundException(String message) {
		super(message);
	}

	public DummyNotFoundException(Throwable cause) {
		super(cause);
	}

}
