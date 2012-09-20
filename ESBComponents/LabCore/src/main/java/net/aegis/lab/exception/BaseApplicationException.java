package net.aegis.lab.exception;

import java.sql.SQLException;

/**
 * Parent of Application Exceptions hierarchy
 * @author richard.ettema
 *
 */
public abstract class BaseApplicationException extends Exception {

    /**
     * An optional nested exception used to provide the ability to encapsulate
     * a lower-level exception to provide more detailed context information
     * concerning the exact cause of the exception.
     */
    protected Throwable rootCause = null;
    /**
     * The additional error code.
     */
    protected String errorCode = null;
    /**
     * Logged status. Used to avoid duplicate logging
     */
    protected boolean isLogged;

    /**
     * Constructor for BaseApplicationException.
     */
    public BaseApplicationException() {
        super();
    }

    /**
     * Constructor for BaseApplicationException.
     * @param s
     */
    public BaseApplicationException(String s) {
        super(s);
    }

    /**
     * Constructor for BaseApplicationException.
     * @param s
     */
    public BaseApplicationException(String s, Throwable rootCause) {
        super(s,rootCause);
        this.rootCause = rootCause;
    }

    /**
     * Constructor for BaseApplicationException.
     * @param s
     */
    public BaseApplicationException(String s, String errorCode) {
        super(s);
        this.errorCode = errorCode;
    }

    /**
     * Constructor for BaseApplicationException.
     * @param s
     */
    public BaseApplicationException(String s, String errorCode, Throwable cause) {
        super(s,cause);
        this.errorCode = errorCode;
        this.rootCause = cause;
    }

    /**
     * Constructor for BaseApplicationException.
     * @param s
     */
    public BaseApplicationException(Throwable cause) {
        super(cause);
        this.rootCause = cause;
    }

    /**
     * Returns the rootCause.
     * @return Throwable
     */
    public Throwable getRootCause() {
        return rootCause;
    }

    /**
     * Returns the errorCode.
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

    /**
     * Examines if the throwable is a SQl Exception and casts it.
     * @return
     */
    public SQLException getSQLException() {
        if (rootCause != null && rootCause instanceof SQLException) {
            return (SQLException) rootCause;
        }
        return null;
    }
}
