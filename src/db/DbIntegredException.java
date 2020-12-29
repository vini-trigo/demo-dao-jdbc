package db;

public class DbIntegredException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DbIntegredException(String msg) {
		super(msg);
	}
}
