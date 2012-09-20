package net.aegis.lab.caseresultparams.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Caseresultparameters;
import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Venkat Keesara
 */
public interface CaseResultParamsService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(Caseresultparameters newInstance) throws ServiceException;

    public boolean saveCaseResultParametersList( List<Caseresultparameters> caseresultparametersList )throws ServiceException;

    public Testcase getTestCaseByresultId(Integer resultId) throws ServiceException;

    public Caseresultparameters read(Integer id) throws ServiceException;

    public void update(Caseresultparameters transientObject) throws ServiceException;

    @Deprecated
    public void updateResultIdByResultId( Integer newResultId , Integer oldResultId )throws ServiceException;

    public void delete(Caseresultparameters persistentObject) throws ServiceException;

    public void deleteById(Integer id) throws ServiceException;

    /*
     * Finder methods
     */
    public Caseresultparameters findByCaseResultParamsId(int caseResultParamsId) throws ServiceException;

    public List<Caseresultparameters> findByResultId(Integer resultId) throws ServiceException;
}
