package net.aegis.gateway.agent.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.aegis.gateway.agent.dao.GatewayDAO;
import net.aegis.gateway.agent.dao.pojo.Gateway;
import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.util.LabConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gatewayService")
public class GatewayServiceImpl implements GatewayService{

    @Autowired
    private GatewayDAO gateway;

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(GatewayServiceImpl.class);

	/**
	 * @return
	 * @see net.aegis.gateway.agent.dao.GatewayDAO#findAllGateway()
	 */
	public List<Gateway> findAllGateway() {
		return gateway.findAllGateway();
	}

	/**
	 * @param user
	 * @return
	 * @see net.aegis.gateway.agent.dao.GatewayDAO#findAllGateway(net.aegis.lab.dao.pojo.User)
	 */
	public List<Gateway> findAllGateway(User user) {
		List<Gateway> results = null;
		
		Gateway criteria = new Gateway();
		List<Criterion> criterionList = new ArrayList<Criterion>();
		
    	if (user!=null) {    		    	
    		if (LabConstants.ROLE_PARTICIPANT==user.getPrimaryRole()) {    			
    			criterionList.add(Restrictions.eq("HCID",user.getParticipant().getCommunityId()));
    			results = gateway.searchByCriteria(criteria,criterionList);
    			
    			criteria.setLabNode(new Integer(Gateway.LAB_NODE));
    			results.addAll(gateway.searchByCriteria(criteria));
    		} else {
    			results = gateway.findAllGateway();
    		}
    	}
    	return results;
	}

	/**
	 * @param HCID
	 * @return
	 * @see net.aegis.gateway.agent.dao.GatewayDAO#findById(java.lang.String)
	 */
	public Gateway findById(String HCID) {
		return gateway.findById(HCID);
	}


}
