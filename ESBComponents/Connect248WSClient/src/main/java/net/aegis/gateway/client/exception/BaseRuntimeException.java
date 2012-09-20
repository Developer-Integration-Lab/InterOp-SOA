/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.gateway.client.exception;

/**
 * 
 * Venkat.Keesara Dec 14, 2011
 */
public abstract class BaseRuntimeException extends RuntimeException {

	protected Throwable rootCause = null;
	/**
	 * The additional error code.
	 */
	protected String errorCode = null;

	/**
	 * Constructor for BaseRuntimeException.
	 */
	public BaseRuntimeException() {
		super();
	}

	/**
	 * Constructor for BaseRuntimeException.
	 * 
	 * @param s
	 */
	public BaseRuntimeException(String s) {
		super(s);
	}

	/**
	 * Constructor for BaseRuntimeException.
	 * 
	 * @param s
	 */
	public BaseRuntimeException(String s, Throwable rootCause) {
		super(s, rootCause);
		this.rootCause = rootCause;
	}

	/**
	 * Constructor for BaseRuntimeException.
	 * 
	 * @param s
	 */
	public BaseRuntimeException(String s, String errorCode) {
		super(s);
		this.errorCode = errorCode;
	}

	/**
	 * Constructor for BaseRuntimeException.
	 * 
	 * @param s
	 */
	public BaseRuntimeException(String s, String errorCode, Throwable cause) {
		super(s, cause);
		this.errorCode = errorCode;
		this.rootCause = cause;
	}

	/**
	 * Constructor for BaseRuntimeException.
	 * 
	 * @param s
	 */
	public BaseRuntimeException(Throwable cause) {
		super(cause);
		this.rootCause = cause;
	}

	/**
	 * Returns the rootCause.
	 * 
	 * @return Throwable
	 */
	public Throwable getRootCause() {
		return rootCause;
	}

	/**
	 * Returns the errorCode.
	 * 
	 * @return String
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the errorCode.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
