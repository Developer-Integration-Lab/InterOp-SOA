package net.aegis.lab.userrole.service;

import net.aegis.lab.dao.pojo.Userrole;
import net.aegis.lab.dao.pojo.Userrole.UserrolePK;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Abhay.Bakshi
 */
public interface UserroleService {

   /*
    * Standard CRUD Methods
    */
    public UserrolePK create(Userrole newInstance) throws ServiceException;

    public Userrole read(UserrolePK userrolePK) throws ServiceException;

    public void update(Userrole transientObject) throws ServiceException;

    public void delete(Userrole persistentObject) throws ServiceException;

    public void deleteByPK(UserrolePK userrolePK) throws ServiceException;

}
