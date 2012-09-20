package net.aegis.lab.user.service;

import java.util.List;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;

public interface UserService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(User newInstance) throws ServiceException;

    public User read(Integer id) throws ServiceException;

    public void update(User transientObject) throws ServiceException;

    public void delete(User persistentObject) throws ServiceException;

    public void delete(Integer id) throws ServiceException;

    /*
     * Finder methods
     */
    public User findByUsername(String username) throws ServiceException;

    public User findByUsernameAndPassword(String username, String password) throws ServiceException;

    public List<User> findAll() throws ServiceException;

    public List<User> findByRolename(String rolename) throws ServiceException;
}
