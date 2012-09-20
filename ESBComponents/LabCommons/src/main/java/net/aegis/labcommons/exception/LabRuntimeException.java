package net.aegis.labcommons.exception;

public class LabRuntimeException extends BaseRuntimeException {

	public LabRuntimeException(){
		super();
	}

	public LabRuntimeException(String errorMessage, String errorCode,
			Throwable rootCause) {
		super(errorMessage, errorCode, rootCause);
	}

	public LabRuntimeException(String errorMessage, String errorCode) {
		super(errorMessage, errorCode);
	}

}
