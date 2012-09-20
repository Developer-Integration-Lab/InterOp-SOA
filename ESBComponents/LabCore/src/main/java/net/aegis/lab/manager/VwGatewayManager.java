/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.lab.manager;

import java.util.List;

import net.aegis.lab.dao.pojo.VwGateway;
import net.aegis.lab.util.ContextUtil;
import net.aegis.lab.view.service.VwGatewayService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * Venkat.Keesara Feb 19, 2012
 */
public class VwGatewayManager {

	private static final Log log = LogFactory.getLog(VwGatewayManager.class);
	private VwGatewayService vwGatewayService = (VwGatewayService) ContextUtil.getLabContext().getBean("vwGatewayService");
	private static VwGatewayManager instance;
	
	private VwGatewayManager() {
    }

    /**
     * @return VwGatewayManager
     */
    public static VwGatewayManager getInstance() {
        if (instance == null) {
            instance = new VwGatewayManager();
        }
        return instance;
    }
    
	public VwGateway findByGatewayAddress(String gatewayAddress) {

		VwGateway vwGateway = null;
		List<VwGateway> vwVwGatewayList = vwGatewayService.findByGatewayAddress(gatewayAddress);
		if (vwVwGatewayList != null && vwVwGatewayList.size() > 0) {
			vwGateway = vwVwGatewayList.get(0);
		}

		return vwGateway;
	}
}
