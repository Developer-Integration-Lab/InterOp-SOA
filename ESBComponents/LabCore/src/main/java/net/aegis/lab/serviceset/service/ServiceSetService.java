package net.aegis.lab.serviceset.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.LabConstants.LabType;

/**
 *
 * @author Jyoti.Devarakonda
 */
public interface ServiceSetService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(Serviceset serviceSet) throws ServiceException;

    public void update(Serviceset serviceSet) throws ServiceException;

    public Serviceset read(Integer setId) throws ServiceException;

    public void delete(Serviceset persistentObject) throws ServiceException;

    public void deleteById(Integer id) throws ServiceException;

    /*
     * Finder Methods
     */
    public List<Serviceset> getServicesets() throws ServiceException;

    public List<Serviceset> findAll(LabType labType) throws ServiceException;

    public List<Serviceset> findBySetName(String setName) throws ServiceException;
}
