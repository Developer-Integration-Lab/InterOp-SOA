/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.gateway.client.exception;

import net.aegis.labcommons.exception.LabRuntimeException;

/**
 * 
 * Venkat.Keesara Dec 14, 2011
 */
public class WSClient32RuntimeException extends LabRuntimeException {

	private static final String ERROR_CODE_RUNTIME_DEFAULT = "WSClient32-RUN-TIME-ERROR";
	private static final String ERROR_MESSAGE_DEFAULT = "WSClient32RuntimeException occured";


	public WSClient32RuntimeException(Throwable rootCause, String errorCode) {
		super(ERROR_MESSAGE_DEFAULT, errorCode, rootCause);
	}

	/**
	 * @param s
	 */
	public WSClient32RuntimeException(String s) {
		super(s, ERROR_CODE_RUNTIME_DEFAULT);
	}

	/**
	 * @param rootCause
	 */
	public WSClient32RuntimeException(String s,Throwable rootCause) {
		super(s, ERROR_CODE_RUNTIME_DEFAULT, rootCause);
	}

	/**
	 * @param s
	 * @param errorCode
	 */
	public WSClient32RuntimeException(String s, String errorCode) {
		super(s, errorCode);
	}

	/**
	 * @param s
	 * @param errorCode
	 * @param cause
	 */
	public WSClient32RuntimeException(String s, String errorCode, Throwable cause) {
		super(s, errorCode, cause);
	}

	/**
	 * @param cause
	 */
	public WSClient32RuntimeException(Throwable cause) {
		super(ERROR_MESSAGE_DEFAULT, ERROR_CODE_RUNTIME_DEFAULT, cause);
	}
}
