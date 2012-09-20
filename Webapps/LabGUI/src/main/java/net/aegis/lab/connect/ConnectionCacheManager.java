package net.aegis.lab.connect;

import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;
import gov.hhs.fha.nhinc.connectmgr.data.CMBusinessEntity;
import gov.hhs.fha.nhinc.connectmgr.data.CMBusinessService;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.manager.SecurityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * CONNECT Connection Cache Manager - provides access to CONNECT's
 * <code>ConnectionManagerCache</code> class
 *
 * @author richard.ettema,jyoti.devarakonda
 */
public class ConnectionCacheManager {

    private static final Log log = LogFactory.getLog(SecurityManager.class);
    private static ConnectionCacheManager instance;

    private ConnectionCacheManager() {
    }

    /**
     * @return ConnectionCacheManager
     */
    public static ConnectionCacheManager getInstance() {
        if (instance == null) {
            instance = new ConnectionCacheManager();
        }
        return instance;
    }

    /**
     * Returns the business entity information associated with the specified home community ID.
     * 
     * @param sHomeCommunityId
     * @return
     */
    public CMBusinessEntity getBusinessEntity(String sHomeCommunityId) {
        log.debug("ConnectionCacheManager.getBusinessEntity() - START");

        CMBusinessEntity oReturnEntity = null;

        try {
            if (sHomeCommunityId != null) {
                oReturnEntity = ConnectionManagerCache.getBusinessEntity(sHomeCommunityId);

                if (log.isDebugEnabled()) {
                    // Iterate over business services and log the uniform servicename
                    for (CMBusinessService service : oReturnEntity.getBusinessServices().getBusinessService()) {
                        log.debug("Found service [" + service.getUniformServiceName() + "] for HCID [" + sHomeCommunityId + "]");
                        // Check for defined binding templates.  If at least one found, log endpoint URL
                        if (service.getBindingTemplates() != null &&
                                service.getBindingTemplates().getBindingTemplate() != null &&
                                service.getBindingTemplates().getBindingTemplate().size() > 0 &&
                                service.getBindingTemplates().getBindingTemplate().get(0) != null) {
                            log.debug("--> First endpoint is [" + service.getBindingTemplates().getBindingTemplate().get(0).getEndpointURL() + "]");
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception", e);
        }

        log.debug("ConnectionCacheManager.getBusinessEntity() - END");

        return oReturnEntity;
    }
    /*
     *  Returns UDDI Service End Points by Service name
     *   Jyoti devarakonda
     */

    public List<String> getUddiServiceEndPointsByServiceName(String sHomeCommunityId, String sServiceName) throws Exception {
        List<String> serviceEndpointsList = new ArrayList<String>();
        try {
            CMBusinessEntity cmBizEntity = ConnectionManagerCache.getBusinessEntityByServiceName(sHomeCommunityId, sServiceName);

            if (cmBizEntity != null) {
                for (CMBusinessService service : cmBizEntity.getBusinessServices().getBusinessService()) {
                    log.debug("Found service [" + service.getUniformServiceName() + "] for HCID [" + sHomeCommunityId + "]");
                    // Check for defined binding templates.  If at least one found, log endpoint URL
                    if (service.getBindingTemplates() != null &&
                            service.getBindingTemplates().getBindingTemplate() != null &&
                            service.getBindingTemplates().getBindingTemplate().size() > 0 &&
                            service.getBindingTemplates().getBindingTemplate().get(0) != null) {

                        log.debug("--> First endpoint is [" + service.getBindingTemplates().getBindingTemplate().get(0).getEndpointURL() + "]");
                        serviceEndpointsList.add(service.getBindingTemplates().getBindingTemplate().get(0).getEndpointURL());

                    }
                }

            } else {
               log.info("ConnectionCacheManager.getUddiServiceEndPointsByServiceName.. No Business Entity with Service Name");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return serviceEndpointsList;
    }


    /*
     *  Returns UDDI Service End Points
     *   Jyoti devarakonda
     */
    public List<String> getUddiServiceEndPoints(String sHomeCommunityId) throws Exception {
        List<String> serviceEndpointsList = new ArrayList<String>();
        try {
            CMBusinessEntity cmBizEntity = ConnectionManagerCache.getBusinessEntity(sHomeCommunityId);

            for (CMBusinessService service : cmBizEntity.getBusinessServices().getBusinessService()) {
                log.debug("Found service [" + service.getUniformServiceName() + "] for HCID [" + sHomeCommunityId + "]");
                // Check for defined binding templates.  If at least one found, log endpoint URL
                if (service.getBindingTemplates() != null &&
                        service.getBindingTemplates().getBindingTemplate() != null &&
                        service.getBindingTemplates().getBindingTemplate().size() > 0 &&
                        service.getBindingTemplates().getBindingTemplate().get(0) != null) {

                    log.debug("--> First endpoint is [" + service.getBindingTemplates().getBindingTemplate().get(0).getEndpointURL() + "]");
                    serviceEndpointsList.add(service.getBindingTemplates().getBindingTemplate().get(0).getEndpointURL());

                }

            }

        } catch (Exception e) {
            throw new Exception(e);
        }

        return serviceEndpointsList;
    }
}
