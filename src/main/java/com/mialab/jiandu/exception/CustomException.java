package com.mialab.jiandu.exception;

public class CustomException extends Exception {

	private static final long serialVersionUID = -4344322521736359013L;
	
	// 异常信息
	private int code;
	public String message;

	public CustomException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
