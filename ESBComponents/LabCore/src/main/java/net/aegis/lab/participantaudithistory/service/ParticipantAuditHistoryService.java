/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.participantaudithistory.service;

import net.aegis.lab.dao.pojo.Participantaudithistory;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Jyoti.Devarakonda
 */
public interface ParticipantAuditHistoryService {


    public Integer create(Participantaudithistory participantAuditHistory)throws ServiceException;
    public void update(Participantaudithistory participantAuditHistory)throws ServiceException;
    public Participantaudithistory read(Integer id)throws ServiceException;
    public void delete(Participantaudithistory persistentObject)throws ServiceException;
    public void deleteById(Integer id)throws ServiceException;
   // public List<Participantaudithistory> findByAuditId(Integer auditId)throws ServiceException;

}
