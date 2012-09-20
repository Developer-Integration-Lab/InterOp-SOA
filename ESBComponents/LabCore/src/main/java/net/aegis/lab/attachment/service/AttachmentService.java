package net.aegis.lab.attachment.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Attachment;
import net.aegis.lab.dto.AttachmentDto;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Ram Ghattu
 * This interface defines methods related to attachment service
 *
 */
public interface AttachmentService {

    /*
     * Standard CRUD Methods
     */
    public Integer create(Attachment newInstance) throws ServiceException;
    public Attachment read(Integer id) throws ServiceException;
    public void update(Attachment transientObject) throws ServiceException;
    public void delete(Attachment persistentObject) throws ServiceException;
    public void deleteById(Integer id) throws ServiceException;
    public void createAttachmentByExecId(AttachmentDto attachmentDto) throws ServiceException;
    public List<Attachment> getAttachmentsByExecId(Integer cseExecId) throws ServiceException;
    public List<Attachment> getAttachmentsBySetExecutionId(Integer setExecId) throws ServiceException;
}
