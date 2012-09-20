package net.aegis.ri.auditrepo.auditextension.service;

import java.util.List;
import net.aegis.ri.auditrepo.dao.pojo.Auditextension;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import net.aegis.lab.util.ContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Abhay.Bakshi
 */

/*
    mysql> use auditrepo;
    Database changed
    mysql> desc auditextension;
    +-----------------------+-------------+------+-----+---------+-------+
    | Field                 | Type        | Null | Key | Default | Extra |
    +-----------------------+-------------+------+-----+---------+-------+
    | id                    | bigint(20)  | NO   | MUL | NULL    |       |
    | eventOutcomeIndicator | varchar(10) | YES  |     | NULL    |       |
    | requestMessage        | longblob    | YES  |     | NULL    |       |
    | responseMessage       | longblob    | YES  |     | NULL    |       |
    +-----------------------+-------------+------+-----+---------+-------+
    4 rows in set (0.17 sec)
 */
public class AuditextensionServiceImplTest {

    private static final Log log = LogFactory.getLog(AuditextensionServiceImplTest.class);
    private AuditextensionService auditExtensionService;

    public AuditextensionServiceImplTest() {
        log.info("AuditextensionServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("AuditextensionServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("AuditextensionServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("AuditextensionServiceImplTest.setUp() - INFO");
        auditExtensionService = (AuditextensionService)ContextUtil.getRIAuditRepoAppContext().getBean("auditextensionService");
    }

    @After
    public void tearDown() {
        log.info("AuditextensionServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of findById method, of class AuditextensionServiceImpl.
     */
//    @Test
    public void testFindById() {

        log.info("AuditextensionServiceImplTest.testFindById(): start - INFO");
        Integer id = 190;                       // a random num chosen for reading from DB
        Auditextension testAuditExtension = null;

        testAuditExtension = auditExtensionService.findById(id);
        assertNotNull(testAuditExtension);
        log.info("AuditextensionServiceImplTest.testFindById(): Audit Extension Event Outcome Indicator --->>" + testAuditExtension.getEventOutcomeIndicator());
    }

    /**
     * Test of findByAuditrepositoryId method, of class AuditextensionServiceImpl.
     */
//    @Test
    public void testFindByAuditrepositoryId() {

        log.info("AuditextensionServiceImplTest.testFindByAuditrepositoryId(): start - INFO");
        Long auditrepositoryId = 54L;         // a random num chosen for reading from DB
        List<Auditextension> testAuditExtentions = null;

        testAuditExtentions = auditExtensionService.findByAuditrepositoryId(auditrepositoryId);
        assertNotNull(testAuditExtentions);

        for(Auditextension ae : testAuditExtentions) {
            log.info("AuditextensionServiceImplTest.testFindByAuditrepositoryId(): Auditextension Id: --->>" +
                    ae.getAuditextensionId() + ". Audit Extension Event Outcome Indicator --->>" +
                    ae.getEventOutcomeIndicator() + ".");
        }
    }

    /**
     * Test of findAll method, of class AuditextensionServiceImpl.
     */
    //@Test
    public void testFindAll() {
        
        /*
        System.out.println("findAll");
        AuditextensionServiceImpl instance = new AuditextensionServiceImpl();
        List expResult = null;
        List result = instance.findAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

}