/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.labcommons.exception;

/**
 * 
 * Venkat.Keesara Dec 14, 2011
 */
public abstract class BaseRuntimeException extends RuntimeException {

	/**
	 * The additional error code.
	 */
	protected String errorCode = null;
	
	
	 /**
     * Logged status. Used to avoid duplicate logging
     */
    protected boolean isLogged;
    

	/**
	 * Constructor for BaseRuntimeException.
	 */
	public BaseRuntimeException() {
		super();
	}

	/**
	 * Constructor for BaseRuntimeException.
	 * 
	 * @param errorMessage
	 */
	public BaseRuntimeException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * Constructor for BaseRuntimeException.
	 * 
	 * @param errorMessage
	 */
	public BaseRuntimeException(String errorMessage, Throwable rootCause) {
		super(errorMessage, rootCause);
	}

	/**
	 * Constructor for BaseRuntimeException.
	 * 
	 * @param errorMessage
	 */
	public BaseRuntimeException(String errorMessage, String errorCode) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	/**
	 * Constructor for BaseRuntimeException.
	 * 
	 * @param errorMessage
	 */
	public BaseRuntimeException(String errorMessage, String errorCode, Throwable cause) {
		super(errorMessage, cause);
		this.errorCode = errorCode;
	}

	/**
	 * Constructor for BaseRuntimeException.
	 * 
	 * @param errorMessage
	 */
	public BaseRuntimeException(Throwable cause) {
		super(cause);
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
	
	 /**
     * Returns the logged status.
     * @return String
     */
    public boolean isLogged() {
        return isLogged;
    }

    /**
     * Sets the logged status.
     *
     */
    public void setLogged() {
        isLogged = true;
    }

}
