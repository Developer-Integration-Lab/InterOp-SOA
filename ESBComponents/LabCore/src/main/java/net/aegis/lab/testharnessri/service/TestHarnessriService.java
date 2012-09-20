/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.testharnessri.service;

import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Testharnessri;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Jyoti.Devarakonda
 */
public interface TestHarnessriService {

      /*
     * Standard CRUD Methods
     */
    public Integer create(Testharnessri newInstance) throws ServiceException;

    public Testharnessri read(Integer id) throws ServiceException;

    public void update(Testharnessri transientObject) throws ServiceException;

    public void delete(Testharnessri persistentObject) throws ServiceException;

    public void deleteById(Integer id) throws ServiceException;

    public Map<String,String> getCommunityIds() throws ServiceException;
    
    public Testharnessri getTestharnessByCommunityId(String communityId) throws ServiceException;
    
    public Integer getTestharnessIdByVersion(String participatingId, String version) throws ServiceException;
    
    public Map<String,String> getCommunityIdsByVersion(String version) throws ServiceException;

    public List<Testharnessri> findAll() throws ServiceException;

    /*
     * Finder methods
     */

}
