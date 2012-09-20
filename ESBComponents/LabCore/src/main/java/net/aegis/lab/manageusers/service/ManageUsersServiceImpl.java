package net.aegis.lab.manageusers.service;


import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.UserDAO;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author        : Devineni Sree Hari SAi
 * @Create Date   : May-05-2010
 * 
 */
@Service("manageusersService")
public class ManageUsersServiceImpl implements ManageUsersService {

    @Autowired
    private UserDAO userDAO;
  
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ManageUsersServiceImpl.class);


    /*
     * Standard CRUD Methods
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(User user) throws ServiceException {
        log.info("saving / creating Users.. TODO################################################### to be done");
        try {
            //return applicationpropertiesDAO.create(applicationproperties);
            return 1;
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }
  

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(User transientObject) throws ServiceException {
        log.info("ManageUsersServiceImpl.update() - INFO");
        try {
            userDAO.update(transientObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

  
    /*
     * Finder methods
     */
    @Override
    public List<User> findByUsername(String key) {
        List<User> userslist = null;
        log.info("ManageUsersServiceImpl.findByParticipantId() - INFO");
        userslist=userDAO.findByUsername(key);
        return userslist;
    }

    /**
     * Purpose: This method returns list of all users in ascending order of their names.
     * 
     * @return List<User>   sorted list of users satisfying the criteria
     * @throws ServiceException
     */
    @Override
    public List<User> findAll() throws ServiceException {
        User criteria = new User();
        List<User> usersList = null;
        List<Order> orderByList = null;
        log.info("ManageUsersServiceImpl.findAll() - INFO");

        orderByList = new ArrayList<Order>();
        orderByList.add(Order.asc("username"));

        //usersList = userDAO.findAll();
        usersList = userDAO.searchByCriteriaAndSort(criteria, this.executeCriteria(criteria), orderByList);

        return usersList;
    }

    /**
     * This method is to pass the criteria list to the method searchByCriteria
     * @param criteria
     * @return List<Criterion>
     */
    private List<Criterion> executeCriteria(User criteria) {
        List<Criterion> criterionList = new ArrayList<Criterion>();


        if (criteria != null) {
            if (null != criteria.getUsername()) {
                criterionList.add(Restrictions.eq("username", criteria.getUsername()));
            }

            if (null != criteria.getPassword()) {
                criterionList.add(Restrictions.eq("password", criteria.getPassword()));
            }

            if (null != criteria.getStatus()) {
                criterionList.add(Restrictions.eq("status", criteria.getStatus()));
            }

            if (null != criteria.getExpirationTime()) {
                criterionList.add(Restrictions.like("expirationTime", criteria.getExpirationTime().toString(), MatchMode.START));
            }

            if (null != criteria.getInvalidAttempts()) {
                criterionList.add(Restrictions.eq("invalidAttempts", criteria.getInvalidAttempts()));
            }

            if (null != criteria.getSecurityReminder()) {
                criterionList.add(Restrictions.eq("securityReminder", criteria.getSecurityReminder()));
            }

            if (null != criteria.getCreatetime()) {
                criterionList.add(Restrictions.like("createTime", criteria.getCreatetime().toString(), MatchMode.START));
            }

            if (null != criteria.getCreateuser()) {
                criterionList.add(Restrictions.eq("createUser", criteria.getCreateuser()));
            }

            if (null != criteria.getChangedtime()) {
                criterionList.add(Restrictions.like("changedTime", criteria.getChangedtime().toString(), MatchMode.START));
            }

            if (null != criteria.getChangeduser()) {
                criterionList.add(Restrictions.eq("changedUser", criteria.getChangeduser()));
            }
        }

        /*
        if (criteria != null) {
            // Check each property for empty value and set to null
            if (criteria.getParticipant() != null) {   // should really never be null -- it's a hack for now
                if (criteria.getParticipant().getParticipantId() != null && !criteria.getParticipant().getParticipantId().toString().equals("")) {
                    criterionList.add(Restrictions.eq("participant.participantId", criteria.getParticipant().getParticipantId()));
                }
            }

            if (criteria.getServiceset() != null) {  // should really never be null -- it's a hack for now
                if (criteria.getServiceset().getSetId() != null && !criteria.getServiceset().getSetId().toString().equals("")) {
                    criterionList.add(Restrictions.eq("serviceset.setId", criteria.getServiceset().getSetId()));
                }
            }

            if (criteria.getExecutionUniqueId() != null && !criteria.getExecutionUniqueId().equals("")) {
                criterionList.add(Restrictions.eq("executionUniqueId", criteria.getExecutionUniqueId()));
            }

            if (criteria.getInitiatorInd() != null && !criteria.getInitiatorInd().equals("")) {
                criterionList.add(Restrictions.eq("initiatorInd", criteria.getInitiatorInd()));
            }

            if (criteria.getResponderInd() != null && !criteria.getResponderInd().equals("")) {
                criterionList.add(Restrictions.eq("responderInd", criteria.getResponderInd()));
            }

            if (criteria.getSsnHandlingInd() != null && !criteria.getSsnHandlingInd().equals("")) {
                criterionList.add(Restrictions.like("ssnHandlingInd", criteria.getSsnHandlingInd(), MatchMode.START));
            }

            if (criteria.getStatus() != null && !criteria.getStatus().equals("")) {
                criterionList.add(Restrictions.eq("status", criteria.getStatus()));
            }

            if (criteria.getBeginTime() != null && !criteria.getBeginTime().toString().equals("")) {
                criterionList.add(Restrictions.like("beginTime", criteria.getBeginTime().toString(), MatchMode.START));
            }

            if (criteria.getEndTime() != null && !criteria.getEndTime().toString().equals("")) {
                criterionList.add(Restrictions.like("endTime", criteria.getEndTime().toString(), MatchMode.START));

            }
        }
        */
        return criterionList;
    }
    

    

}
