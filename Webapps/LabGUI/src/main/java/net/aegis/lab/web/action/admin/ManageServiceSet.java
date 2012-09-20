package net.aegis.lab.web.action.admin;

import java.sql.Timestamp;

import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.manager.ServiceSetManager;
import net.aegis.lab.web.action.BaseAction;
import net.aegis.lab.web.util.BooleanMapper;

import org.apache.commons.lang.Validate;

/**
 * This class manages actions related to a service set.  It currently provides fetchServiceSet and updateServiceSet actions
 * to support editing of a service set on the UI.
 *
 * @author Tareq.Nabeel
 */
public class ManageServiceSet extends BaseAction {

    private static final long serialVersionUID = 1L;

    // Used to retrieve the requested service set via url
    private Integer id;

    // Used to store request values posted via form controls
    private Serviceset serviceSet;

    // We don't want to update changedTime and changeUser columns in database when there were no changes.
    // We'll use this to conditionally update the service set depending on whether or not there were changes on the UI.
    private boolean serviceSetModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Serviceset getServiceSet() {
        return serviceSet;
    }

    public void setServiceSet(Serviceset selectedServiceSet) {
        this.serviceSet = selectedServiceSet;
    }

    public boolean isServiceSetModified() {
        return serviceSetModified;
    }

    public void setServiceSetModified(boolean serviceSetModified) {
        this.serviceSetModified = serviceSetModified;
    }

    /**
     * This method retrieves the service set corresponding to the requested service set id
     *
     * @return Serviceset instance
     * @throws ServiceException
     */
    private Serviceset retrieveServiceSet() throws ServiceException {
        // Retrieve the service set using the setName parameter
        Validate.notNull(getId(),"Cannot retrieve Serviceset id null");
        Serviceset retrievedServiceSet = ServiceSetManager.getInstance().findServiceSetById(getId()); 
        log.info("ManageServiceSet.retrieveServiceSet() - "+ (serviceSet!=null? " found serviceset '" : " cound not find Serviceset '")+ getId() + "'");
        return retrievedServiceSet;
    }

    /**
     * This method would get called when user navigates to Service Set e.g. via a link on the service set list
     *
     * @return success if all went well; error otherwise
     */
    public String fetchServiceSet() {
        log.info("ManageServiceSet.fetchServiceSet() getServiceSet()="+getServiceSet());
        try {
            Serviceset retrievedServiceSet = retrieveServiceSet();
            setServiceSet(retrievedServiceSet);
            retrievedServiceSet.setInitiatorAllowedInd(BooleanMapper.convertYesNoToBooleanString(retrievedServiceSet.getInitiatorAllowedInd()));
            retrievedServiceSet.setResponderAllowedInd(BooleanMapper.convertYesNoToBooleanString(retrievedServiceSet.getResponderAllowedInd()));
            return SUCCESS;
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
    }

    /**
     * This method gets called when user clicks on "Save Changes"
     *
     * @return success if all went well; error otherwise
     */
    public String updateServiceSet() {
        log.info("ManageServiceSet.updateServiceSet() serviceSetModified="+isServiceSetModified()+"...");
        try {
            // Retrieve the session-managed service set from database
            Serviceset servicesetToSave = retrieveServiceSet();
            if (isServiceSetModified()) {
                // This is the serviceset request created and initialized by Struts based on request params.
                Serviceset servicesetFromRequest = getServiceSet();

                // Prepare the serviceset for update
                transferServiceSetDataFromRequest(servicesetToSave, servicesetFromRequest);

                ServiceSetManager.getInstance().updateServiceSet(servicesetToSave);

                this.addActionMessage(getText("admin.serviceset.updated.successfully"));
            }
            setServiceSet(servicesetToSave);
            // We need to convert Y/N to true/false so UI can display the values correctly
            servicesetToSave.setInitiatorAllowedInd(BooleanMapper.convertYesNoToBooleanString(servicesetToSave.getInitiatorAllowedInd()));
            servicesetToSave.setResponderAllowedInd(BooleanMapper.convertYesNoToBooleanString(servicesetToSave.getResponderAllowedInd()));
            setServiceSetModified(false);
            return SUCCESS;
        } catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
    }

    private void transferServiceSetDataFromRequest(Serviceset servicesetToSave, Serviceset servicesetFromRequest) {
        servicesetToSave.setChangedtime(new Timestamp(System.currentTimeMillis()));
        servicesetToSave.setChangeduser(getProfile().getUsername());
        servicesetToSave.setInitiatorAllowedInd(BooleanMapper.convertBooleanStringToYN(servicesetFromRequest.getInitiatorAllowedInd()));
        servicesetToSave.setResponderAllowedInd(BooleanMapper.convertBooleanStringToYN(servicesetFromRequest.getResponderAllowedInd()));
        servicesetToSave.setSetName(servicesetFromRequest.getSetName());
        servicesetToSave.setSsnHandlingInd(servicesetFromRequest.getSsnHandlingInd());
        servicesetToSave.setStatus(servicesetFromRequest.getStatus());
    }

}
