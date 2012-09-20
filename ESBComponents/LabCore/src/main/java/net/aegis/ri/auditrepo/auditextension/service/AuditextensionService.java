package net.aegis.ri.auditrepo.auditextension.service;

import java.util.List;

import net.aegis.ri.auditrepo.dao.pojo.Auditextension;

/**
 *
 * @author richard.ettema
 */
public interface AuditextensionService {

    /*
     * Finder methods
     */
    public Auditextension findById(long auditextensionId);
    public List<Auditextension> findByAuditrepositoryId(long auditrepositoryId);
    public List<Auditextension> findAll();

}
