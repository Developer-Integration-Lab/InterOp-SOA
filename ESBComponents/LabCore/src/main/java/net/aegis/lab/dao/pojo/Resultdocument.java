package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Pojo mapping table resultdocument</p>
 * <p></p>
 *
 * <p>Generated at Thu May 06 08:54:23 EDT 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Resultdocument implements Serializable {

    /**
     * Attribute resultDocumentId.
     */
    private Integer resultDocumentId;
    /**
     * Attribute caseresult
     */
    private Caseresult caseresult;
    /**
     * Attribute documentUniqueId.
     */
    private String documentUniqueId;
    /**
     * Attribute repositoryId.
     */
    private String repositoryId;
    /**
     * Attribute communityId.
     */
    private String communityId;
    /**
     * Attribute rawData.
     */
    private byte[] rawData;

    /**
     * Attribute classCode
     */
    private String classCode;

    /**
     * documentHash
     */
    private String documentHash;

    /**
     * documentSize
     */
    private Integer documentSize;
    
    /**
     * Attribute messageType.
     */
    private String messageType;
    
    /**
	 * Attribute status.
	 */
	private String status;
	
	/**
	 * Attribute message.
	 */
	private String message;
	
	/**
	 * Attribute createdAt.
	 */
	private Timestamp createdAt;
	
	/**
	 * Attribute createdBy.
	 */
	private String createdBy;
	
	/**
	 * Attribute updatedAt.
	 */
	private Timestamp updatedAt;
	
	/**
	 * Attribute updatedBy.
	 */
	private String updatedBy;


    /**
     * @return resultDocumentId
     */
    public Integer getResultDocumentId() {
        return resultDocumentId;
    }

    /**
     * @param resultDocumentId new value for resultDocumentId
     */
    public void setResultDocumentId(Integer resultDocumentId) {
        this.resultDocumentId = resultDocumentId;
    }

    /**
     * get caseresult
     */
    public Caseresult getCaseresult() {
        return this.caseresult;
    }

    /**
     * set caseresult
     */
    public void setCaseresult(Caseresult caseresult) {
        this.caseresult = caseresult;
    }

    /**
     * @return documentUniqueId
     */
    public String getDocumentUniqueId() {
        return documentUniqueId;
    }

    /**
     * @param documentUniqueId new value for documentUniqueId
     */
    public void setDocumentUniqueId(String documentUniqueId) {
        this.documentUniqueId = documentUniqueId;
    }

    /**
     * @return repositoryId
     */
    public String getRepositoryId() {
        return repositoryId;
    }

    /**
     * @param repositoryId new value for repositoryId
     */
    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
     * @return communityId
     */
    public String getCommunityId() {
        return communityId;
    }

    /**
     * @param communityId new value for communityId
     */
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    /**
     * @return rawData
     */
    public byte[] getRawData() {
        return rawData;
    }

    /**
     * @param rawData new value for rawData
     */
    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    /**
     * Get the value of documentSize
     *
     * @return the value of documentSize
     */
    public Integer getDocumentSize() {
        return documentSize;
    }

    /**
     * Set the value of documentSize
     *
     * @param documentSize new value of documentSize
     */
    public void setDocumentSize(Integer documentSize) {
        this.documentSize = documentSize;
    }

    /**
     * Get the value of documentHash
     *
     * @return the value of documentHash
     */
    public String getDocumentHash() {
        return documentHash;
    }

    /**
     * Set the value of documentHash
     *
     * @param documentHash new value of documentHash
     */
    public void setDocumentHash(String documentHash) {
        this.documentHash = documentHash;
    }

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status new value for status 
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message new value for message 
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @return createdAt
	 */
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt new value for createdAt 
	 */
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	/**
	 * @return createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy new value for createdBy 
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	/**
	 * @return updatedAt
	 */
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt new value for updatedAt 
	 */
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	/**
	 * @return updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy new value for updatedBy 
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
    
}
