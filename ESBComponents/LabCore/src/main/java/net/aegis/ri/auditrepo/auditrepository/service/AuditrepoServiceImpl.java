package net.aegis.ri.auditrepo.auditrepository.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.util.LabConstants;
import net.aegis.ri.auditrepo.dao.AuditrepositoryDAO;
import net.aegis.ri.auditrepo.dao.pojo.Auditrepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ram Ghattu
 */
@Service("auditrepoService")
public class AuditrepoServiceImpl implements AuditrepoService {

    @Autowired
    private AuditrepositoryDAO auditrepoDao;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(AuditrepoServiceImpl.class);

    @Override
    public Auditrepository findById(int id) {
        return auditrepoDao.read(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer saveAuditrepo(Auditrepository auditrepository) {
        log.info("saveAuditrepo");
        return auditrepoDao.create(auditrepository);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteAuditrepo(int id) {
        auditrepoDao.delete(findById(id));
    }

    @Override
    public List<Auditrepository> getAuditrepoInfoByUserId(String userId) {
        List<Auditrepository> list = new ArrayList<Auditrepository>();
        if (userId == null) {
            log.error("UserId is null");
            list = null;
        } else {
            list = auditrepoDao.findByUserId(userId);
        }
        return list;
    }

    @Override
    public List<Auditrepository> findAll(int id) {
        return auditrepoDao.findAll(id);
    }

    @Override
    public List<Auditrepository> findAllAuditRepo() {
        return auditrepoDao.findAllAuditRepo();
    }

    /**
     * Purpose -    This method overcomes any issues because of time discrepancies
     *              across database servers (Test Harness RI servers).
     *
     * @param piRI_ID               Allows the user to choose which RI server to read from.
     * @return java.sql.Timestamp   Current time on the RI server specified.
     * @throws ServiceException     If any issue while reading from the DB server.
     */
    @Override
    public java.sql.Timestamp getCurrentTimeOnRI(Integer piRI_ID) throws ServiceException {

        log.info("AuditrepoServiceImpl.getCurrentTimeOnRI() - INFO");

        DriverManagerDataSource objDSRI = null;
        java.sql.Timestamp objTSToReturn = null;
        java.sql.Connection objConnection = null;
        java.sql.Statement objMySQLStatement = null;
        java.sql.ResultSet objRS = null;

        // Perform nominal parameter validity
        if (null == piRI_ID) {
            throw new ServiceException("AuditrepoServiceImpl.getCurrentTimeOnRI(): parameter is null", ServiceException.ERROR_MESSAGE_INVALID_ARGUMENT);
        }

        // Determine which data source the user would like to leverage
        if (LabConstants.RI1_ID.intValue() == piRI_ID.intValue()) {
            objDSRI = (DriverManagerDataSource) ContextUtil.getRIAuditRepoAppContext().getBean(LabConstants.DATA_SOURCE_BEAN_FOR_AUDITREPO_ON_RI_SRVR);
        }
        if (LabConstants.RI2_ID.intValue() == piRI_ID.intValue()) {
            objDSRI = (DriverManagerDataSource) ContextUtil.getRI2AuditRepoAppContext().getBean(LabConstants.DATA_SOURCE_BEAN_FOR_AUDITREPO_ON_RI_SRVR);
        }
        if (LabConstants.RI3_ID.intValue() == piRI_ID.intValue()) {
            objDSRI = (DriverManagerDataSource) ContextUtil.getRI3AuditRepoAppContext().getBean(LabConstants.DATA_SOURCE_BEAN_FOR_AUDITREPO_ON_RI_SRVR);
        }
         if (LabConstants.RI4_ID.intValue() == piRI_ID.intValue()) {
            objDSRI = (DriverManagerDataSource) ContextUtil.getRI4AuditRepoAppContext().getBean(LabConstants.DATA_SOURCE_BEAN_FOR_AUDITREPO_ON_RI_SRVR);
        }

        // Is data source bean defined ?
        if (null == objDSRI) {
            throw new ServiceException("AuditrepoServiceImpl.getCurrentTimeOnRI(): Datasource is NOT defined. Test Harness RI ID=" + piRI_ID);
        }

        // Now, let us get the current time on the specified RI server.
        try {
            objConnection = objDSRI.getConnection();    // won't be NPE here.
        } catch (java.sql.SQLException se) {
            se.printStackTrace();
            throw new ServiceException("AuditrepoServiceImpl.getCurrentTimeOnRI(): problems connecting to database: ", se.getMessage());
        }

        try {
            if (null != objConnection) {
                objMySQLStatement = objConnection.createStatement();
            }
            objMySQLStatement.execute(LabConstants.CURRENT_TIMESTAMP_ON_RI_SRVR_SQL);
            objRS = objMySQLStatement.getResultSet();
            while (objRS.next()) {   // move the cursor forward.  Old-style JDBC programming! ;)
                objTSToReturn = objRS.getTimestamp(LabConstants.CURRENT_TIMESTAMP_ALIAS);
            }
        } catch (java.sql.SQLException se) {
            se.printStackTrace();
            throw new ServiceException("AuditrepoServiceImpl.getCurrentTimeOnRI(): problems reading result set: ", se.getMessage());
        } finally {   // Clean-up still important, though old-style JDBC programming! ;)
            try {
                if (null != objRS) {
                    objRS.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != objMySQLStatement) {
                    objMySQLStatement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != objConnection) {
                    objConnection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // print the URL and the time of the database server - for logging purposes.
        log.info("AuditrepoServiceImpl.getCurrentTimeOnRI(): \tDATABASE URL=" + objDSRI.getUrl() +
                "\tDATABASE TIME=" + objTSToReturn);
        return objTSToReturn;
    }

    @Transactional
    @Override
    public void cleanAuditRepo() {
        for (Auditrepository auditrepo : findAllAuditRepo()) {
            auditrepoDao.delete(auditrepo);
        }
    }

    @Override
    public List<Auditrepository> getAuditRepositoryByParticipantTimeRange(Timestamp endTime, Timestamp startTime, String communityId) throws ServiceException {
        return auditrepoDao.findByParticipantTimeRange(startTime, endTime, communityId);
    }

    @Override
    public List<Auditrepository> getAuditRepositoryByTimeRange(Timestamp endTime, Timestamp startTime) throws ServiceException {
        return auditrepoDao.findByTimeRange(endTime, startTime);
    }

    @Override
    public List<Auditrepository> getAuditRepoByCandTimeRangeAndMsgType(String communityId, Timestamp startTime, String msgType) throws ServiceException {
        log.info("start AuditrepoServiceImpl.getAuditRepoByCandTimeRangeAndMsgType() - INFO");
        List<Auditrepository> auditRepoList = new ArrayList<Auditrepository>();
        try {
            Auditrepository auditRepo = new Auditrepository();
            List<Criterion> criterionList = new ArrayList<Criterion>();
            auditRepo.setCommunityId(communityId);
            auditRepo.setMessageType(msgType);
            criterionList.add(Restrictions.eq("communityId", communityId));
            criterionList.add(Restrictions.like("messageType", msgType, MatchMode.START));
            criterionList.add(Restrictions.ge("timestamp", startTime));
            auditRepoList = auditrepoDao.searchByCriteria(auditRepo, criterionList);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        log.info("end AuditrepoServiceImpl.getAuditRepoByCandTimeRangeAndMsgType() - INFO audit repo list size " + auditRepoList.size());
        return auditRepoList;
    }
}
