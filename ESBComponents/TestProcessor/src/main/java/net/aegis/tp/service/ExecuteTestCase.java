/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.tp.service;

import javax.jws.WebService;

import net.aegis.tp.exception.TPRuntimeException;
import net.aegis.tp.jms.provider.MsgProvider;
import net.aegis.tp.ws.ProcessTestCaseResponseType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Naresh.Jaganathan
 */
@WebService(serviceName = "processTestCaseService", portName = "processTestCasePort", endpointInterface = "net.aegis.tp.ws.ProcessTestCasePortType", targetNamespace = "http://ws.tp.aegis.net/")
public class ExecuteTestCase {
    private static final Log log = LogFactory.getLog(ExecuteTestCase.class);
    public net.aegis.tp.ws.ProcessTestCaseResponseType manageTestCaseService(net.aegis.tp.ws.ProcessTestCaseRequestType processTestCaseRequest) {
        ProcessTestCaseResponseType result = new ProcessTestCaseResponseType();
        try {
            log.info("***************Entered the manageTestCaseService***************");
            MsgProvider msgprovider = new MsgProvider();
            String status = msgprovider.processMessage(processTestCaseRequest);
            result.setStatus(status);
            log.info("***************Exiting the manageTestCaseService***************" + status);
        } catch (TPRuntimeException ex) {
           // not needed until unless if you want add any error message 
        	throw ex;
        }
        return result;
    }
}
