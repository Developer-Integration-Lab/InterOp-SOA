package net.aegis.lab.manageusers.service;



import java.util.List;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;

/**
 * @author        : Devineni Sree Hari SAi
 * @Create Date   : May-11-2010
 *
 */
public interface ManageUsersService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(User newInstance) throws ServiceException;

   // public Applicationproperties read(Integer id) throws ServiceException;

    public void update(User transientObject) throws ServiceException;

    //public void delete(Applicationproperties persistentObject) throws ServiceException;

    //public void deleteById(Integer id) throws ServiceException;

    /*
     * Finder methods
     */
    public List<User>  findByUsername(String key) throws ServiceException;

    public List<User> findAll() throws ServiceException;

    
   

}
