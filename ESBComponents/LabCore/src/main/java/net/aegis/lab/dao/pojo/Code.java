package net.aegis.lab.dao.pojo;

import java.io.Serializable;


/**
 * <p>Pojo mapping table code</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Code implements Serializable {

	/**
	 * Attribute codetype
	 */
	 private Codetype codetype;	

	/**
	 * Primary key
	 */
	private CodePK codePK;

	/**
	 * Attribute description.
	 */
	private String description;
	
	
	/**
	 * get codetype
	 */
	public Codetype getCodetype() {
		return this.codetype;
	}
	
	/**
	 * set codetype
	 */
	public void setCodetype(Codetype codetype) {
		this.codetype = codetype;
	}

	/**
	 * Get the primary key
	 */
	public CodePK getCodePK() {
		return this.codePK;
	}
	
	/**
	 * set the primary key
	 */
	public void setCodePK(CodePK codePK) {
		this.codePK = codePK;
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return this.codePK.getValue();
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
		  * <p>Composite primary key for table code</p>
 	  *
 	  * <p>Generated at Sat Feb 06 09:46:36 EST 2010</p>
 	  * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 	  */
	public static class CodePK implements Serializable {

		/**
		 * Attribute type
		 */
		private String type;
	
		/**
		 * Attribute value
		 */
		private String value;
	
		/**
		 * Return type
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
		 * Return value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value new value for value 
		 */
		public void setValue(String value) {
			this.value = value;
		}
	

		/**
		 * calculate hashcode
		 */
		public int hashCode()
		{
			//TODO : implement this method
			return super.hashCode();
		}

		/**
		 * equals method
		 */
		public boolean equals(Object object)
		{
			//TODO : implement this method
			return super.equals(object);
		}

	}

}