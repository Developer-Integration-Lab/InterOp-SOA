package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.util.List;

import net.aegis.lab.enums.MessageTypeEnum;

import org.apache.commons.lang.StringUtils;


/**
 * <p>Pojo mapping table questionnaire</p>
 * <p></p>
 *
 * <p>Generated at Tue Jun 14 13:43:50 EDT 2011</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Questionnaire implements Serializable {

	/**
	 * Attribute questionId.
	 */
	private Integer questionId;
	
	/**
	 * Attribute serviceset
	 */
	 private Serviceset serviceset;	

	/**
	 * Attribute name.
	 */
	private String name;
	
	/**
	 * Attribute serviceType.
	 */
	private String serviceType;
	
	/**
	 * Attribute description.
	 */
	private String description;
	
	/**
	 * Attribute value.
	 */
	private String answer;
	
	/**
	 * Attribute initiatorInd.
	 */
	private String initiatorInd;
	
	/**
	 * Attribute responderInd.
	 */
	private String responderInd;
	
	/**
	 * Attribute displayOrder.
	 */
	private Integer displayOrder;

        /**
	 * Attribute uiDisplay.
	 */
	private String uiDisplay;
	
	/**
	 * List of Questionnairecase
	 */
	private List<Questionnairecase> questionnairecases = null;

	/**
	 * List of Questionnaireexecution
	 */
	private List<Questionnaireresponse> questionnaireexecutions = null;

	
	/**
	 * @return questionId
	 */
	public Integer getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId new value for questionId 
	 */
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	
	/**
	 * get serviceset
	 */
	public Serviceset getServiceset() {
		return this.serviceset;
	}
	
	/**
	 * set serviceset
	 */
	public void setServiceset(Serviceset serviceset) {
		this.serviceset = serviceset;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name new value for name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType new value for serviceType 
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
	 * @return answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param value new value for answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	/**
	 * @return initiatorInd
	 */
	public String getInitiatorInd() {
		return initiatorInd;
	}

	/**
	 * @param initiatorInd new value for initiatorInd 
	 */
	public void setInitiatorInd(String initiatorInd) {
		this.initiatorInd = initiatorInd;
	}
	
	/**
	 * @return responderInd
	 */
	public String getResponderInd() {
		return responderInd;
	}

	/**
	 * @param responderInd new value for responderInd 
	 */
	public void setResponderInd(String responderInd) {
		this.responderInd = responderInd;
	}
	
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getUiDisplay() {
        return uiDisplay;
    }

    public void setUiDisplay(String uiDisplay) {
        this.uiDisplay = uiDisplay;
    }
	
	/**
	 * Get the list of Questionnairecase
	 */
	 public List<Questionnairecase> getQuestionnairecases() {
	 	return this.questionnairecases;
	 }
	 
	/**
	 * Set the list of Questionnairecase
	 */
	 public void setQuestionnairecases(List<Questionnairecase> questionnairecases) {
	 	this.questionnairecases = questionnairecases;
	 }
	/**
	 * Get the list of Questionnaireexecution
	 */
	 public List<Questionnaireresponse> getQuestionnaireexecutions() {
	 	return this.questionnaireexecutions;
	 }
	 
	/**
	 * Set the list of Questionnaireexecution
	 */
	 public void setQuestionnaireexecutions(List<Questionnaireresponse> questionnaireexecutions) {
	 	this.questionnaireexecutions = questionnaireexecutions;
	 }

    public boolean isCDA() {
        if (StringUtils.isNotEmpty(this.getServiceType()) && this.getServiceType().equalsIgnoreCase(MessageTypeEnum.MESSAGE_PAYLOAD.getTextId())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPD() {
        if (StringUtils.isNotEmpty(this.getServiceType()) && this.getServiceType().equalsIgnoreCase(MessageTypeEnum.PATIENT_DISCOVERY.getTextId())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isQD() {
        if (StringUtils.isNotEmpty(this.getServiceType()) && this.getServiceType().equalsIgnoreCase(MessageTypeEnum.QUERY_FOR_DOCUMENTS.getTextId())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRD() {
        if (StringUtils.isNotEmpty(this.getServiceType()) && this.getServiceType().equalsIgnoreCase(MessageTypeEnum.RETRIEVE_DOCUMENTS.getTextId())) {
            return true;
        } else {
            return false;
        }
    }
}