/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.auditsummary.service;

import java.util.List;
import net.aegis.lab.dao.pojo.Auditsummary;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Testharnessri;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.ContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ram.ghattu
 */
public class AuditSummaryServiceImplTest {

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(AuditSummaryServiceImplTest.class);

    private AuditSummaryService auditSummaryService;

    public AuditSummaryServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        auditSummaryService = (AuditSummaryService)ContextUtil.getLabContext().getBean("auditSummaryService");
    }

    @After
    public void tearDown() {
    }

        /**
     * Test of store method, of class AuditSummaryServiceImpl.
     */
    @Test
    public void testStore() {
        log.info("store");
        Auditsummary auditSummary = new Auditsummary();
        auditSummary.setRepositoryId(353);
        auditSummary.setUserId("Ram");
        auditSummary.setPatientId("Mastan");
        auditSummary.setActionCode("R");
        auditSummary.setEnterpriseSourceId("1.1");
        auditSummary.setEnterpriseSourceSite("HCID1");
        auditSummary.setMessageType("PRQ");
        auditSummary.setNetworkAccessPointId("10.0.3.114");
        auditSummary.setOutcomeIndicator(0);
        auditSummary.setType("PRQ");
        auditSummary.setTypeCode("Patient Discovery Request");
        auditSummary.setCreateuser("richard");
        auditSummary.setChangeduser("richard");

        // Testharness setup
        Testharnessri testHarness = new Testharnessri();
        testHarness.setTestharnessId(1);
        auditSummary.setTestharnessri(testHarness);

        // Caseresult setup
        Caseresult caseResult = new Caseresult();
        caseResult.setResultId(1);
        auditSummary.setCaseresult(caseResult);

        
        //auditSummaryService.store(auditSummary);
    }
    
    /**
     * Test of findByUserId method, of class AuditSummaryServiceImpl.
     */
    @Test
    public void testFindByUserId() throws ServiceException {
        log.info("findByUserId");
        List<Auditsummary> auditSummaryList = auditSummaryService.findByUserId("Ram");
        for(Auditsummary summary: auditSummaryList)
        {
            log.info("summary id is --->>"+summary.getSummaryId());
            log.info("summary patient is --->>"+summary.getPatientId());
        }
    }

    /**
     * Test of findBySummaryId method, of class AuditSummaryServiceImpl.
     */
    @Test
    public void testFindBySummaryId() {
        log.info("findBySummaryId");
        //AuditSummary summary = auditSummaryService.findBySummaryId(summaryId);
        //System.out.println("summary patient is is -->>"+summary.getPatientId());
    }



    /**
     * Test of delete method, of class AuditSummaryServiceImpl.
     */
    @Test
    public void testDelete() {
        log.info("delete ... dont delete yet");
        //auditSummaryService.delete(1);
    }

    /**
     * Test of findAuditLogsByPatient method, of class AuditSummaryServiceImpl.
     */
    @Test
    public void testFindAuditLogsByPatient() throws ServiceException {
        log.info("findAuditLogsByPatient");
        List<Auditsummary> auditSummaryList = auditSummaryService.findAuditLogsByPatient("Mastan");
        for(Auditsummary summary: auditSummaryList)
        {
            log.info("summary id is --->>"+summary.getSummaryId());
            log.info("summary id is --->>"+summary.getUserId());
            log.info("summary patient is --->>"+summary.getPatientId());
        }
    }
}