/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Testharnessri;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.testharnessri.service.TestHarnessriService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Jyoti.Devarakonda
 */
public class TestHarnessriManager {


    private static final Log log = LogFactory.getLog(TestHarnessriManager.class);
    private static TestHarnessriManager instance;
    private TestHarnessriService testHarnessriService = (TestHarnessriService) ContextUtil.getLabContext().getBean("testHarnessriService");

    private TestHarnessriManager() {
        // singleton
    }

    /**
     * @return TestHarnessriManager
     */
    public static TestHarnessriManager getInstance() {
        if (instance == null) {
            instance = new TestHarnessriManager();
        }
        return instance;
    }
    
    public Map<String , String> getTestHarnessriCommIds() throws ServiceException{
    	log.info("TestHarnessriManager.getTestHarnessriCommIds()");
        Map<String, String> riCommunityIds = new HashMap<String,String>();
        try {
           riCommunityIds = testHarnessriService.getCommunityIds();
            if (riCommunityIds.size()==0) {
                throw new ServiceException("ERROR: could not find TestHarnessri");
            } else {
                return riCommunityIds;
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding the testnarnessris: '");
            se.setErrorCode("");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding the testnarnessris:", e);
            ServiceException se = new ServiceException("ERROR: failure finding the testnarnessris:", e);
            se.setLogged();
            throw se;
        }
    }
    
    public Testharnessri getTestharnessByCommunityId(String communityId) throws ServiceException{
    	log.info("TestHarnessriManager.getTestharnessByCommunityId()");
        Testharnessri testharnessri = null;
        try {
        	testharnessri = testHarnessriService.getTestharnessByCommunityId(communityId);
            if (testharnessri == null) {
                throw new ServiceException("ERROR: could not find TestHarnessri");
            } else {
                return testharnessri;
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding the testnarnessri: '");
            se.setErrorCode("");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding the testnarnessri:", e);
            ServiceException se = new ServiceException("ERROR: failure finding the testnarnessri:", e);
            se.setLogged();
            throw se;
        }
    }


    public Map<String , String> getTestHarnessriCommIdsByVersion(String version) throws ServiceException{
        log.info("TestHarnessriManager.getTestHarnessriCommIds()");
        Map<String, String> riCommunityIds = new HashMap<String,String>();
        try {
           riCommunityIds = testHarnessriService.getCommunityIdsByVersion(version);
            if (riCommunityIds.size()==0) {
                throw new ServiceException("ERROR: could not find TestHarnessri");
            } else {
                return riCommunityIds;
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding the testnarnessris: '");
            se.setErrorCode("");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding the testnarnessris:", e);
            ServiceException se = new ServiceException("ERROR: failure finding the testnarnessris:", e);
            se.setLogged();
            throw se;
        }
    }
    
    public Testharnessri getTestHarnessriByIpAddress(String ipAddress) throws ServiceException{
        log.info("TestHarnessriManager.getTestHarnessriCommIds()");
        List<Testharnessri> testHarnessriList = null;
        Testharnessri testHarnessri = null;
        try {
        	testHarnessriList = testHarnessriService.findAll();
            if (testHarnessriList.size()==0) {
                throw new ServiceException("ERROR: could not find TestHarnessri");
            } else {
            	for(Testharnessri ri : testHarnessriList){
            		if(ri.getIpAddress().equalsIgnoreCase(ipAddress)){
            			testHarnessri = ri;
            		}
            	}
                return testHarnessri;
            }
        } catch (ServiceException se) {
            log.error("ERROR: failure finding the testnarnessri: '");
            se.setErrorCode("");
            se.setLogged();
            throw se;
        } catch (Exception e) {
            log.error("ERROR: failure finding the testnarnessri:", e);
            ServiceException se = new ServiceException("ERROR: failure finding the testnarnessri:", e);
            se.setLogged();
            throw se;
        }
    }

}



