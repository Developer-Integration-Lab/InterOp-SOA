/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.caseschedule.service;

import net.aegis.lab.dao.pojo.Caseschedule;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Jyoti.Devarakonda
 */
public interface CaseScheduleService {

     /*
     * Standard CRUD Methods
     */
    public Integer create(Caseschedule newInstance) throws ServiceException;

    public Caseschedule read(Integer id) throws ServiceException;

    public void update(Caseschedule transientObject) throws ServiceException;

    public void delete(Caseschedule persistentObject) throws ServiceException;

    public void deleteById(Integer id) throws ServiceException;

    /*
     * Finder methods
     */

}
