/* Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved */
package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.Questionnaireresponse;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.questionnaireresponse.service.QuestionnaireresponseService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * QuestionnaireresponseManager
 *
 * @author Venkat.Keesara
 * @since Jun 15, 2011
 * @copyright Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
public class QuestionnaireresponseManager {

    private static final Log log = LogFactory.getLog(QuestionnaireresponseManager.class);
    private QuestionnaireresponseService questionnaireresponseService = (QuestionnaireresponseService) ContextUtil.getLabContext().getBean("questionnaireresponseService");
    private static QuestionnaireresponseManager instance;

    private QuestionnaireresponseManager() {
    }

    /**
     * @return ScenariocaseParamsManager
     */
    public static QuestionnaireresponseManager getInstance() {
        if (instance == null) {
            instance = new QuestionnaireresponseManager();
        }
        return instance;
    }

    public List<Questionnaireresponse> findBySetExecutionId(Integer setId) throws ServiceException {
        log.info("QuestionnaireresponseManager.findBySetExecutionId() - INFO");
        return questionnaireresponseService.findBySetExecutionId(setId);
    }
}
