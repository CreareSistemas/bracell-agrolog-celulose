package br.com.crearesistemas.service;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String key;
	private String message;
	private String[] args;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

}
