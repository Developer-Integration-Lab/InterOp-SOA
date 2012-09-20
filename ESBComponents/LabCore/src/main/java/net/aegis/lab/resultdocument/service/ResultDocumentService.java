package net.aegis.lab.resultdocument.service;

import net.aegis.lab.dao.pojo.Resultdocument;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author richard.ettema
 */
public interface ResultDocumentService {

    public Integer create(Resultdocument resultdocument)throws ServiceException;
    public void update(Resultdocument resultdocument)throws ServiceException;
    public Resultdocument read(Integer id)throws ServiceException;
    public void delete(Resultdocument persistentObject)throws ServiceException;
    public void deleteById(Integer id)throws ServiceException;

}
