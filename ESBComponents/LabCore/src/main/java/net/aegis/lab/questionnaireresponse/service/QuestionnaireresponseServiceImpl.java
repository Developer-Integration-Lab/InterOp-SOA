/* Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved */

package net.aegis.lab.questionnaireresponse.service;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.QuestionnaireresponseDAO;
import net.aegis.lab.dao.pojo.Questionnaireresponse;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * QuestionnaireresponseServiceImpl
 *
 * @author Venkat.Keesara
 * @since Jun 16, 2011
 * @copyright Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
@Service("questionnaireresponseService")
public class QuestionnaireresponseServiceImpl implements QuestionnaireresponseService {

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(QuestionnaireresponseServiceImpl.class);

    @Autowired
    private QuestionnaireresponseDAO questionnaireresponseDAO;

    /**
     *
     *  this method to create a new record of Questionnaireresponse in Questionnaireresponse table
     * @param serviceSet
     * @return Integer
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Questionnaireresponse questionnaireresponse) throws ServiceException {
        log.info("saving Questionnaireresponse..");
        try {
            return questionnaireresponseDAO.create(questionnaireresponse);
        } catch (Exception e) {
            log.error("create failed ",e);
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);

        }
    }

    @Override
    public List<Questionnaireresponse> findAll() throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Questionnaireresponse> findBySetExecutionId(Integer setId) throws ServiceException {
        log.info("QuestionnaireresponseServiceImpl.findBySetId() - INFO");
        List<Questionnaireresponse> results = null;
        Questionnaireresponse criteria = null;

        if (null == setId) {
            log.error("QuestionnaireresponseServiceImpl.findBySetId() - Bad parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("QuestionnaireresponseServiceImpl.findBySetId() - Bad parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        criteria = new Questionnaireresponse();
        Servicesetexecution servicesetexe = new Servicesetexecution();
        servicesetexe.setSetExecutionId(setId);
        criteria.setServicesetexecution(servicesetexe);

        try {
            if (criteria != null) {
                log.info("QuestionnaireresponseServiceImpl.findBySetId() - Search by Criteria");
                results = questionnaireresponseDAO.searchByCriteria(criteria, this.executeCriteria(criteria));
            }
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return results;
    }

     private List<Criterion> executeCriteria(Questionnaireresponse criteria) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        if (criteria != null) {

            if (criteria.getServicesetexecution() != null && criteria.getServicesetexecution().getSetExecutionId() != null) {
                criterionList.add(Restrictions.eq("servicesetexecution.setExecutionId", criteria.getServicesetexecution().getSetExecutionId()));
            }
            if (criteria.getAnswer() != null) {
                criterionList.add(Restrictions.eq("answer", criteria.getAnswer()));
            }
        }
        return criterionList;
    }
}
