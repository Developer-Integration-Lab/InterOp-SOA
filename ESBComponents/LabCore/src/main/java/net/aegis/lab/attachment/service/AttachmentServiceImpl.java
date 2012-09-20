package net.aegis.lab.attachment.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.aegis.lab.caseresult.service.CaseResultService;
import net.aegis.lab.dao.AttachmentDAO;
import net.aegis.lab.dao.pojo.Attachment;
import net.aegis.lab.dao.pojo.Caseexecution;
import net.aegis.lab.dao.pojo.Caseresult;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.dto.AttachmentDto;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.servicesetexecution.service.ServiceSetExecutionService;
import net.aegis.lab.util.FileUtil;
import net.aegis.lab.util.LabConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ram Ghattu
 * Synopsis: This class is used to implement all
 * methods defined in attachment service interface.
 * 
 */
@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentDAO attachmentDAO;
    @Autowired
    private CaseResultService caseResultService;
    @Autowired
    private ServiceSetExecutionService serviceSetExecutionService;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(AttachmentServiceImpl.class);

    /**
     * This method is used to create an attachment
     * @param attachment
     * @return
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Attachment attachment) throws ServiceException {
        log.info("create attachment ......");
        try {
            return attachmentDAO.create(attachment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /**
     * This method is used to read attacment
     * @param id
     * @return
     * @throws ServiceException
     */
    @Override
    public Attachment read(Integer id) throws ServiceException {
        log.info("read attachment ......");
        Attachment attachment = null;
        try {
            attachment = attachmentDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
        return attachment;
    }

    /**
     * This method is used to update attachment
     * @param attachment
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Attachment attachment) throws ServiceException {
        log.info("update attachment......");
        try {
            attachmentDAO.update(attachment);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /**
     * This method is used to delete attachment
     * @param attachment
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Attachment attachment) throws ServiceException {
        log.info("delete attachment......");
        try {
            attachmentDAO.delete(attachment);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /**
     * This method is used to delete attachment by id
     * @param id
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("deleteById attachment......");
        try {
            attachmentDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    /**
     * This method is used to create attachments by case execution id
     * If there is an existing case result with case execution id
     * then it creates new attachment with existing case result id
     * otherwise it creates new attachment with new case result
     *
     * @param attachmentDto
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void createAttachmentByExecId(AttachmentDto attachmentDto) throws ServiceException {
        boolean setAttachmentType = false;
        Caseresult caseResult = null;
        Servicesetexecution serviceSetExecution = null;

        if (attachmentDto.getSetExecutionId()!=null && attachmentDto.getSetExecutionId()>0) {
            setAttachmentType = true;
            log.info("This is a serviceSetExec attachment......for serviceSetExecutionId:" + attachmentDto.getSetExecutionId());
            serviceSetExecution = serviceSetExecutionService.findBySetExecutionId(attachmentDto.getSetExecutionId());
        } else {
            log.info("createAttachmentByExecId attachment......");
            // get case result by caseExecutionId
            caseResult = caseResultService.findByExecIdAndMaxResultId(attachmentDto.getCaseExecId());
            log.info("createAttachmentByExecId attachment......" + caseResult);
        }
        Attachment attachment = new Attachment();
        try {
            if (setAttachmentType) {
                if (serviceSetExecution==null) {
                    log.error("serviceSetExecution was null for serviceSetExecutionId:" + attachmentDto.getSetExecutionId());
                } else {
                    attachment.setCaseresult(null);
                    attachment.setServiceSetExecution(serviceSetExecution);
                    attachment.setFilename(attachmentDto.getFileName());
                    attachment.setContent(FileUtil.getBytesFromFile(attachmentDto.getFile()));
                    attachment.setStatus(attachmentDto.getStatus());
                    attachment.setCreateuser(attachmentDto.getUser());
                    attachment.setChangeduser(attachmentDto.getUser());
                    attachment.setDescription(attachmentDto.getDescription());
                    attachment.setContentType(attachmentDto.getContentType());
                    create(attachment);
                }
            } else {
                if (caseResult != null) {
                    // create new attachment and relate to existing case result
                    // set required attachment fields
                    attachment.setCaseresult(caseResult);
                    attachment.setFilename(attachmentDto.getFileName());
                    attachment.setContent(FileUtil.getBytesFromFile(attachmentDto.getFile()));
                    attachment.setStatus(attachmentDto.getStatus());
                    attachment.setCreateuser(attachmentDto.getUser());
                    attachment.setChangeduser(attachmentDto.getUser());
                    attachment.setDescription(attachmentDto.getDescription());
                    attachment.setContentType(attachmentDto.getContentType());
                    create(attachment);
                } else {
                    // create new attachment and relate to new case result
                    // set case execution for case result
                    Caseexecution caseExec = new Caseexecution();
                    caseResult = new Caseresult();
                    caseExec.setCaseExecutionId(attachmentDto.getCaseExecId());
                    caseResult.setCaseexecution(caseExec);
                    caseResult.setResult(LabConstants.STATUS_MANUAL);
                    caseResult.setMessage("Attach document passed");
                    caseResult.setSubmissionInd("N");
                    caseResult.setExecuteTime(new Timestamp(System.currentTimeMillis()));
                    caseResultService.create(caseResult);
                    // set required attachment fields
                    attachment.setCaseresult(caseResult);
                    attachment.setFilename(attachmentDto.getFileName());
                    attachment.setContent(FileUtil.getBytesFromFile(attachmentDto.getFile()));
                    attachment.setStatus(attachmentDto.getStatus());
                    attachment.setCreateuser(attachmentDto.getUser());
                    attachment.setChangeduser(attachmentDto.getUser());
                    attachment.setDescription(attachmentDto.getDescription());
                    attachment.setContentType(attachmentDto.getContentType());
                    create(attachment);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AttachmentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceException(ex, ServiceException.ERROR_CODE_GLOBAL_DEFAULT);
        }
        log.info("createAttachmentByExecId attachment done......");
    }

    /**
     * This method returns list of attachments by case execution id
     * @param cseExecId
     * @return
     * @throws ServiceException
     */
    @Override
    public List<Attachment> getAttachmentsByExecId(Integer cseExecId) throws ServiceException{
        List<Attachment> attachments = null;
        log.info("createAttachmentByExecId attachment......");
        // get case result by caseExecutionId
        Caseresult caseResult = caseResultService.findByExecIdAndMaxResultId(cseExecId);
        log.info("createAttachmentByExecId attachment......" + caseResult);
        if(caseResult != null){
            attachments = attachmentDAO.findByResultId(caseResult.getResultId());
        }
        return attachments;
    }

    /**
     * this method returns attachments by set execution id
     * @param setExecId
     * @return
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public List<Attachment> getAttachmentsBySetExecutionId(Integer setExecId) throws ServiceException{
    List<Attachment> attachments =
         attachmentDAO.findBySetExecutionId(setExecId);
        return attachments;
    }
}
