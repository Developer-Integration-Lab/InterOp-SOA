package net.aegis.gateway.agent.dao;

import net.aegis.lab.dao.pojo.User;
import net.aegis.lab.genericdao.GenericDao;
import java.util.List;

import net.aegis.gateway.agent.dao.pojo.Gateway;
import net.aegis.gateway.agent.dao.pojo.Transaction;

/**
 * <p>Hibernate DAO layer for Gateway</p>
 * <p>Generated at Wed Feb 10 22:27:58 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
/**
 * ILT-346
 * @author Sunil.Bhaskarla
 */

public interface GatewayDAO extends GenericDao<Gateway, Integer> {

    
    public List<Gateway> findAllGateway();
    public List<Gateway> findAllGateway(User user);
    public Gateway findById(String HCID);

}
