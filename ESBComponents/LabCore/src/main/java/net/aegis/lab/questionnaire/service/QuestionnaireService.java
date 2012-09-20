/* Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved */

package net.aegis.lab.questionnaire.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Questionnaire;
import net.aegis.lab.exception.ServiceException;

/**
 * QuestionnaireService
 *
 * @author Venkat.Keesara
 * @since Jun 14, 2011
 * @copyright Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
public interface QuestionnaireService {

    public List<Questionnaire> findAll() throws ServiceException;
    public List<Questionnaire> findBySetId(Integer setId) throws ServiceException;
    public List<Questionnaire> findByServiceType(String serviceType) throws ServiceException;
    public List<Questionnaire> findByInitiatorInd(String initiatorInd)throws ServiceException;
    public List<Questionnaire> findByResponderInd(String responderInd)throws ServiceException;
}
