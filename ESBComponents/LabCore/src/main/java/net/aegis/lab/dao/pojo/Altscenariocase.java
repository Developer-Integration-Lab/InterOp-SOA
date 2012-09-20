package net.aegis.lab.dao.pojo;

import java.io.Serializable;

/**
 * <p>Pojo mapping table altscenariocase</p>
 * <p></p>
 *
 * <p>Generated at Fri Oct 15 17:02:46 EDT 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 * 
 */
public class Altscenariocase implements Serializable {

    /**
     * Attribute altscenariocaseId.
     */
    private Integer altscenariocaseId;
    /**
     * Attribute scenariocase
     */
    private Scenariocase scenariocase;
    /**
     * Attribute alternateName.
     */
    private String alternateName;
    /**
     * Attribute description.
     */
    private String description;
    /**
     * Attribute alternateCriteria.
     */
    private String alternateCriteria;
    /**
     * Attribute config.
     */
    private byte[] config;

    /**
     * @return altscenariocaseId
     */
    public Integer getAltscenariocaseId() {
        return altscenariocaseId;
    }

    /**
     * @param altscenariocaseId new value for altscenariocaseId
     */
    public void setAltscenariocaseId(Integer altscenariocaseId) {
        this.altscenariocaseId = altscenariocaseId;
    }

    /**
     * get scenariocase
     */
    public Scenariocase getScenariocase() {
        return this.scenariocase;
    }

    /**
     * set scenariocase
     */
    public void setScenariocase(Scenariocase scenariocase) {
        this.scenariocase = scenariocase;
    }

    /**
     * @return alternateName
     */
    public String getAlternateName() {
        return alternateName;
    }

    /**
     * @param alternateName new value for alternateName
     */
    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
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
     * @return alternateCriteria
     */
    public String getAlternateCriteria() {
        return alternateCriteria;
    }

    /**
     * @param alternateCriteria new value for alternateCriteria
     */
    public void setAlternateCriteria(String alternateCriteria) {
        this.alternateCriteria = alternateCriteria;
    }

    /**
     * @return config
     */
    public byte[] getConfig() {
        return config;
    }

    /**
     * @param config new value for config
     */
    public void setConfig(byte[] config) {
        this.config = config;
    }
}
