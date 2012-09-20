package net.aegis.lab.resultdocument.service;

import net.aegis.lab.dao.ResultdocumentDAO;
import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author richard.ettema
 */
@Service("resultDocumentService")
public class ResultDocumentServiceImpl implements ResultDocumentService {


    @Autowired
    ResultdocumentDAO resultDocumentServiceDAO;


    @SuppressWarnings("unused")
    public static final Log log = LogFactory.getLog(ResultDocumentServiceImpl.class);


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Resultdocument resultdocument)throws ServiceException {
        log.info("create resultDocument");
        try{
           return resultDocumentServiceDAO.create(resultdocument);
        }catch(Exception e){
           throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id)throws ServiceException {
         log.info("deleteById resultDocument");
        try{
          resultDocumentServiceDAO.delete(read(id));
        }catch(Exception e){
         throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Resultdocument persistentObject )throws ServiceException {
       log.info("delete resultDocument");
       try{
         resultDocumentServiceDAO.delete(persistentObject);
       }catch(Exception e){
         throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
       }
    }

    @Override
    public Resultdocument read(Integer id)throws ServiceException{
       log.info("read resultDocument");
       Resultdocument resultDocument ;

       try{
           resultDocument = resultDocumentServiceDAO.read(id);
        }catch(Exception e){
         throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
       }

       return resultDocument;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Resultdocument resultdocument)throws ServiceException{
        log.info("update resultDocument");
        try{
          resultDocumentServiceDAO.update(resultdocument);
        }catch(Exception e){
          throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

}
