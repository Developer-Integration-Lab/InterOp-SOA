package net.aegis.lab.web.action.admin;

import java.util.List;

import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.manager.ServiceSetManager;
import net.aegis.lab.web.action.BaseAction;

/**
 * This class manages the actions related to service sets.  It currently provides fetchServiceSets action to support listing of service sets
 * on the UI.
 * 
 * @author Tareq.Nabeel
 */
public class ManageServiceSets extends BaseAction {

    private static final long serialVersionUID = 1L;
    private List<Serviceset> serviceSets;

    public List<Serviceset> getServiceSets() {
        return serviceSets;
    }

    public void setServiceSets(List<Serviceset> serviceSets) {
        this.serviceSets = serviceSets;
    }

    public String fetchServiceSets() throws Exception {
        log.info("ManageServiceSets.fetchServiceSets().....");
        try {
            setServiceSets(ServiceSetManager.getInstance().getServicesets());
            log.info("ManageServiceSets.fetchServiceSets() - serviceSets.size=" + serviceSets.size() + " serviceSets="+serviceSets);
            return SUCCESS;
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
    }

}
