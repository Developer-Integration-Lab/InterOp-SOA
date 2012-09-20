package net.aegis.ri.ws.scenario;

import java.util.Map;
import javax.xml.ws.BindingProvider;
import net.aegis.tp.ws.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author Naresh.Jaganathan
 */
public class ServiceProcessor {

    private static final Log log = LogFactory.getLog(ServiceProcessor.class);

    public ServiceProcessor(){}

    /**
     * Correlate patients method for resopnder scenarios (RI as Initiator)
     * @param endpointURL
     * @param localHCID
     * @param remoteHCIDList1
     * @param f
     * @return
     */
    public ProcessTestCaseResponseType executeRequest(ProcessTestCaseRequestType requestType, String serviceEndpointURL) {
        ProcessTestCaseResponseType result = new ProcessTestCaseResponseType();

//        try {
            log.info("*************Entered the correlatePatient()*************");
            ProcessTestCaseService service = new ProcessTestCaseService();
            ProcessTestCasePortType port = service.getProcessTestCasePort();
            if(serviceEndpointURL != null && !serviceEndpointURL.equals("")){
                // Use Proxy Instance as BindingProvider
                BindingProvider bp = (BindingProvider) port;
                //(Optional) Configure RequestContext with endpoint's URL
                Map<String, Object> rc = bp.getRequestContext();
                rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceEndpointURL);
                //End - Dynamic proxy ********************************************************************************************
            }
           
            result = port.manageTestCaseService(requestType);
            log.info("*************Exiting the correlatePatient()*************" + result.getStatus());
            /*} catch (Exception ex) {
            throw ex;
        }*/
        return result;
    }
}