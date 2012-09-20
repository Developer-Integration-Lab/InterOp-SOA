package net.aegis.lab.applicatioproperties.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Applicationproperties;
import net.aegis.lab.exception.ServiceException;

import org.hibernate.criterion.Criterion;

/**
 * @author        : Devineni Sree Hari SAi
 * @Create Date   : May-05-2010
 *
 */
public interface ApplicationpropertiesService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(Applicationproperties newInstance) throws ServiceException;

    //public Applicationproperties read(String id) throws ServiceException;

    public void update(Applicationproperties transientObject) throws ServiceException;

    //public void delete(Applicationproperties persistentObject) throws ServiceException;
    //public void deleteById(Integer id) throws ServiceException;

    /*
     * Finder methods
     */
    public List<Applicationproperties> findByKey(String key) throws ServiceException;

    public List<Applicationproperties> findAll() throws ServiceException;

    public Applicationproperties AppProperty(String key, String value) throws ServiceException;

    public List<Applicationproperties> searchByCriteria(Applicationproperties criteria, List<Criterion> criterion) throws ServiceException;

    public boolean remove(Applicationproperties ap) throws ServiceException;
}
