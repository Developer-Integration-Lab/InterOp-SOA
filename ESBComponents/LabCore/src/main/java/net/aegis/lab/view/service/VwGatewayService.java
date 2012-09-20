/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.lab.view.service;

import java.util.List;

import net.aegis.lab.dao.pojo.VwGateway;

/**
 *
 * Venkat.Keesara
 * Feb 19, 2012
 */
public interface VwGatewayService {
	public List<VwGateway> findByGatewayAddress(String gatewayAddress) ;

}
