package net.aegis.lab.web.action.participant;

import java.io.File;

import net.aegis.lab.web.action.BaseAction;

/**
 * This action class is used to
 * attach documents for attach document testcase
 * 
 * @author ram.ghattu
 */
public class AttachDocument extends BaseAction {

    private static final long serialVersionUID = 1L;
    private File upload;//The actual file
    private String uploadContentType; //The content type of the file
    private String uploadFileName; //The uploaded file name
    private String fileCaption;//The caption of the file entered by user
    private String executionUniqueId;
    private int setExecutionId;



    @Override
    public String execute() throws Exception {
        String scenarioId = this.getRequest().getParameter("selectedScenarioId");
        this.getRequest().getSession().setAttribute("scenarioId", scenarioId);
        String caseExecId = this.getRequest().getParameter("selectedCaseExecutionId");
        this.getRequest().getSession().setAttribute("caseExecId", caseExecId);
        return SUCCESS;
    }

    public String getFileCaption() {
        return fileCaption;
    }

    public void setFileCaption(String fileCaption) {
        this.fileCaption = fileCaption;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getExecutionUniqueId() {
        return executionUniqueId;
    }

    public void setExecutionUniqueId(String executionUniqueId) {
        this.executionUniqueId = executionUniqueId;
    }

    public int getSetExecutionId() {
        return setExecutionId;
    }

    public void setSetExecutionId(int setExecutionId) {
        this.setExecutionId = setExecutionId;
    }




}
