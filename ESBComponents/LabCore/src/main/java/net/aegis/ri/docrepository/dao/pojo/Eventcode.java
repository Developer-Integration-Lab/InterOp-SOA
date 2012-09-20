package net.aegis.ri.docrepository.dao.pojo;

import java.io.Serializable;


/**
 * <p>Pojo mapping table eventcode</p>
 * <p></p>
 *
 * <p>Generated at Wed Feb 10 22:27:58 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Eventcode implements Serializable {

	/**
	 * Attribute eventcodeid.
	 */
	private Integer eventcodeid;
	
	/**
	 * Attribute documentid.
	 */
	private Integer documentid;
	
	/**
	 * Attribute eventCode.
	 */
	private String eventCode;
	
	/**
	 * Attribute eventCodeScheme.
	 */
	private String eventCodeScheme;
	
	/**
	 * Attribute eventCodeDisplayName.
	 */
	private String eventCodeDisplayName;
	
	
	/**
	 * @return eventcodeid
	 */
	public Integer getEventcodeid() {
		return eventcodeid;
	}

	/**
	 * @param eventcodeid new value for eventcodeid 
	 */
	public void setEventcodeid(Integer eventcodeid) {
		this.eventcodeid = eventcodeid;
	}
	
	/**
	 * @return documentid
	 */
	public Integer getDocumentid() {
		return documentid;
	}

	/**
	 * @param documentid new value for documentid 
	 */
	public void setDocumentid(Integer documentid) {
		this.documentid = documentid;
	}
	
	/**
	 * @return eventCode
	 */
	public String getEventCode() {
		return eventCode;
	}

	/**
	 * @param eventCode new value for eventCode 
	 */
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	
	/**
	 * @return eventCodeScheme
	 */
	public String getEventCodeScheme() {
		return eventCodeScheme;
	}

	/**
	 * @param eventCodeScheme new value for eventCodeScheme 
	 */
	public void setEventCodeScheme(String eventCodeScheme) {
		this.eventCodeScheme = eventCodeScheme;
	}
	
	/**
	 * @return eventCodeDisplayName
	 */
	public String getEventCodeDisplayName() {
		return eventCodeDisplayName;
	}

	/**
	 * @param eventCodeDisplayName new value for eventCodeDisplayName 
	 */
	public void setEventCodeDisplayName(String eventCodeDisplayName) {
		this.eventCodeDisplayName = eventCodeDisplayName;
	}
	


}