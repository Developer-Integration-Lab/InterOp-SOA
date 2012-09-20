package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.util.List;


/**
 * <p>Pojo mapping table codetype</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Codetype implements Serializable {

	/**
	 * Attribute type.
	 */
	private String type;
	
	/**
	 * Attribute description.
	 */
	private String description;
	
	/**
	 * List of Code
	 */
	private List<Code> codes = null;

	
	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type new value for type 
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description new value for description 
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Get the list of Code
	 */
	 public List<Code> getCodes() {
	 	return this.codes;
	 }
	 
	/**
	 * Set the list of Code
	 */
	 public void setCodes(List<Code> codes) {
	 	this.codes = codes;
	 }


}