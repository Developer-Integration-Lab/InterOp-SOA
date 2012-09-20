package net.aegis.lab.exception;

import net.aegis.labcommons.exception.LabRuntimeException;

public class ServiceException extends LabRuntimeException {

	private static final long serialVersionUID = -5968483774010141399L;

	public static final String ERROR_CODE_GLOBAL_DEFAULT = "errors.global.default";
	public static final String ERROR_CODE_DATABASE_DEFAULT = "errors.database.default";
	public static final String ERROR_MESSAGE_DEFAULT = "A service level exception has occurred.";
	public static final String ERROR_MESSAGE_UNKNOWN = "Unknown Error";
	public static final String ERROR_MESSAGE_DBUNKNOWN = "Unknown Database Error";
	public static final String ERROR_MESSAGE_UNQ_CONSTRAINT = "Unique Constraint Violated";
	public static final String ERROR_MESSAGE_INVALID_ARGUMENT = "Invalid Argument";
	public static final String ERROR_MESSAGE_SERVICE_LIMIT_REACHED = "The service is no longer available since the max limit is reached";
	public static final String ERROR_MESSAGE_NO_MATCHING_RECORDS = "There were no matching records found";
	public static final String ERROR_MESSAGE_NO_PRIVILEGES_TO_RECORDS = "No Access to the records";

	private String exceptionMessage = null;

	/**
	 * Default constructor
	 */
	public ServiceException() {
		super();
	}

	/**
	 * @param rootCause
	 * @param errorCode
	 */
	public ServiceException(Throwable rootCause, String errorCode) {
		super(ERROR_MESSAGE_DEFAULT, errorCode, rootCause);
	}

	/**
	 * @param s
	 */
	public ServiceException(String s) {
		super(s, ERROR_CODE_GLOBAL_DEFAULT);
	}

	/**
	 * @param s
	 * @param rootCause
	 */
	public ServiceException(String s, Throwable rootCause) {
		super(s, ERROR_CODE_GLOBAL_DEFAULT,  rootCause);
	}

	/**
	 * @param s
	 * @param errorCode
	 */
	public ServiceException(String s, String errorCode) {
		super(s, errorCode);
	}

	/**
	 * @param s
	 * @param errorCode
	 * @param cause
	 */
	public ServiceException(String s, String errorCode, Throwable cause) {
		super(s, errorCode, cause);
	}

	/**
	 * @param cause
	 */
	public ServiceException(Throwable cause) {
		super(ERROR_MESSAGE_DEFAULT,  ERROR_CODE_GLOBAL_DEFAULT,  cause);
	}

	/**
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * @param exceptionMessage the exceptionMessage to set
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}
