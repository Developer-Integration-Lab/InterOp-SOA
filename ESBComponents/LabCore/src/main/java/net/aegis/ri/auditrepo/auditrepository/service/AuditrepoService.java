package net.aegis.ri.auditrepo.auditrepository.service;

import java.sql.Timestamp;
import java.util.List;

import net.aegis.lab.exception.ServiceException;
import net.aegis.ri.auditrepo.dao.pojo.Auditrepository;

/**
 *
 * @author Ram Ghattu
 */
public interface AuditrepoService {
    public Auditrepository findById(int id);
    public Integer saveAuditrepo(Auditrepository auditrepository);
    public void deleteAuditrepo(int id);
    public List<Auditrepository> getAuditrepoInfoByUserId(String userId);
    public List<Auditrepository> findAll(int id);
    public List<Auditrepository> findAllAuditRepo();
    public Timestamp getCurrentTimeOnRI(Integer piRI_ID) throws ServiceException;
    public void cleanAuditRepo();
    public List<Auditrepository> getAuditRepositoryByParticipantTimeRange(Timestamp endTime, Timestamp startTime, String communityId) throws ServiceException;
    public List<Auditrepository> getAuditRepositoryByTimeRange(Timestamp endTime, Timestamp startTime) throws ServiceException;
    public List<Auditrepository> getAuditRepoByCandTimeRangeAndMsgType(String communityId,Timestamp startTime, String msgType) throws ServiceException;

}
