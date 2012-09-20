package net.aegis.lab.manager;


import java.util.List;

import net.aegis.lab.attachment.service.AttachmentService;
import net.aegis.lab.dao.pojo.Attachment;
import net.aegis.lab.dto.AttachmentDto;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author ram.ghattu
 */
public class AttachmentManager {

    private static AttachmentManager instance;
    /**
     * @return AttachmentManager
     */
    public static AttachmentManager getInstance() {
        if (instance == null) {
            instance = new AttachmentManager();
        }
        return instance;
    }

    private AttachmentService attachmentService = (AttachmentService) ContextUtil.getLabContext().getBean("attachmentService");
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(AttachmentManager.class);

    public void executeAttachment(AttachmentDto attachmentDto) throws ServiceException{
        log.info("inside execute attachment of AttachmentManager");        
        attachmentService.createAttachmentByExecId(attachmentDto);
        log.info("completed execute attachment of AttachmentManager");
    }

    public List<Attachment> retrieveAttachments(Integer caseExecId) throws ServiceException{
        return attachmentService.getAttachmentsByExecId(caseExecId);
    }

    public List<Attachment> retrieveAttachmentsBySetExecutionId(Integer setExecId) throws ServiceException{
        return attachmentService.getAttachmentsBySetExecutionId(setExecId);
    }


    public Attachment getAttachmentById(Integer attachmentId) throws ServiceException {
        return attachmentService.read(attachmentId);
    }

    public void deleteAttachmentById(Integer attachmentId) throws ServiceException {
        attachmentService.deleteById(attachmentId);
    }
}
