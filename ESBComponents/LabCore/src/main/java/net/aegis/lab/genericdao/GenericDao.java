package net.aegis.lab.genericdao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * The basic GenericDao interface with CRUD methods
 * Finders are added with interface inheritance and AOP introductions for concrete implementations
 *
 * Extended interfaces may declare methods starting with find... list... iterate... or scroll...
 * They will execute a preconfigured query that is looked up based on the rest of the method name
 */
public interface GenericDao<T, PK extends Serializable>
{

    PK create(T newInstance);

    T read(PK id);

    void update(T transientObject);

    void delete(T persistentObject);

    public List<T> searchByCriteria(T criteriaClass);
    public List<T> searchByCriteria(T criteriaClass, List<?> criterion);
    public List<T> searchByCriteria(T criteriaClass, Map<String, String> restriction);
    public List<T> searchByCriteriaAndSort(T criteriaClass, List<?> criterion, List<?> orderBy);

}
