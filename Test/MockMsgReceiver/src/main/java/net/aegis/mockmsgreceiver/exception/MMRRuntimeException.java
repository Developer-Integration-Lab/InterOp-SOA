

package net.aegis.mockmsgreceiver.exception;

import net.aegis.labcommons.exception.LabRuntimeException;

public class MMRRuntimeException extends LabRuntimeException {

/**
 *
 * @author Sunil.Bhaskarla
 */

	private static final long serialVersionUID = -805468365288610326L;
	private static final String ERROR_CODE_RUNTIME_DEFAULT = "MMR-RUN-TIME-ERROR";
	private static final String ERROR_MESSAGE_DEFAULT = "MMRRuntimeException occured";

	
	public MMRRuntimeException(Throwable rootCause, String errorCode) {
		super(ERROR_MESSAGE_DEFAULT, errorCode, rootCause);
	}

	/**
	 * @param s
	 */
	public MMRRuntimeException(String s) {
		super(s, ERROR_CODE_RUNTIME_DEFAULT);
	}

	/**
	 * @param rootCause
	 */
	public MMRRuntimeException(String s , Throwable rootCause) {
		super(s, ERROR_CODE_RUNTIME_DEFAULT, rootCause);
	}

	/**
	 * @param s
	 * @param errorCode
	 */
	public MMRRuntimeException(String s, String errorCode) {
		super(s, errorCode);
	}

	/**
	 * @param s
	 * @param errorCode
	 * @param cause
	 */
	public MMRRuntimeException(String s, String errorCode, Throwable cause) {
		super(s, errorCode, cause);
	}

	/**
	 * @param cause
	 */
	public MMRRuntimeException(Throwable cause) {
		super(ERROR_MESSAGE_DEFAULT, ERROR_CODE_RUNTIME_DEFAULT, cause);
	}
	
	public MMRRuntimeException(LabRuntimeException cause) {
		super(cause.getMessage(), cause.getErrorCode(), cause);
	}
}
