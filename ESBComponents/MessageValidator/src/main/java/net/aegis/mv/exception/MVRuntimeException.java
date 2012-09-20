/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mv.exception;

import net.aegis.labcommons.exception.LabRuntimeException;

/**
 * 
 * Jyoti.Devarakonda Feb 5, 2011
 */
public class MVRuntimeException extends LabRuntimeException {

	private static final String ERROR_CODE_RUNTIME_DEFAULT = "MV-RUN-TIME-ERROR";
	private static final String ERROR_MESSAGE_DEFAULT = "MVRuntimeException occured";

	
	public MVRuntimeException(Throwable rootCause, String errorCode) {
		super(ERROR_MESSAGE_DEFAULT, errorCode, rootCause);
	}

	/**
	 * @param s
	 */
	public MVRuntimeException(String s) {
		super(s, ERROR_CODE_RUNTIME_DEFAULT);
	}

	/**
	 * @param rootCause
	 */
	public MVRuntimeException(String s , Throwable rootCause) {
		super(s, ERROR_CODE_RUNTIME_DEFAULT, rootCause);
	}

	/**
	 * @param s
	 * @param errorCode
	 */
	public MVRuntimeException(String s, String errorCode) {
		super(s, errorCode);
	}

	/**
	 * @param s
	 * @param errorCode
	 * @param cause
	 */
	public MVRuntimeException(String s, String errorCode, Throwable cause) {
		super(s, errorCode, cause);
	}

	/**
	 * @param cause
	 */
	public MVRuntimeException(Throwable cause) {
		super(ERROR_MESSAGE_DEFAULT, ERROR_CODE_RUNTIME_DEFAULT, cause);
	}
	
	public MVRuntimeException(LabRuntimeException cause) {
		super(cause.getMessage(), cause.getErrorCode(), cause);
	}
}
