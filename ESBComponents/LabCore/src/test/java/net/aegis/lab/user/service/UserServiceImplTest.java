/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.user.service;

import java.util.List;
import net.aegis.lab.participant.service.ParticipantService;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.ContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jyoti.Devarakonda
 */
public class UserServiceImplTest {

    private UserService userService;
    private ParticipantService participantService;

    private static final Log log = LogFactory.getLog(UserServiceImplTest.class);

    public UserServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        log.info("calling setup ... ");
        userService = (UserService)ContextUtil.getLabContext().getBean("userService");
        participantService = (ParticipantService)ContextUtil.getLabContext().getBean("participantService");
      
        log.info("calling setup ... userservice --->>"+userService);
    }

    @After
    public void tearDown() {

    }

    /**
     * Test of create method, of class UserServiceImpl.
     */
    //@Test
    public void testCreate(){
        try {
            log.info("create");
            User newInstance = new User();
            newInstance.setUsername("Jyoti");
            newInstance.setPassword("jyoti");
            newInstance.setStatus("Active");
            newInstance.setExpirationTime(null);
            newInstance.setComments(null);
            newInstance.setCreatetime(null);
            newInstance.setCreateuser("jyoti");
            newInstance.setChangedtime(null);
            newInstance.setChangeduser(null);
            Integer expResult = 1;
            Integer result = userService.create(newInstance);

            Participant participant = new Participant();

            Nhinrep nhinRep = new Nhinrep();

            participant.setParticipantName("Jyoti");
            participant.setUser(newInstance);
            participant.setCommunityId(null);
            participant.setIpAddress("");
            participant.setContactName("Sai");
            participant.setContactEmail("jyot@busy.com");
            //participant.setNhinrep(nhinRep);
            participant.setContactPhone("703-677-6815");

            participantService.create(participant);
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            // fail("The test case is a prototype.");
        } catch (ServiceException e) {

            e.printStackTrace();
        }
    }

    /**
     * Test of read method, of class UserServiceImpl.
     */
   /* @Test
    public void testRead() throws Exception {
        System.out.println("read");
        Integer id = null;
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.read(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/


    @Test
    public void testReadLabAccessFilter() throws Exception {
         // We could this to call API that filters based on LabAccessFilter
        List<User> users = userService.findByRolename("Participant");
        assertNotNull(users);
        assertTrue(users.size()>0);
        //Story 51: Integrate Conformance Tests - Core Library changes
        assertNotNull(users.get(0).getUserroles().get(0).getRole().getLabAccessFilter());
        log.info("UserroleServiceImplTest.testRead(): labAccessFilter--->>" + users.get(0).getUserroles().get(0).getRole().getLabAccessFilter());
    }
    /**
     * Test of update method, of class UserServiceImpl.
     */
   /* @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        User transientObject = null;
        UserServiceImpl instance = new UserServiceImpl();
        instance.update(transientObject);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of delete method, of class UserServiceImpl.
     */
   /* @Test
    public void testDelete_User() throws Exception {
        System.out.println("delete");
        User persistentObject = null;
        UserServiceImpl instance = new UserServiceImpl();
        instance.delete(persistentObject);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of delete method, of class UserServiceImpl.
     */
   /* @Test
    public void testDelete_Integer() throws Exception {
        System.out.println("delete");
        Integer id = null;
        UserServiceImpl instance = new UserServiceImpl();
        instance.delete(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of findByUsername method, of class UserServiceImpl.
     */
    /*@Test
    public void testFindByUsername() throws Exception {
        System.out.println("findByUsername");
        String username = "";
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.findByUsername(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of findByUsernameAndPassword method, of class UserServiceImpl.
     */
  /*  @Test
    public void testFindByUsernameAndPassword() throws Exception {
        System.out.println("findByUsernameAndPassword");
        String username = "";
        String password = "";
        UserServiceImpl instance = new UserServiceImpl();
        User expResult = null;
        User result = instance.findByUsernameAndPassword(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of findAll method, of class UserServiceImpl.
     */
   /* @Test
    public void testFindAll() throws Exception {
        System.out.println("findAll");
        UserServiceImpl instance = new UserServiceImpl();
        List expResult = null;
        List result = instance.findAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
}
