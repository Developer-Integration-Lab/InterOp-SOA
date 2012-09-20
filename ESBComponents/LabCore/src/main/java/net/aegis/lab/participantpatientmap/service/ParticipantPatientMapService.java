package net.aegis.lab.participantpatientmap.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dao.pojo.Participantpatientmap;
import net.aegis.lab.dto.ParticipantPatientMapDto;
import net.aegis.lab.exception.ServiceException;

import org.hibernate.criterion.Criterion;

/**
 *
 * @author richard.ettema
 */
public interface ParticipantPatientMapService {

    public Integer create(Participantpatientmap participantPatientMap)throws ServiceException;
    public void update(Participantpatientmap participantPatientMap)throws ServiceException;
    public Participantpatientmap read(Integer id)throws ServiceException;
    public void delete(Participantpatientmap persistentObject)throws ServiceException;
    public void deleteById(Integer id)throws ServiceException;
    public List<Participantpatientmap> searchByCriteria(Participantpatientmap criteria, List<Criterion> criterionList)throws ServiceException;
    public List<ParticipantPatientMapDto> createAndGetParticipantPatientMapIds(Participant participant) throws ServiceException;
    public void updateParticipantPatientMapId(List<ParticipantPatientMapDto> participantPatientMapDtoList) throws ServiceException;
    public String getPatientIdFromParticipantPatientMap(String patientId,Integer participantId) throws ServiceException;
    public List<ParticipantPatientMapDto> getParticipantPatientMapList(Nhinrep nhinrep) throws ServiceException ;
    public void createParticipantPatientMapForRegisterParticipant(List<ParticipantPatientMapDto> participantPatientMapDtoList,Nhinrep nhinrep) throws ServiceException;
}
