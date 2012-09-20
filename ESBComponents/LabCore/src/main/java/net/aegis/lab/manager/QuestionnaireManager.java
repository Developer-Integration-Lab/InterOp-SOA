/* Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved */
package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.Questionnaire;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.questionnaire.service.QuestionnaireService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * QuestionnaireManager
 *
 * @author Venkat.Keesara
 * @since Jun 15, 2011
 * @copyright Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
public class QuestionnaireManager {

    private static final Log log = LogFactory.getLog(QuestionnaireManager.class);
    private QuestionnaireService questionnaireService = (QuestionnaireService) ContextUtil.getLabContext().getBean("questionnaireService");
    private static QuestionnaireManager instance;

    private QuestionnaireManager() {
    }

    /**
     * @return ScenariocaseParamsManager
     */
    public static QuestionnaireManager getInstance() {
        if (instance == null) {
            instance = new QuestionnaireManager();
        }
        return instance;
    }

    public List<Questionnaire> findBySetId(Integer setId) throws ServiceException {
        log.info("QuestionnaireManager.findBySetId() - INFO");
        return questionnaireService.findBySetId(setId);
    }
}
