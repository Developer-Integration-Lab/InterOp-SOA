/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.tp.exception;

import net.aegis.labcommons.exception.LabRuntimeException;

/**
 * 
 * Venkat.Keesara Dec 14, 2011
 */
public class TPRuntimeException extends LabRuntimeException {

	private static final String ERROR_CODE_RUNTIME_DEFAULT = "TP-RUN-TIME-ERROR";
	private static final String ERROR_MESSAGE_DEFAULT = "TPRuntimeException occured";

	
	public TPRuntimeException(Throwable rootCause, String errorCode) {
		super(ERROR_MESSAGE_DEFAULT, errorCode, rootCause);
	}

	/**
	 * @param errorMessage
	 */
	public TPRuntimeException(String errorMessage) {
		super(errorMessage, ERROR_CODE_RUNTIME_DEFAULT);
	}

	/**
	 * @param rootCause
	 */
	public TPRuntimeException(String errorMessage , Throwable rootCause) {
		super(errorMessage, ERROR_CODE_RUNTIME_DEFAULT, rootCause);
	}

	/**
	 * @param errorMessage
	 * @param errorCode
	 */
	public TPRuntimeException(String errorMessage, String errorCode) {
		super(errorMessage, errorCode);
	}

	/**
	 * @param errorMessage
	 * @param errorCode
	 * @param cause
	 */
	public TPRuntimeException(String errorMessage, String errorCode, Throwable cause) {
		super(errorMessage, errorCode, cause);
	}

	/**
	 * @param cause
	 */
	public TPRuntimeException(Throwable cause) {
		super(ERROR_MESSAGE_DEFAULT, ERROR_CODE_RUNTIME_DEFAULT, cause);
	}
}
