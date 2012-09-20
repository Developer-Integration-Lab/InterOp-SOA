/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.md.exception;

import net.aegis.labcommons.exception.LabRuntimeException;

/**
 * 
 * Venkat.Keesara Dec 14, 2011
 */
public class MDRuntimeException extends LabRuntimeException {

	private static final String ERROR_CODE_RUNTIME_DEFAULT = "MD-RUN-TIME-ERROR";
	private static final String ERROR_MESSAGE_DEFAULT = "MDRuntimeException occured";

	
	public MDRuntimeException(Throwable rootCause, String errorCode) {
		super(ERROR_MESSAGE_DEFAULT, errorCode, rootCause);
	}

	/**
	 * @param s
	 */
	public MDRuntimeException(String s) {
		super(s, ERROR_CODE_RUNTIME_DEFAULT);
	}

	/**
	 * @param rootCause
	 */
	public MDRuntimeException(String s , Throwable rootCause) {
		super(s, ERROR_CODE_RUNTIME_DEFAULT, rootCause);
	}

	/**
	 * @param s
	 * @param errorCode
	 */
	public MDRuntimeException(String s, String errorCode) {
		super(s, errorCode);
	}

	/**
	 * @param s
	 * @param errorCode
	 * @param cause
	 */
	public MDRuntimeException(String s, String errorCode, Throwable cause) {
		super(s, errorCode, cause);
	}

	/**
	 * @param cause
	 */
	public MDRuntimeException(Throwable cause) {
		super(ERROR_MESSAGE_DEFAULT, ERROR_CODE_RUNTIME_DEFAULT, cause);
	}
	
	public MDRuntimeException(LabRuntimeException cause) {
		super(cause.getMessage(), cause.getErrorCode(), cause);
	}
}
