package net.aegis.lab.nhinrep.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.exception.ServiceException;


/**
 *
 * @author Abhay.Bakshi
 * @modified  Sree Hari Devineni
 * @dt        May-10-2010
 *  // Added  registerNhinrep()
 */
public interface NhinrepService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(Nhinrep newInstance) throws ServiceException;

    public Nhinrep read(Integer id) throws ServiceException;

    public void update(Nhinrep transientObject) throws ServiceException;

    public void delete(Nhinrep persistentObject) throws ServiceException;

    public void deleteById(Integer id) throws ServiceException;

    /*
     * Finder methods
     */
    public Nhinrep findByNhinRepId(Integer nhinrepId) throws ServiceException;

    public List<Nhinrep> findByStatus(String pstrStatus) throws ServiceException;

    public List<Nhinrep> getAllNhinReps() throws ServiceException ;


    /*
     * Business methods
     */
    public Nhinrep updateContactInformation(Integer pnhinRepId, String pName, String pContactName, String pContactPhone, String pContactEmail) throws ServiceException ;


    // Requirement: An Admin needs to be able to register a new Nhin Rep.
      public Nhinrep registerNhinrep(User pUser, Nhinrep pNhinrep, int pReprole) throws ServiceException;
}

