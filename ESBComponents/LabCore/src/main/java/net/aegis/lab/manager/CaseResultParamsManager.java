package net.aegis.lab.manager;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.caseresult.service.CaseResultService;
import net.aegis.lab.caseresultparams.service.CaseResultParamsService;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Caseresultparameters;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Venkat Keesara
 */
public class CaseResultParamsManager {

    private static final Log log = LogFactory.getLog(CaseResultParamsManager.class);
    CaseResultParamsService caseResultParamsService = (CaseResultParamsService) ContextUtil.getLabContext().getBean("caseResultParamsService");
    CaseResultService caseResultService = (CaseResultService)ContextUtil.getLabContext().getBean("caseResultService");
    private static CaseResultParamsManager instance;

    private CaseResultParamsManager() {
    }

    /**
     * @return CaseResultParamsManager
     */
    public static CaseResultParamsManager getInstance() {
        if (instance == null) {
            instance = new CaseResultParamsManager();
        }
        return instance;
    }

    public Caseresultparameters findByCaseResultParamsId(int caseResultParamsId) throws ServiceException {
        Caseresultparameters caseResultParams = new Caseresultparameters();
        try {
            caseResultParams = caseResultParamsService.findByCaseResultParamsId(caseResultParamsId);
        } catch (ServiceException se) {
            log.error("ERROR: Failed to read findByCaseResultParamsId.", se);
            throw se;
        } catch (Exception e) {
            log.error("ERROR: - exception occured in retrieving findByCaseResultParamsId");
            log.error(e);
        }

        return caseResultParams;
    }

    public List<Caseresultparameters> findByResultId(Integer resultId) throws ServiceException {
        List<Caseresultparameters> caseResultParamsList = new ArrayList<Caseresultparameters>();
        try {
            caseResultParamsList = caseResultParamsService.findByResultId(resultId);
        } catch (ServiceException se) {
            log.error("ERROR: Failed to read findByResultId().", se);
            throw se;
        } catch (Exception e) {
            log.error("ERROR: - exception occured in retrieving findByResultId()");
            log.error(e);
        }

        return caseResultParamsList;
    }

    public void updateResultIdByResultId( Integer newResultId , Integer oldResultId )throws ServiceException
    {
         log.info("updateResultIdByResultId() Start- INFO");
         log.info("updateResultIdByResultId() ::newResultId== " + newResultId + "::::oldResultId==" + oldResultId);

        try {
            List<Caseresultparameters> caseResultParamsList = caseResultParamsService.findByResultId(oldResultId);
            Caseresult fetchedNewResult = caseResultService.findByResultId(newResultId);
            if(fetchedNewResult!=null && fetchedNewResult.getResultId()!=null)
            {
                for (Caseresultparameters transientObject : caseResultParamsList) {
                    transientObject.setCaseresult(fetchedNewResult);
                    log.info("updateResultIdByResultId() Before Update calling- INFO");
                    caseResultParamsService.update(transientObject);
                }
            }
        } catch (Exception e) {
            log.error("Error:::" + e.getMessage());
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
         log.info("updateResultIdByResultId() End- INFO");
    }
}
