package net.aegis.lab.userrole.service;

import net.aegis.lab.dao.pojo.Role;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.dao.pojo.Userrole;
import net.aegis.lab.dao.pojo.Userrole.UserrolePK;
import net.aegis.lab.user.service.UserService;
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
public class UserroleServiceImplTest {

    private static final Log log = LogFactory.getLog(UserroleServiceImplTest.class);
    private UserroleService testUserroleService;
    private UserService testUserService;

    public UserroleServiceImplTest() {
        log.info("UserroleServiceImplTest empty constructor - INFO");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        log.info("UserroleServiceImplTest.setUpClass() - INFO");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        log.info("UserroleServiceImplTest.tearDownClass() - INFO");
    }

    @Before
    public void setUp() {
        log.info("UserroleServiceImplTest.setUp() - INFO");
        testUserroleService = (UserroleService)ContextUtil.getLabContext().getBean("userroleService");
        testUserService = (UserService)ContextUtil.getLabContext().getBean("userService");
    }

    @After
    public void tearDown() {
        log.info("UserroleServiceImplTest.tearDown() - INFO");
    }

    /**
     * Test of create method, of class UserroleServiceImpl.
     */
//    @Test
    public void testCreate() throws Exception {

        log.info("UserroleServiceImplTest.testCreate(): start - INFO");

        Userrole testUserrole = null;
        UserrolePK testUserrolePK = null;
        Role testRole = null;               // part of composite key
        User testUser = null;               // part of composite key

        testUserrole = new Userrole();
        testUserrolePK = new Userrole.UserrolePK();
        
        //
        // create a new user
        //
        log.info("UserroleServiceImplTest.testCreate(): Creating a user in DB - INFO");
        Integer testUserID = null;              // create a new test user in DB
        testUser = new User();
        testUser.setUsername("testUserNameToDelete");
        testUser.setPassword("testUserPasswordToDelete");
        testUser.setStatus("L");
        testUser.setCreateuser("testCreateUser.aegis");
        testUser.setChangeduser("testChangedUser.aegis");
        testUserID = testUserService.create(testUser);
        assertNotNull(testUserID);
        log.info("UserroleServiceImplTest.testCreate(): User Info: ID --->>" + testUser.getUserId() +
                            " and Name --->>" + testUser.getUsername());

        //
        // read an existing role
        //
        testRole = new Role();
        testRole.setRoleId(5);              // 5 - Participant role

        //
        // now proceed to create a user-role using composite primary key
        //
        testUserrolePK.setRoleId(testRole.getRoleId());     // an existing role
        testUserrolePK.setUserId(testUser.getUserId());     // newly created test user
        testUserrole.setUserrolePK(testUserrolePK);
        testUserrolePK = testUserroleService.create(testUserrole);

        assertTrue(("Test user role to be created with Primary Key="+testUserrolePK), (testUserrole.getUserrolePK().getRoleId()>0));
        log.info("UserroleServiceImplTest.testCreate(): User Role : Role ID --->>" + testUserrole.getUserrolePK().getRoleId());
        log.info("UserroleServiceImplTest.testCreate(): User Role : User ID --->>" + testUserrole.getUserrolePK().getUserId());
    }

    /**
     * Test of read method, of class UserroleServiceImpl.
     */
//    @Test
    public void testRead() throws Exception {

        log.info("UserroleServiceImplTest.testRead(): start - INFO");
        Userrole testUserrole = null;
        User testUser = null;               // part of composite key
        Role testRole = null;               // part of composite key
        UserrolePK testUserrolePK = null;

        testUserrole = new Userrole();
        testUserrolePK = new Userrole.UserrolePK();

        //
        // read an existing user
        //
        log.info("UserroleServiceImplTest.testRead(): Reading a User from DB - INFO");
        Integer testUserID = 1;              // at least one user expected in DB
        testUser = testUserService.read(testUserID);
        assertNotNull(testUser);
        log.info("UserroleServiceImplTest.testRead(): User Name --->>" + testUser.getUsername());


        //
        // read an existing role
        //
        testRole = new Role();
        testRole.setRoleId(5);              // 5 - Participant role

        //
        // now proceed to read a user-role from DB
        //
        testUserrolePK.setRoleId(testRole.getRoleId());     // an existing role
        testUserrolePK.setUserId(testUser.getUserId());     // newly created test user
        testUserrole.setUserrolePK(testUserrolePK);
        testUserrole = testUserroleService.read(testUserrolePK);
        assertNotNull(testUserrole);
        log.info("UserroleServiceImplTest.testRead(): User Role: User Name --->>"
                    + testUserrole.getUser().getUsername()
                    + " and Role Name --->>"
                    + testUserrole.getRole().getRolename());
    }

    /**
     * Test of update method, of class UserroleServiceImpl.
     */
//    @Test
    public void testUpdate() throws Exception {


        log.info("UserroleServiceImplTest.testUpdate(): start - INFO");

        Userrole testUserroleToCreate = null;
        Userrole testUserroleToFind = null;
        UserrolePK testUserrolePK = null;
        Role testRole = null;               // part of composite key
        User testUser = null;               // part of composite key

        testUserroleToCreate = new Userrole();
        testUserrolePK = new Userrole.UserrolePK();

        //
        // create a new user
        //
        log.info("UserroleServiceImplTest.testUpdate(): Creating a user in DB - INFO");
        Integer testUserID = null;              // create a new test user in DB
        testUser = new User();
        testUser.setUsername("testUserNameToDelete");
        testUser.setPassword("testUserPasswordToDelete");
        testUser.setStatus("L");
        testUser.setCreateuser("testCreateUser.aegis");
        testUser.setChangeduser("testChangedUser.aegis");
        testUserID = testUserService.create(testUser);
        assertNotNull(testUserID);
        log.info("UserroleServiceImplTest.testCreate(): User Info: ID --->>" + testUser.getUserId() +
                            " and Name --->>" + testUser.getUsername());

        //
        // read an existing role
        //
        testRole = new Role();
        testRole.setRoleId(5);              // 5 - Participant role

        //
        // now proceed to create a user-role using composite primary key
        //
        testUserrolePK.setRoleId(testRole.getRoleId());     // an existing role
        testUserrolePK.setUserId(testUser.getUserId());     // newly created test user
        testUserroleToCreate.setUserrolePK(testUserrolePK);
        testUserrolePK = testUserroleService.create(testUserroleToCreate);

        assertTrue(("Test user role to be created with Primary Key="+testUserrolePK), (testUserroleToCreate.getUserrolePK().getRoleId()>0));
        log.info("UserroleServiceImplTest.testUpdate(): User Role : Role ID --->>" + testUserroleToCreate.getUserrolePK().getRoleId());
        log.info("UserroleServiceImplTest.testUpdate(): User Role : User ID --->>" + testUserroleToCreate.getUserrolePK().getUserId());


        // change a parameter in order to update the user-role created
        testRole.setRoleId(3);                              // 3 - NHIN Representative role
        testUserrolePK.setRoleId(testRole.getRoleId());
        testUserroleToCreate.setUserrolePK(testUserrolePK);
        testUserroleService.update(testUserroleToCreate);

        // find the updated user role record and display changed information
        testUserroleToFind = testUserroleService.read(testUserrolePK);
        log.info("UserroleServiceImplTest.testUpdate(): Updated Role Id --->>" + testUserroleToFind.getRole().getRoleId());
    }

    /**
     * Test of delete method, of class UserroleServiceImpl.
     */
//    @Test
    public void testDelete() throws Exception {

        log.info("UserroleServiceImplTest.testDelete(): start - INFO");

        Userrole testUserrole = null;
        UserrolePK testUserrolePK = null;
        Role testRole = null;               // part of composite key
        User testUser = null;               // part of composite key

        testUserrole = new Userrole();
        testUserrolePK = new Userrole.UserrolePK();
        
        //
        // create a new user
        //
        log.info("UserroleServiceImplTest.testDelete(): Creating a user in DB - INFO");
        Integer testUserID = null;              // create a new test user in DB
        testUser = new User();
        testUser.setUsername("testUserNameToDelete");
        testUser.setPassword("testUserPasswordToDelete");
        testUser.setStatus("L");
        testUser.setCreateuser("testCreateUser.aegis");
        testUser.setChangeduser("testChangedUser.aegis");
        testUserID = testUserService.create(testUser);
        assertNotNull(testUserID);
        log.info("UserroleServiceImplTest.testDelete(): User Info: ID --->>" + testUser.getUserId() +
                            " and Name --->>" + testUser.getUsername());

        //
        // read an existing role
        //
        testRole = new Role();
        testRole.setRoleId(5);              // 5 - Participant role

        //
        // now proceed to create a user-role using composite primary key
        //
        testUserrolePK.setRoleId(testRole.getRoleId());     // an existing role
        testUserrolePK.setUserId(testUser.getUserId());     // newly created test user
        testUserrole.setUserrolePK(testUserrolePK);
        testUserrolePK = testUserroleService.create(testUserrole);

        assertTrue(("Test user role to be created with Primary Key="+testUserrolePK), (testUserrole.getUserrolePK().getRoleId()>0));
        log.info("UserroleServiceImplTest.testDelete(): User Role : Role ID --->>" + testUserrole.getUserrolePK().getRoleId());
        log.info("UserroleServiceImplTest.testDelete(): User Role : User ID --->>" + testUserrole.getUserrolePK().getUserId());

        testUserroleService.delete(testUserrole);
        testUserService.delete(testUserID);     // also detele newly created test user
        log.info("UserroleServiceImplTest.testDelete(): end - INFO");
    }

    /**
     * Test of deleteByPK method, of class UserroleServiceImpl.
     */
//    @Test
    public void testDeleteByPK() throws Exception {

        log.info("UserroleServiceImplTest.testDeleteByPK(): start - INFO");

        Userrole testUserrole = null;
        UserrolePK testUserrolePK = null;
        Role testRole = null;               // part of composite key
        User testUser = null;               // part of composite key

        testUserrole = new Userrole();
        testUserrolePK = new Userrole.UserrolePK();
        
        //
        // create a new user
        //
        log.info("UserroleServiceImplTest.testDeleteByPK(): Creating a user in DB - INFO");
        Integer testUserID = null;              // create a new test user in DB
        testUser = new User();
        testUser.setUsername("testUserNameToDelete");
        testUser.setPassword("testUserPasswordToDelete");
        testUser.setStatus("L");
        testUser.setCreateuser("testCreateUser.aegis");
        testUser.setChangeduser("testChangedUser.aegis");
        testUserID = testUserService.create(testUser);
        assertNotNull(testUserID);
        log.info("UserroleServiceImplTest.testDeleteByPK(): User Info: ID --->>" + testUser.getUserId() +
                            " and Name --->>" + testUser.getUsername());

        //
        // read an existing role
        //
        testRole = new Role();
        testRole.setRoleId(5);              // 5 - Participant role

        //
        // now proceed to create a user-role using composite primary key
        //
        testUserrolePK.setRoleId(testRole.getRoleId());     // an existing role
        testUserrolePK.setUserId(testUser.getUserId());     // newly created test user
        testUserrole.setUserrolePK(testUserrolePK);
        testUserrolePK = testUserroleService.create(testUserrole);

        assertTrue(("Test user role to be created with Primary Key="+testUserrolePK), (testUserrole.getUserrolePK().getRoleId()>0));
        log.info("UserroleServiceImplTest.testDeleteByPK(): User Role : Role ID --->>" + testUserrole.getUserrolePK().getRoleId());
        log.info("UserroleServiceImplTest.testDeleteByPK(): User Role : User ID --->>" + testUserrole.getUserrolePK().getUserId());

        testUserroleService.deleteByPK(testUserrolePK);
        testUserService.delete(testUserID);     // also detele newly created test user
        log.info("UserroleServiceImplTest.testDeleteByPK(): end - INFO");
    }

}