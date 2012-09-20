/* Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved */

package net.aegis.lab.questionnaireresponse.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Questionnaireresponse;
import net.aegis.lab.exception.ServiceException;

/**
 * QuestionnaireresponseService
 *
 * @author Venkat.Keesara
 * @since Jun 16, 2011
 * @copyright Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
public interface QuestionnaireresponseService {

    public Integer create(Questionnaireresponse questionnaireresponse) throws ServiceException;
    public List<Questionnaireresponse> findAll() throws ServiceException;
    public List<Questionnaireresponse> findBySetExecutionId(Integer setId) throws ServiceException;
}
