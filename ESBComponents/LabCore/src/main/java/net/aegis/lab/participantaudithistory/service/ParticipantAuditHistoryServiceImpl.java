/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.participantaudithistory.service;

import net.aegis.lab.dao.ParticipantaudithistoryDAO;
import net.aegis.lab.dao.pojo.Participantaudithistory;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jyoti.Devarakonda
 */

@Service("candiateAuditHistService")
public class ParticipantAuditHistoryServiceImpl implements ParticipantAuditHistoryService {


    @Autowired
    ParticipantaudithistoryDAO participantAuditHistServiceDAO;


    @SuppressWarnings("unused")
    public static final Log log = LogFactory.getLog(ParticipantAuditHistoryServiceImpl.class);


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Participantaudithistory participantAuditHistory)throws ServiceException {
        log.info("create participantAuditHistory......");
        try{
           return participantAuditHistServiceDAO.create(participantAuditHistory);
        }catch(Exception e){
           throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id)throws ServiceException {
         log.info("deleteById.....participantAuditHistory.....");
        try{
          participantAuditHistServiceDAO.delete(read(id));
        }catch(Exception e){
         throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Participantaudithistory persistentObject )throws ServiceException {
       log.info("delete......participantAuditHistory");
       try{
         participantAuditHistServiceDAO.delete(persistentObject);
       }catch(Exception e){
         throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
       }
    }

    @Override
    public Participantaudithistory read(Integer id)throws ServiceException{
       log.info("read.....participantAuditHistory...");
       Participantaudithistory participantAuditHistory ;

       try{
           participantAuditHistory = participantAuditHistServiceDAO.read(id);
        }catch(Exception e){
         throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
       }

       return participantAuditHistory;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Participantaudithistory participantAuditHistory)throws ServiceException{
        log.info("update participantAuditHistory......participantAuditHistory...");
        try{
          participantAuditHistServiceDAO.update(participantAuditHistory);
        }catch(Exception e){
          throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /* @Override
     public List<Participantaudithistory> findByAuditId(Integer id)throws ServiceException{
         log.info("findByAuditId participantAuditHistory......");

         List <Participantaudithistory> participantAuditHistList = null;
         
         try{
         
           participantAuditHistList = participantAuditHistServiceDAO
         
         }catch(Exception e){
           throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
         }

     
     }*/

}
