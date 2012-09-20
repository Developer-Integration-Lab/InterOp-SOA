package net.aegis.mv.mockdata;

public class ExpectedDocument {
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDocumentUniqueId() {
		return documentUniqueId;
	}
	public void setDocumentUniqueId(String documentUniqueId) {
		this.documentUniqueId = documentUniqueId;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getExtrinsicObjectStatus() {
		return extrinsicObjectStatus;
	}
	public void setExtrinsicObjectStatus(String extrinsicObjectStatus) {
		this.extrinsicObjectStatus = extrinsicObjectStatus;
	}
	public String getRepositoryUniqueId() {
		return repositoryUniqueId;
	}
	public void setRepositoryUniqueId(String repositoryUniqueId) {
		this.repositoryUniqueId = repositoryUniqueId;
	}
	private String id;
	private String documentUniqueId;
	private String hash;
	private String size;
	private String classCode;
	private String extrinsicObjectStatus;
	private String repositoryUniqueId;
}
