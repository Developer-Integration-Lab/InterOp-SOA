/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.validation.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.dao.pojo.Validation;
import net.aegis.lab.dto.ValidationDto;
import net.aegis.lab.exception.ServiceException;

import org.hibernate.criterion.Criterion;

/**
 *
 * @author Jyoti.Devarakonda
 */
public interface ValidationService {


     /*
     * Standard CRUD Methods
     */

    public int create(Validation validation)throws ServiceException;
    public void update(Validation validation) throws ServiceException;
    public Validation read(int id) throws ServiceException;
    public void delete(Validation persistentObject) throws ServiceException;
    public void deleteById(int Id) throws ServiceException;
    public void createValidationServiceSetRecords(List<Servicesetexecution> serviceSetExecs)throws ServiceException;
    public List<Validation> searchByCriteria(Validation criteria, List<Criterion> criterion) throws ServiceException;
    public void setValidationRecordsForReview(List<ValidationDto> toBeReviewdValidationRecs, int nhinrepId)throws ServiceException;
    public void unAssignValidationRecords(List<ValidationDto> toBeUnassignedValidationRecs, int nhinrepId) throws ServiceException;

    /**
     * Find 'REVIEW' status validation records by nhin rep id (NHIN Validator)
     */
    public List<Validation> findReviewByNhinRepId(Integer pNhinRepId) throws ServiceException;
}
