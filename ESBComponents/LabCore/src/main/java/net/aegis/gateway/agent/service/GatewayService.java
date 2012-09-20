package net.aegis.gateway.agent.service;

import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Gateway;
import net.aegis.lab.dao.pojo.User;

        
public interface GatewayService {

    List<Gateway> findAllGateway();
    List<Gateway> findAllGateway(User user);
    public Gateway findById(String hcid);
    
}
