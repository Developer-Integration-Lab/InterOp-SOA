/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.testharnessri.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.TestharnessriDAO;
import net.aegis.lab.dao.pojo.Testharnessri;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jyoti.Devarakonda
 */
@Service("testHarnessriService")
public class TestHarnessriServiceImpl implements TestHarnessriService {

    @Autowired
    TestharnessriDAO testHarnessriDAO;
    public static final Log log = LogFactory.getLog(TestHarnessriServiceImpl.class);

    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Testharnessri newInstance) throws ServiceException {
        log.info("TestHarnessriServiceImpl.create() - INFO");
        Integer id = null;
        try {
            id = testHarnessriDAO.create(newInstance);
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }
        return id;
    }

    @Override
    public Testharnessri read(Integer id) throws ServiceException {
        log.info("TestHarnessriServiceImpl.read() - INFO");
        Testharnessri testHarnessri = null;
        try {
            testHarnessri = testHarnessriDAO.read(id);
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }
        return testHarnessri;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Testharnessri transientObject) throws ServiceException {
        log.info("TestHarnessriServiceImpl.update() - INFO");
        try {
            testHarnessriDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Testharnessri persistentObject) throws ServiceException {
        log.info("TestHarnessriServiceImpl.delete(persistentObject) - INFO");
        try {
            testHarnessriDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("TestHarnessriServiceImpl.delete(id) - INFO");
        try {
            testHarnessriDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public Map<String,String> getCommunityIds() throws ServiceException {
        log.info("TestHarnessriServiceImpl.getCommunityIds) - INFO");
        Testharnessri testHarnessri = new Testharnessri();
        Map<String,String> comminityIds = new HashMap<String,String>();
        try {
            List<Testharnessri> testHarnessriList = testHarnessriDAO.searchByCriteria(testHarnessri);
            for (Testharnessri testHarnessris : testHarnessriList) {
                comminityIds.put(testHarnessris.getTestharnessId().toString(), testHarnessris.getCommunityId());
            }
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return comminityIds;
    }
    
    @Override
    public Testharnessri getTestharnessByCommunityId(String communityId) throws ServiceException {
        log.info("TestHarnessriServiceImpl.getTestharnessByCommunityId) - INFO");
        Testharnessri testHarnessri = new Testharnessri();
        try {
            List<Testharnessri> testHarnessriList = testHarnessriDAO.findByCommunityId(communityId);
            for (Testharnessri ri : testHarnessriList) {
            	if(ri.getCommunityId().equals(communityId)){
            		testHarnessri = ri;
            		break;
            	}
            }
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return testHarnessri;
    }
    
    @Override
    public Map<String,String> getCommunityIdsByVersion(String version) throws ServiceException {
        log.info("TestHarnessriServiceImpl.getCommunityIds) - INFO");
        Map<String,String> comminityIds = new HashMap<String,String>();
        try {
            List<Testharnessri> testHarnessriList = testHarnessriDAO.findByVersion(version);
            for (Testharnessri testHarnessris : testHarnessriList) {
            		comminityIds.put(testHarnessris.getParticipatingId().toString(), testHarnessris.getCommunityId());
            }
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return comminityIds;
    }

    @Override
    public List<Testharnessri> findAll() throws ServiceException {
        log.info("TestHarnessriServiceImpl.findAll() - INFO");
        return testHarnessriDAO.findAll();
    }
    
    @Override
    public Integer getTestharnessIdByVersion(String participatingId, String version) throws ServiceException {
    	Integer id = 0;
    	List<Testharnessri> testharnessriList = testHarnessriDAO.findByVersion(version);
    	for(Testharnessri ri : testharnessriList){
    		if(participatingId.equals(ri.getParticipatingId().toString())){
    			id = ri.getTestharnessId();
    		}
    	}
    	return id;
    }

    /*
     * Finder methods
     */
}
