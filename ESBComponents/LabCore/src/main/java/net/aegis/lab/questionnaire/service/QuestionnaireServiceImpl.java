/* Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved */

package net.aegis.lab.questionnaire.service;

import java.util.ArrayList;
import java.util.List;

import net.aegis.lab.dao.QuestionnaireDAO;
import net.aegis.lab.dao.pojo.Questionnaire;
import net.aegis.lab.dao.pojo.Serviceset;
import net.aegis.lab.exception.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * QuestionnaireServiceImpl
 *
 * @author Venkat.Keesara
 * @since Jun 14, 2011
 * @copyright Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
@Service("questionnaireService")
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(QuestionnaireServiceImpl.class);

    @Autowired
    private QuestionnaireDAO questionnaireDAO;

    @Override
    public List<Questionnaire> findAll() throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Questionnaire> findBySetId(Integer setId) throws ServiceException {
        log.info("QuestionnaireServiceImpl.findBySetId() - INFO");
        List<Questionnaire> results = null;
        Questionnaire criteria = null;

        if (null == setId) {
            log.error("QuestionnaireServiceImpl.findBySetId() - Bad parameter(s) passed. - ERROR");
            ServiceException se = new ServiceException("QuestionnaireServiceImpl.findBySetId() - Bad parameter(s) passed.");
            se.setLogged();
            throw se;
        }

        criteria = new Questionnaire();
        Serviceset serviceset = new Serviceset();
        serviceset.setSetId(setId);
        criteria.setServiceset(serviceset);

        try {
            if (criteria != null) {
                log.info("QuestionnaireServiceImpl.findBySetId() - Search by Criteria");
               List<Criterion> criterionList = this.executeCriteria(criteria);
               List<Order> orderList = new ArrayList<Order>();
               orderList.add(Order.asc("displayOrder"));
               results = questionnaireDAO.searchByCriteriaAndSort(criteria,criterionList,orderList );
            }
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return results;
    }

    @Override
    public List<Questionnaire> findByServiceType(String serviceType) throws ServiceException {

          throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Questionnaire> findByInitiatorInd(String initiatorInd) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Questionnaire> findByResponderInd(String responderInd) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

     private List<Criterion> executeCriteria(Questionnaire criteria) {

        List<Criterion> criterionList = new ArrayList<Criterion>();

        if (criteria != null) {

            if (criteria.getServiceset() != null && criteria.getServiceset().getSetId() != null) {
                criterionList.add(Restrictions.eq("serviceset.setId", criteria.getServiceset().getSetId()));
            }

            if (criteria.getName() != null) {
                criterionList.add(Restrictions.eq("name", criteria.getName()));
            }

            if (criteria.getServiceType() != null) {
                criterionList.add(Restrictions.eq("serviceType", criteria.getServiceType()));
            }

            if (criteria.getDescription() != null) {
                criterionList.add(Restrictions.eq("description", criteria.getDescription()));
            }

            if (criteria.getAnswer() != null) {
                criterionList.add(Restrictions.eq("answer", criteria.getAnswer()));
            }

            if (criteria.getInitiatorInd() != null) {
                criterionList.add(Restrictions.eq("initiatorInd", criteria.getInitiatorInd()));
            }

            if (criteria.getResponderInd() != null) {
                criterionList.add(Restrictions.eq("responderInd", criteria.getResponderInd()));
            }

            if (criteria.getDisplayOrder()!= null) {
                criterionList.add(Restrictions.eq("displayOrder", criteria.getDisplayOrder()));
            }

            if (criteria.getUiDisplay() != null) {
                criterionList.add(Restrictions.eq("uiDisplay", criteria.getUiDisplay()));
            }
        }
        return criterionList;
    }
}
