package net.aegis.lab.web.action.participant;

import net.aegis.lab.manager.AttachmentManager;
import net.aegis.lab.web.action.BaseAction;

/**
 *
 * @author ram.ghattu
 */
public class DeleteAttachment extends BaseAction {

    private static final long serialVersionUID = 1L;
    private String attachmentId;
    private String filename;

    @Override
    public String execute() throws Exception {
        log.info("DeleteAttachment.execute() - INFO START");
        AttachmentManager.getInstance().deleteAttachmentById(Integer.parseInt(attachmentId));
        this.addActionMessage("Deleted file " + filename + " successfully");
        log.info("DeleteAttachment.execute() - INFO END");
        return SUCCESS;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
