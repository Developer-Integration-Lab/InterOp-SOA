package net.aegis.lab.participantpatientmap.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.aegis.lab.dao.ParticipantpatientmapDAO;
import net.aegis.lab.dao.PatientDAO;
import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Participantpatientmap;
import net.aegis.lab.dao.pojo.Patient;
import net.aegis.lab.dto.ParticipantPatientMapDto;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.util.LabConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author richard.ettema
 */
@Service("participantPatientMapService")
public class ParticipantPatientMapServiceImpl implements ParticipantPatientMapService {

    @Autowired
    ParticipantpatientmapDAO candiatePatientMapServiceDAO;
    @Autowired
    PatientDAO patientDao;
    @SuppressWarnings("unused")
    public static final Log log = LogFactory.getLog(ParticipantPatientMapServiceImpl.class);

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public Integer create(Participantpatientmap participantPatientMap) throws ServiceException {
        log.info("create participantPatientMap......");
        try {
            return candiatePatientMapServiceDAO.create(participantPatientMap);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void deleteById(Integer id) throws ServiceException {
        log.info("deleteById.....participantPatientMap.....");
        try {
            candiatePatientMapServiceDAO.delete(read(id));
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void delete(Participantpatientmap persistentObject) throws ServiceException {
        log.info("delete......participantPatientMap");
        try {
            candiatePatientMapServiceDAO.delete(persistentObject);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Override
    public Participantpatientmap read(Integer id) throws ServiceException {
        log.info("read.....participantPatientMap...");
        Participantpatientmap participantPatientMap;

        try {
            participantPatientMap = candiatePatientMapServiceDAO.read(id);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }

        return participantPatientMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void update(Participantpatientmap participantPatientMap) throws ServiceException {
        log.info("update participantPatientMap......participantPatientMap...");
        try {
            candiatePatientMapServiceDAO.update(participantPatientMap);
        } catch (Exception e) {
            throw new ServiceException(e, ServiceException.ERROR_CODE_DATABASE_DEFAULT);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public List<ParticipantPatientMapDto> createAndGetParticipantPatientMapIds(Participant participant) throws ServiceException {
        log.info("ParticipantPatientMapServiceImpl.getAllParticipantPatientIds..");
        List<Participantpatientmap> result = null;
        List<ParticipantPatientMapDto> participantPatientMapDtoList = new ArrayList<ParticipantPatientMapDto>();
        List<Patient> patientList = null;
        try {
            result = candiatePatientMapServiceDAO.findByParticipantId(participant.getParticipantId());

            Date currentTime = new Date();
            Long time = currentTime.getTime();
            if (result != null && result.size() == 0) {
                log.info("ParticipantPatientMapServiceImpl.getAllParticipantPatientIds the result is" + result.size());
                Patient patient = new Patient();
                List<Criterion> criterionList = new ArrayList<Criterion>();
                criterionList.add(Restrictions.like("status", LabConstants.STATUS_PATIENT_ASSIGNED,MatchMode.START));
                patientList = patientDao.searchByCriteria(patient);
                log.info("ParticipantPatientMapServiceImpl.getAllParticipantPatientIds the size of the patientList is" + patientList.size());
                for (Patient patientNew : patientList) {
                    Participantpatientmap participantpatientmap = new Participantpatientmap();
                    participantpatientmap.setParticipant(participant);
                    participantpatientmap.setPatient(patientNew);
                    participantpatientmap.setCreatetime(new Timestamp(time));
                    participantpatientmap.setCreateuser(participant.getUser().getUsername());
                    participantpatientmap.setChangedtime(new Timestamp(time));
                    participantpatientmap.setChangeduser(participant.getUser().getUsername());
                    candiatePatientMapServiceDAO.create(participantpatientmap);
                }
                result = candiatePatientMapServiceDAO.findByParticipantId(participant.getParticipantId());
            }

            for (Participantpatientmap participantPatientMap : result) {
                ParticipantPatientMapDto participantPatientMapDto = new ParticipantPatientMapDto();
                Patient patientNew = participantPatientMap.getPatient();
                String patientName = patientNew.getLastName() + ", " + patientNew.getFirstName();
                String patientAddr = patientNew.getAddressLine1() + "<br>" + patientNew.getCity() + ", " + patientNew.getState() + " " + patientNew.getZipCode();
                participantPatientMapDto.setPatientName(patientName);
                participantPatientMapDto.setPatientAddress(patientAddr);
                participantPatientMapDto.setPatientId(patientNew.getPatientId());
                participantPatientMapDto.setParticipant(participant);
                participantPatientMapDto.setParticipantPatientMapId(participantPatientMap.getParticipantPatientMapId());
                participantPatientMapDto.setParticipantPatientId(participantPatientMap.getParticipantPatientId());
                participantPatientMapDto.setGender(patientNew.getGender());
                participantPatientMapDto.setDateOfBirth(patientNew.getDateOfBirthStr());
                participantPatientMapDtoList.add(participantPatientMapDto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }
        return participantPatientMapDtoList;
    }

    @Override
    public List<ParticipantPatientMapDto> getParticipantPatientMapList(Nhinrep nhinrep) throws ServiceException {
        log.info("ParticipantPatientMapServiceImpl.getAllParticipantPatientIds..");
        List<ParticipantPatientMapDto> participantPatientMapDtoList = new ArrayList<ParticipantPatientMapDto>();
        List<Patient> patientList = null;
        try {
            Patient patient = new Patient();
            List<Criterion> criterionList = new ArrayList<Criterion>();
            criterionList.add(Restrictions.like("status", LabConstants.STATUS_PATIENT_ASSIGNED, MatchMode.START));
            patientList = patientDao.searchByCriteria(patient, criterionList);
            log.info("ParticipantPatientMapServiceImpl.getParticipantPatientMapList the size of the patientList is" + patientList.size());
            for (Patient patientNew : patientList) {
                ParticipantPatientMapDto participantPatientMapDto = new ParticipantPatientMapDto();
                String patientName = patientNew.getLastName() + ", " + patientNew.getFirstName();
                String patientAddr = patientNew.getAddressLine1() + "<br>" + patientNew.getCity() + ", " + patientNew.getState() + " " + patientNew.getZipCode();
                participantPatientMapDto.setPatientName(patientName);
                participantPatientMapDto.setPatientAddress(patientAddr);
                participantPatientMapDto.setPatientId(patientNew.getPatientId());
                participantPatientMapDto.setGender(patientNew.getGender());
                participantPatientMapDto.setDateOfBirth(patientNew.getDateOfBirthStr());
                participantPatientMapDtoList.add(participantPatientMapDto);
            }

        } catch (Exception ex) {
            throw new ServiceException(ex);
        }

        return participantPatientMapDtoList;
    }

    @Transactional
    @Override
    public void createParticipantPatientMapForRegisterParticipant(List<ParticipantPatientMapDto> participantPatientMapDtoList,Nhinrep nhinrep) throws ServiceException {
        log.info("ParticipantPatientMapServiceImpl.createParticipantPatientMapForParticipant");
        try {
            Date currentTime = new Date();
            Long time = currentTime.getTime();
            for (ParticipantPatientMapDto participantPatientMapDto : participantPatientMapDtoList) {
                    Participantpatientmap participantPatientMap = new Participantpatientmap();
                    participantPatientMap.setParticipant(participantPatientMapDto.getParticipant());
                    participantPatientMap.setParticipantPatientId(participantPatientMapDto.getParticipantPatientId());
                    participantPatientMap.setPatient(patientDao.read(participantPatientMapDto.getPatientId()));
                    participantPatientMap.setCreateuser(nhinrep.getUser().getUsername());
                    participantPatientMap.setCreatetime(new Timestamp(time));
                    participantPatientMap.setChangeduser(nhinrep.getUser().getUsername());
                    this.create(participantPatientMap);
            }

        } catch (Exception ex) {
           ex.printStackTrace();
           throw new ServiceException(ex);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    @Override
    public void updateParticipantPatientMapId(List<ParticipantPatientMapDto> participantPatientMapDtoList) throws ServiceException {
        log.info("ParticipantPatientMapServiceImpl.updateParticipantPatientMapId");
        Date currentTime = new Date();
        Long time = currentTime.getTime();
        for (ParticipantPatientMapDto participantPatientMapDto : participantPatientMapDtoList) {
            Participantpatientmap participantPatientMap = candiatePatientMapServiceDAO.read(participantPatientMapDto.getParticipantPatientMapId());
            participantPatientMap.setParticipantPatientId(participantPatientMapDto.getParticipantPatientId());
            participantPatientMap.setChangedtime(new Timestamp(time));
            participantPatientMap.setChangeduser(participantPatientMapDto.getParticipant().getUser().getUsername());
            candiatePatientMapServiceDAO.update(participantPatientMap);
        }
    }

    @Override
    public List<Participantpatientmap> searchByCriteria(Participantpatientmap criteria, List<Criterion> criterionList)
            throws ServiceException {
        log.info("ParticipantPatientMapServiceImpl.updateParticipantPatientMapId");
        List<Participantpatientmap> participantPatientMap = candiatePatientMapServiceDAO.searchByCriteria(criteria, criterionList);
        return participantPatientMap;
    }

    /**
     *
     * @param participantPatientId
     * @param participantId
     * @return
     * @throws ServiceException
     */
    @Override
    public String getPatientIdFromParticipantPatientMap(String participantPatientId, Integer participantId)
            throws ServiceException {
        String returnPatientId = null;
        try {
            Participantpatientmap criteria = new Participantpatientmap();
            List<Criterion> criterionList = new ArrayList<Criterion>();
            log.info("patient id-->> " + participantPatientId + " participant id --->> " + participantId);
            criterionList.add(Restrictions.eq("participantPatientId", participantPatientId));
            criterionList.add(Restrictions.eq("participant.participantId", participantId));
            List<Participantpatientmap> candPatientMaps = candiatePatientMapServiceDAO.searchByCriteria(criteria, criterionList);
            log.info("The participant patient map size" + candPatientMaps.size());
            for (Participantpatientmap maps : candPatientMaps) {
                if (maps.getPatient() != null) {
                    returnPatientId = maps.getPatient().getPatientId();
                }
                break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }
        return returnPatientId;
    }
}
