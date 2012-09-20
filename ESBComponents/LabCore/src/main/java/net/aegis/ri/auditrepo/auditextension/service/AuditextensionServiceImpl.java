package net.aegis.ri.auditrepo.auditextension.service;

import java.util.List;

import net.aegis.ri.auditrepo.dao.AuditextensionDAO;
import net.aegis.ri.auditrepo.dao.pojo.Auditextension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author richard.ettema
 */
@Service("auditextensionService")
public class AuditextensionServiceImpl implements AuditextensionService {

    @Autowired
    private AuditextensionDAO auditextDao;
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(AuditextensionServiceImpl.class);

    @Override
    public Auditextension findById(long auditextensionId) {
        return auditextDao.read(auditextensionId);
    }

    @Override
    public List<Auditextension> findByAuditrepositoryId(long auditrepositoryId) {
        return auditextDao.findByAuditrepositoryId(auditrepositoryId);
    }

    @Override
    public List<Auditextension> findAll() {
        return auditextDao.findAll();
    }

}
