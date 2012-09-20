package net.aegis.ri.patientcorrelationdb.service;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import net.aegis.lab.util.ContextUtil;
import net.aegis.ri.docrepository.dao.pojo.Document;
import net.aegis.ri.patientcorrelationdb.dao.pojo.Correlatedidentifiers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Abhay.Bakshi
 */

/*
    mysql> desc correlatedidentifiers;
    +-------------------------------+------------------+------+-----+---------+----------------+
    | Field                         | Type             | Null | Key | Default | Extra          |
    +-------------------------------+------------------+------+-----+---------+----------------+
    | correlationId                 | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
    | PatientAssigningAuthorityId   | varchar(45)      | NO   |     | NULL    |                |
    | PatientId                     | varchar(45)      | NO   |     | NULL    |                |
    | CorrelatedPatientAssignAuthId | varchar(45)      | NO   |     | NULL    |                |
    | CorrelatedPatientId           | varchar(45)      | NO   |     | NULL    |                |
    | CorrelationExpirationDate     | datetime         | YES  |     | NULL    |                |
    +-------------------------------+------------------+------+-----+---------+----------------+
    6 rows in set (0.00 sec)
 */

public class PatientCorrelationServiceImplTest {

    private static final Log log = LogFactory.getLog(PatientCorrelationServiceImplTest.class);
    private PatientCorrelationService patientCorrelationService;

    public PatientCorrelationServiceImplTest() {
        log.info("PatientCorrelationServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("PatientCorrelationServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("PatientCorrelationServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("PatientCorrelationServiceImplTest.setUp() - INFO");
        patientCorrelationService = (PatientCorrelationService)ContextUtil.getRIPatientCorrelationDbAppContext().getBean("correlationService");
    }

    @After
    public void tearDown() {
        log.info("PatientCorrelationServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of findAllCorrelation method, of class PatientCorrelationServiceImpl.
     */
    @Test
    public void testFindAllCorrelation() {

        log.info("PatientCorrelationServiceImplTest.testFindAllCorrelation(): start  - INFO");
        List<Correlatedidentifiers> testCorrelatedidentifiers = null;


        testCorrelatedidentifiers = patientCorrelationService.findAllCorrelation();
        assertNotNull(testCorrelatedidentifiers);

        for(Correlatedidentifiers correlatedidentifier: testCorrelatedidentifiers) {
            log.info("PatientCorrelationServiceImplTest.testFindAllCorrelation(): Correlated Identifier Patient ID --->>" +
                    correlatedidentifier.getPatientId() + " and Correlated Patient ID --->>" +
                    correlatedidentifier.getCorrelatedPatientId() + " and Correlation Expiration Date --->>" +
                    correlatedidentifier.getCorrelationExpirationDate());
        }
    }

    /**
     * Test of cleanCorrelationIdentifiers method, of class PatientCorrelationServiceImpl.
     */
//    @Test
    public void testCleanCorrelationIdentifiers() {

        //
        // NOT TO IMPLEMENT UNLESS MOCK OBJECTS INTRODUCED
        //
        
        /*
        System.out.println("cleanCorrelationIdentifiers");
        PatientCorrelationServiceImpl instance = new PatientCorrelationServiceImpl();
        instance.cleanCorrelationIdentifiers();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        */
    }

}