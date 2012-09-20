package net.aegis.lab.web.action.participant;

import java.io.File;

import net.aegis.lab.dto.AttachmentDto;
import net.aegis.lab.manager.AttachmentManager;
import net.aegis.lab.web.action.BaseAction;

/**
 *
 * @author ram.ghattu
 */
public class AttachDocumentExecute extends BaseAction{

    private static final long serialVersionUID = 1L;
    private File upload;//The actual file
    private String uploadContentType; //The content type of the file
    private String uploadFileName; //The uploaded file name
    private String description;//The caption of the file entered by user
    private String executionUniqueId;
    private int setExecutionId;


    
    @Override
    public String execute() throws Exception {
        String scenarioId = (String)this.getRequest().getSession().getAttribute("scenarioId");  // really not necessary here to do.
        String caseExecId = (String)this.getRequest().getSession().getAttribute("caseExecId");        
        String userName = this.getProfile().getUsername();
        boolean progress = true;
        if(upload == null || uploadFileName == null){
            this.addActionError("Please select a file to attach");
            progress = false;
        }
        if(description != null && description.length() > 2000){
            this.addActionError("File description cannot be more than 2000 characters");
            progress = false;
        }
        if (progress){
            AttachmentDto attachmentDto = new AttachmentDto();
            
            if (setExecutionId>0 && (executionUniqueId!=null && !"".equals(executionUniqueId))) {
                attachmentDto.setSetExecutionId(new Integer(setExecutionId));
            } else
                attachmentDto.setCaseExecId(Integer.parseInt(caseExecId));
            attachmentDto.setFile(upload);
            attachmentDto.setDescription(description);
            attachmentDto.setUser(userName);
            attachmentDto.setFileName(uploadFileName);
            attachmentDto.setStatus("ACTIVE");
            attachmentDto.setContentType(uploadContentType);
            AttachmentManager.getInstance().executeAttachment(attachmentDto);
            this.addActionMessage("Document "+"\""+uploadFileName+"\"" +" attached successfully");
        }
        return SUCCESS;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
