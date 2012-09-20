package net.aegis.lab.validation.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import net.aegis.lab.dao.ValidationDAO;
import net.aegis.lab.dao.pojo.Servicesetexecution;
import net.aegis.lab.dao.pojo.Validation;
import net.aegis.lab.dto.ValidationDto;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.nhinrep.service.NhinrepService;
import net.aegis.lab.servicesetexecution.service.ServiceSetExecutionService;
import net.aegis.lab.util.LabConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jyoti.Devarakonda
 */
@Service("validationService")
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    private ValidationDAO validationDAO;
    @Autowired
    private NhinrepService nhinRepService;
    @Autowired
    private ServiceSetExecutionService serviceSetExecutionService;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ValidationServiceImpl.class);

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public int create(Validation validation) throws ServiceException {
        log.info("Saving Validation..........");
        try {
            return validationDAO.create(validation);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Validation validation) throws ServiceException {
        log.info("Saving Validation..........");
        try {
            validationDAO.update(validation);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    @Override
    public Validation read(int id) throws ServiceException {
        log.info("Reading Validation..........");
        try {
            return validationDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(int id) throws ServiceException {
        log.info("DeletingByID Validation..........");
        try {
            validationDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Validation persistentObject) throws ServiceException {
        log.info("Deleting Validation..........");
        try {
            validationDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void createValidationServiceSetRecords(List<Servicesetexecution> serviceSetExecs) throws ServiceException {
        Date currentTime = new Date();
        Long time = currentTime.getTime();
        log.info("ValidationServiceImpl.createValidationServiceSetRecords----->>>");
        for (Servicesetexecution serviceSetExec : serviceSetExecs) {
            Validation toBeValidated = new Validation();
            toBeValidated.setServicesetexecution(serviceSetExec);
            toBeValidated.setStatus(LabConstants.STATUS_VALIDATION_SUBMITTED);
            toBeValidated.setCreatetime(new Timestamp(time));
            toBeValidated.setCreateuser(serviceSetExec.getParticipant().getUser().getUsername());
            toBeValidated.setChangeduser(serviceSetExec.getParticipant().getUser().getUsername());
            this.create(toBeValidated);
            log.info("ValidationServiceImpl.createValidationServiceSetRecords----->>>" + toBeValidated);

        }
    }

    @Override
    public List<Validation> searchByCriteria(Validation criteria, List<Criterion> criterion) throws ServiceException {
        log.info("ValidationServiceImpl.searchByCriteria() - INFO");
        try {
            return validationDAO.searchByCriteria(criteria, criterion);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void setValidationRecordsForReview(List<ValidationDto> toBeReviewedValidationRecs, int nhinrepId) throws ServiceException {
        log.info("ValidationServiceImpl.setValidationRecordsForReview----->>>" + nhinrepId);
        try {
            Date currentTime = new Date();
            Long time = currentTime.getTime();
            for (ValidationDto validationRecs : toBeReviewedValidationRecs) {
                if (validationRecs.getAssignedTo() == null) {
                    Validation toBeReviewedValidationRec = validationDAO.read(validationRecs.getValidationId());
                    toBeReviewedValidationRec.setStatus(LabConstants.STATUS_VALIDATION_REVIEW);
                    toBeReviewedValidationRec.getServicesetexecution().setStatus(LabConstants.STATUS_VALIDATION_REVIEW);
                    toBeReviewedValidationRec.setNhinRepId(nhinrepId);
                    toBeReviewedValidationRec.setChangedtime(new Timestamp(time));
                    toBeReviewedValidationRec.setChangeduser(toBeReviewedValidationRec.getServicesetexecution().getParticipant().getUser().getUsername());
                    this.update(toBeReviewedValidationRec);
                    serviceSetExecutionService.updateScenarioExecsAndCaseExecsToCloseForValidationRecs(toBeReviewedValidationRec.getServicesetexecution().getExecutionUniqueId());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void unAssignValidationRecords(List<ValidationDto> toBeUnassignedValidationRecs, int nhinrepId) throws ServiceException {
        log.info("ValidationServiceImpl.unAssignValidationRecords----->>>");
        try {
            Date currentTime = new Date();
            Long time = currentTime.getTime();
            for (ValidationDto validationRecs : toBeUnassignedValidationRecs) {
                if (validationRecs.getAssignedTo() != null) {
                    Validation toBeUnassignedValidationRec = validationDAO.read(validationRecs.getValidationId());

                    if (null != toBeUnassignedValidationRec) {
                        if(toBeUnassignedValidationRec.getNhinRepId() == nhinrepId) {
                            log.info("ValidationServiceImpl.unAssignValidationRecords(): The validation record " +
                                        toBeUnassignedValidationRec.getServicesetexecution().getExecutionUniqueId() +
                                        " belongs to nhinrepId="+nhinrepId+". Updating it next.");
                            toBeUnassignedValidationRec.setStatus(LabConstants.STATUS_VALIDATION_SUBMITTED);
                            toBeUnassignedValidationRec.getServicesetexecution().setStatus(LabConstants.STATUS_VALIDATION_SUBMITTED);
                            toBeUnassignedValidationRec.setNhinRepId(null);
                            toBeUnassignedValidationRec.setChangedtime(new Timestamp(time));
                            toBeUnassignedValidationRec.setChangeduser(toBeUnassignedValidationRec.getServicesetexecution().getParticipant().getUser().getUsername());
                            this.update(toBeUnassignedValidationRec);
                            serviceSetExecutionService.updateScenarioExecsAndCaseExecsToCloseForValidationRecs(toBeUnassignedValidationRec.getServicesetexecution().getExecutionUniqueId());
                        }
                        else {
                            log.info("ValidationServiceImpl.unAssignValidationRecords(): The validation record " +
                                        toBeUnassignedValidationRec.getServicesetexecution().getExecutionUniqueId() +
                                        " did not belong to nhinrepId="+nhinrepId+". Skipping it from any update.");
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException();
        }

    }

    @Override
    public List<Validation> findReviewByNhinRepId(Integer pNhinRepId) throws ServiceException {
     
        log.info("ValidationServiceImpl.findReviewByNhinRepId----->>>" + pNhinRepId);

        try {
            return validationDAO.findReviewByNhinRepId(pNhinRepId);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

    }
}
