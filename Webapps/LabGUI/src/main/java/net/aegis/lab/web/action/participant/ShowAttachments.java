package net.aegis.lab.web.action.participant;

import java.util.List;

import net.aegis.lab.dao.pojo.Attachment;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Scenarioexecution;
import net.aegis.lab.manager.AttachmentManager;
import net.aegis.lab.manager.ParticipantCaseExecutionManager;
import net.aegis.lab.web.action.BaseAction;

/**
 *
 * @author ram.ghattu
 */
public class ShowAttachments extends BaseAction {

    private static final long serialVersionUID = 1L;
    private List<Attachment> attachments;
    private String caseName;
    private String executionUniqueId;
    private Integer setExecutionId;


    @Override
    public String execute() throws Exception {

        executionUniqueId = this.getRequest().getParameter("executionUniqueId");
        if (executionUniqueId!=null && !"".equals(executionUniqueId)) {
            log.info("ShowAttachments.execute() - INFO: "+executionUniqueId);

            try {
                setExecutionId = new Integer(this.getRequest().getParameter("setExecutionId"));
                this.getRequest().setAttribute("executionUniqueId", executionUniqueId);
                this.getRequest().setAttribute("setExecutionId", setExecutionId);

                attachments = AttachmentManager.getInstance().retrieveAttachmentsBySetExecutionId(setExecutionId);
                //this.getRequest().setAttribute("enableDeleteButton","no");
            } catch (Throwable e) {
                log.error("SSE Exception", e);
                return this.processException(e);
            }
        } else {
            log.info("ShowAttachments.execute() - INFO: "+caseName);
            String caseExecId = this.getRequest().getParameter("selectedCaseExecutionId");
            this.setCaseName(this.getRequest().getParameter("selectedCaseName"));
            log.info("ShowAttachments.execute() - INFO session id "+this.getSession().getAttribute("caseExecId"));
            // get the selected case execution id and set it to session
            if(caseExecId != null) {
                this.getSession().setAttribute("caseExecId", caseExecId);
            } else {
                caseExecId = (String)this.getSession().getAttribute("caseExecId");
            }

            // The following 'if' block is *ONLY* to set a session-level value for
            // *scenario id* so that this *scenario id* is displayed for every invocation
            // of the corresponding ShowAttachments.jsp -- whether from Active Test Results
            // or from History Test Results.
            if(null != caseExecId) {
                Caseexecution objTmpCaseExecution = null;
                Scenarioexecution objTmpScenarioExecution = null;

                objTmpCaseExecution = ParticipantCaseExecutionManager.getInstance().getCaseExecutionById(Integer.parseInt(caseExecId));
                if (null != objTmpCaseExecution) {
                    objTmpScenarioExecution = objTmpCaseExecution.getScenarioexecution();
                }
                if (null != objTmpScenarioExecution) {
                    this.getRequest().getSession().setAttribute("scenarioId", objTmpScenarioExecution.getScenario().getScenarioId());
                }
            }

            log.info("ShowAttachments.execute() - case exec id " + caseExecId);
            log.info("ShowAttachments.execute() - case name " + this.getCaseName());
            try {
                attachments = AttachmentManager.getInstance().retrieveAttachments(Integer.parseInt(caseExecId));
                log.info("ShowAttachments.execute() - attachments " + attachments);

                if (attachments != null) {
                    for (Attachment attch : attachments) {
                        log.info("ShowAttachments.execute() - file name " + attch.getFilename());
                    }
                }
            } catch (Exception e) {
                log.error("Exception", e);
                return this.processException(e);
            }
        }
        return SUCCESS;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * @return the caseName
     */
    public String getCaseName() {
        return caseName;
    }

    /**
     * @param caseName the caseName to set
     */
    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
}
