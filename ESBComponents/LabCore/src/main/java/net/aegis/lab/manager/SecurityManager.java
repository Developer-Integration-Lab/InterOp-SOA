package net.aegis.lab.manager;

import java.util.Date;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.user.service.UserService;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.util.Format;
import net.aegis.lab.util.LabConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SecurityManager {

    private static final Log log = LogFactory.getLog(SecurityManager.class);

    private static SecurityManager instance;

    private UserService userService = (UserService) ContextUtil.getLabContext().getBean("userService");

    private SecurityManager() {
    }

    /**
     * @return SecurityManager
     */
    public static SecurityManager getInstance() {
        if (instance == null) {
            instance = new SecurityManager();
        }
        return instance;
    }

    /**
     * Authenticate user/password
     * @param user
     * @param password
     * @return User found user information
     * @throws ServiceException
     */
    public User authenticate(String username, String password) throws ServiceException {
        log.info("SecurityManager.authenticate() - username [" + username + "] - START - " + Format.getFormattedDate("yyyy-MM-dd HH:mm:ss", new Date()));

        User user = null;

        try {
            user = userService.findByUsername(username);
            if (user != null) {

                Integer iInvalidAttempts = 0;

                // Compare password; if wrong, set invalid flag
                if (!user.getPassword().equals(password)) {

                    String userStatus = user.getStatus();

                    if((userStatus != null) && (!userStatus.equals(""))) {

                        if (!(userStatus.equals(User.STATUS_LOCKED)) &&  !(userStatus.equals(User.STATUS_TERMINATED))) {
                            //int userStatusNum = 1;
                            //Integer iInvalidAttempts = 1;
                            if(userStatus.equals(User.STATUS_ACTIVE)){
                                log.info("User status: " + userStatus);
                                iInvalidAttempts = user.getInvalidAttempts();
                                iInvalidAttempts++;
                                user.setInvalidAttempts(iInvalidAttempts);
                                userService.update(user);
                            } else {
                                // *********************************************
                                // NOT SURE IF THIS ELSE BLOCK IS EVER EXECUTED !!
                                // ANYWAY, NO HARM IN KEEPING IT.  LESS TIME
                                // TO TEST ALL THIS PIECE OF CODE NOW.
                                // *********************************************
                                
                                //if(isNumeric(userStatus)){
                                //    userStatusNum = Integer.parseInt(userStatus) + 1;
                                //}
                                iInvalidAttempts = user.getInvalidAttempts();
                                iInvalidAttempts++;
                                user.setInvalidAttempts(iInvalidAttempts);
                                userService.update(user);
                            }

                            //if(userStatusNum > 4){
                            if (user.getInvalidAttempts() > LabConstants.MAX_ALLOWED_INVALID_LOGIN_ATTEMPTS) {
                                user.setStatus(User.STATUS_LOCKED);
                                userService.update(user);
                                userStatus = user.getStatus();
                                //user.setInvalidAttempts(iInvalidAttempts);
                            } else {
                                //userStatus = String.valueOf(userStatusNum);
                                //user.setStatus(userStatus);
                                user.setInvalidAttempts(iInvalidAttempts);
                            }

                            //log.info("User status - invalid attempt2: " + userStatus);
                            log.info("SecurityManager.authenticate() - iInvalidAttempts = " + user.getInvalidAttempts());

                            userService.update(user);
                        }

                    }

                    if (!(userStatus.equals(User.STATUS_LOCKED)) &&  !(userStatus.equals(User.STATUS_TERMINATED))) {
                        user.setInvalidLoginAttempt(true);
                        log.warn("Failure authenticating user [" + username + "].  (errors.login.failed.invalid.password)");
                        // Set ServiceException with error code and throw
                        throw new ServiceException("Failure authenticating user [" + username + "].", "errors.login.failed.invalid.password");
                    }
                }
                else {
                    user.setInvalidLoginAttempt(false);
                    if ((null != user.getStatus()) && (!(User.STATUS_LOCKED.equalsIgnoreCase(user.getStatus())))) {
                        user.setInvalidAttempts(0);
                    }
                    userService.update(user);
                }

                // Check user status; if not ACTIVE, set ServiceException with error code and throw
                if (!(user.getStatus().equals(User.STATUS_LOCKED)) &&  !(user.getStatus().equals(User.STATUS_TERMINATED))) {
                    user.setStatus(User.STATUS_ACTIVE);
                    user.setInvalidAttempts(0);
                    userService.update(user);

                    // Check user roles
                    if (user.getUserroles() != null && user.getUserroles().size() > 0) {
                        log.info("SecurityManager.authenticate() - User Roles found! 1st one is " + user.getUserroles().get(0).getRole().getRolename());
                    }
                }
                else if (user.getStatus().equals(User.STATUS_LOCKED)) {
                    log.warn("Failure authenticating user [" + username + "].  (errors.login.failed.status.locked)");
                    throw new ServiceException("Failure authenticating user [" + username + "].", "errors.login.failed.status.locked");
                }
                else if (user.getStatus().equals(User.STATUS_TERMINATED)) {
                    log.warn("Failure authenticating user [" + username + "].  (errors.login.failed.status.terminated)");
                    throw new ServiceException("Failure authenticating user [" + username + "].", "errors.login.failed.status.terminated");
                }
                else {
                    // This should never happen...
                    log.warn("Failure authenticating user [" + username + "].  (errors.login.failed.status.unknown)");
                    throw new ServiceException("Failure authenticating user [" + username + "].", "errors.login.failed.status.unknown");
                }
            }
        }
        catch (ServiceException se) {
            log.error(se.getMessage() + " " + se.getErrorCode());
            se.setLogged();
            throw se;
        }
        catch (Exception e) {
            ServiceException se = new ServiceException("Failure authenticating user [" + username + "].", "errors.login.failed.general", e);
            log.error("Failure authenticating user [" + username + "].", se);
            se.setLogged();
            throw se;
        }

        log.info("SecurityManager.authenticate() - username [" + username + "] - END - " + Format.getFormattedDate("yyyy-MM-dd HH:mm:ss", new Date()));

        return user;
    }

}
