/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.testcase.service;

import java.util.List;

import net.aegis.lab.dao.ScenariocaseDAO;
import net.aegis.lab.dao.TestcaseDAO;
import net.aegis.lab.dao.pojo.Scenariocase;
import net.aegis.lab.dao.pojo.Testcase;
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
@Service("testCaseService")
public class TestCaseServiceImpl implements TestCaseService{


    @Autowired
    TestcaseDAO testCaseDAO ;

    @Autowired
    private ScenariocaseDAO scenarioCaseDAO;

    @SuppressWarnings("unused")

    public static final Log log = LogFactory.getLog(TestCaseServiceImpl.class);

     /*
     * Standard CRUD Methods
     */

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Testcase newInstance) throws ServiceException {
        log.info("TestCaseServiceImpl.create() - INFO");
        Integer id = null;
        try {
            id = testCaseDAO.create(newInstance);
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }
        return id;
    }


    @Override
    public Testcase read(Integer id) throws ServiceException {
        log.info("TestCaseServiceImpl.read() - INFO");
        Testcase testCase = null;
        try {
            testCase = testCaseDAO.read(id);
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }
        return testCase;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Testcase transientObject) throws ServiceException {
        log.info("TestCaseServiceImpl.update() - INFO");
        try {
            testCaseDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void updateTestCaseAndScenarioCases(Testcase testCase) throws ServiceException {
        log.info("Saving TestCase..........");
        try {
             for (Scenariocase scenarioCase : testCase.getScenariocases()) {
                scenarioCaseDAO.update(scenarioCase);
            }
            testCaseDAO.update(testCase);
        } catch (Exception e) {
            log.error("ERROR: failure updating test case: '"+testCase.getName()+"'", e);
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Testcase persistentObject) throws ServiceException {
        log.info("TestCaseServiceImpl.delete(persistentObject) - INFO");
        try {
            testCaseDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("TestCaseServiceImpl.delete(id) - INFO");
        try {
            testCaseDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }


    @Override
    public List<Testcase> findAll() throws ServiceException {
        try {
           return testCaseDAO.findAll();
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }
    }

    @Override
    public List<Testcase> findByName(String name) throws ServiceException {
        try {
           return testCaseDAO.findByName(name);
        } catch (Exception e) {
            ServiceException se = new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
            throw se;
        }
    }

}
