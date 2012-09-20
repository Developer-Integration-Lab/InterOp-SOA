/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.Nhinrep;
import net.aegis.lab.dao.pojo.Participant;
import net.aegis.lab.dto.ParticipantPatientMapDto;
import net.aegis.lab.exception.ServiceException;
import net.aegis.lab.participantpatientmap.service.ParticipantPatientMapService;
import net.aegis.lab.util.ContextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Jyoti.Devarakonda
 */
public class ParticipantPatientMapManager {

    private static ParticipantPatientMapManager instance;
    ParticipantPatientMapService participantPatientMapService = (ParticipantPatientMapService) ContextUtil.getLabContext().getBean("participantPatientMapService");
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ParticipantPatientMapManager.class);

    private ParticipantPatientMapManager() {
    }

    /**
     * @return ParticipantManager
     */
    public static ParticipantPatientMapManager getInstance() {
        if (instance == null) {
            instance = new ParticipantPatientMapManager();
        }
        return instance;
    }

    public List<ParticipantPatientMapDto> createAndGetParticipantPatientMap(Participant participant) throws ServiceException {
        log.info("--<<ParticipantPatientMapManager.createAndGetParticipantPatientMap-->>");
        List<ParticipantPatientMapDto> participantPatientMapList = null;
        try {
            participantPatientMapList = participantPatientMapService.createAndGetParticipantPatientMapIds(participant);
            log.info("--<<ParticipantPatientMapManager.createAndGetParticipantPatientMap size-->>" + participantPatientMapList);
        } catch (ServiceException ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }
        return participantPatientMapList;
    }

    public void updateParticipantPatientMapId(List<ParticipantPatientMapDto> participantPatientMapDto) throws ServiceException {
        log.info("ParticipantPatientMapManager.updateParticipantPatientMapId");
        try {
            participantPatientMapService.updateParticipantPatientMapId(participantPatientMapDto);
        } catch (ServiceException ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }
    }

    public void createParticipantPatientMap(List<ParticipantPatientMapDto> participantPatientMapDto, Nhinrep nhinrep) throws ServiceException {
        log.info("ParticipantPatientMapManager.createParticipantPatientMap");
        try {
            participantPatientMapService.createParticipantPatientMapForRegisterParticipant(participantPatientMapDto, nhinrep);
        } catch (ServiceException ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }
    }

    public List<ParticipantPatientMapDto> getPatientListForRegisterParticipant(Nhinrep nhinrep) throws ServiceException {
        List<ParticipantPatientMapDto> participantPatientMapDtoList = null;
        try {
            participantPatientMapDtoList = participantPatientMapService.getParticipantPatientMapList(nhinrep);
        } catch (ServiceException ex) {
            ex.printStackTrace();
            throw new ServiceException(ex);
        }

        return participantPatientMapDtoList;
    }
    
    
    /**
     * @param participantPatientId
     * @param participantId
     * @return
     * @throws ServiceException
     */
    public String getPatientIdFromParticipantPatientMap(String participantPatientId, Integer participantId)throws ServiceException {
    	String returnPatientId = null;
    	try {
    		returnPatientId = participantPatientMapService.getPatientIdFromParticipantPatientMap(participantPatientId, participantId);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new ServiceException(ex);
    	}
    	return returnPatientId;
    }
    
}
