package src.exception;

public class InventorySystemRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -7791960980445264872L;

	public InventorySystemRuntimeException(Throwable e) {
		super(e);
	}
	
	public InventorySystemRuntimeException(String s, Throwable e) {
		super(s,e);
	}
	
	public InventorySystemRuntimeException(String s) {
		super(s);
	}

}
