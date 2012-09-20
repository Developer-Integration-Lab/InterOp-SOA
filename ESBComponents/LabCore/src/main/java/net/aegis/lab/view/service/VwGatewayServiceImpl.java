/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.lab.view.service;

import java.util.List;

import net.aegis.lab.dao.pojo.VwGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.aegis.lab.dao.VwGatewayDAO;

/**
 *
 * Venkat.Keesara
 * Feb 19, 2012
 */
@Service("vwGatewayService")
public class VwGatewayServiceImpl implements VwGatewayService{

	@Autowired
	private VwGatewayDAO vwGatewayDAO;
	
	@Override
	public List<VwGateway> findByGatewayAddress(String gatewayAddress) {
		return vwGatewayDAO.findByGatewayAddress(gatewayAddress);
	}

}
