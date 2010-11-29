package info.jo32.EasyQandASite.controller;

public class Signal {
	
	private int code;
	private Object message;
	private Object message2;

	public Signal(int code, Object message) {
		this.code = code;
		this.message = message;
	}
	
	public Signal(int code, Object message,Object message2) {
		this.code = code;
		this.message = message;
		this.message2 = message2;
	}
}
