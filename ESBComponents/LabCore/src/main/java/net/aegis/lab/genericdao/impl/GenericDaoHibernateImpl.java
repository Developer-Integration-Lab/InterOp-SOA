package net.aegis.lab.genericdao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.aegis.lab.genericdao.GenericDao;
import net.aegis.lab.genericdao.finder.FinderArgumentTypeFactory;
import net.aegis.lab.genericdao.finder.FinderExecutor;
import net.aegis.lab.genericdao.finder.FinderNamingStrategy;
import net.aegis.lab.genericdao.finder.impl.SimpleFinderArgumentTypeFactory;
import net.aegis.lab.genericdao.finder.impl.SimpleFinderNamingStrategy;
import net.aegis.lab.util.LabConstants;
import net.aegis.lab.util.LabConstants.LabType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 * @author Ram Ghattu
 * Hibernate implementation of GenericDao
 * A typesafe implementation of CRUD and finder methods based on Hibernate and Spring AOP
 * The finders are implemented through the executeFinder method. Normally called by the FinderIntroductionInterceptor
 */
public class GenericDaoHibernateImpl<T, PK extends Serializable> implements GenericDao<T, PK>, FinderExecutor {

    private SessionFactory sessionFactory;
    private FinderNamingStrategy namingStrategy = new SimpleFinderNamingStrategy(); // Default. Can override in config
    private FinderArgumentTypeFactory argumentTypeFactory = new SimpleFinderArgumentTypeFactory(); // Default. Can override in config
    private static final Log log = LogFactory.getLog(GenericDaoHibernateImpl.class);
    private Class<T> type;

    public GenericDaoHibernateImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PK create(T o) {
        return (PK) getSession().save(o);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T read(PK id) {
        return (T) getSession().get(type, id);
    }

    @Override
    public void update(T o) {
        getSession().merge(o);
    }

    @Override
    public void delete(T o) {
        getSession().delete(o);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> searchByCriteria(T criteriaClass, List<?> criterion) {
        Criteria crit = getSession().createCriteria(criteriaClass.getClass());
        for (Criterion c : (List<Criterion>) criterion) {
            crit.add(c);
        }
        return crit.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> searchByCriteria(T criteriaClass) {
        Criteria crit = getSession().createCriteria(criteriaClass.getClass());
        List<Criterion> criterionList = new ArrayList<Criterion>();
        Class objClass = criteriaClass.getClass();
        Field fields[] = objClass.getDeclaredFields();
        Method methods[] = objClass.getMethods();
        Object args[] = null;
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                try {
                    if (method.invoke(criteriaClass, args) != null) {
                        String name = method.getName().substring(3);
                        Object fieldValue = method.invoke(criteriaClass, args);
                        for (Field field : fields) {
                            if (field.getName().equalsIgnoreCase(name)) {
                                log.info("field name setting --->> " + field.getName() + " field value --->>" + fieldValue);
                                criterionList.add(Restrictions.eq(field.getName(), fieldValue));
                            }
                        }
                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GenericDaoHibernateImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GenericDaoHibernateImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GenericDaoHibernateImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for (Criterion c : criterionList) {
            crit.add(c);
        }
        return crit.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> searchByCriteria(T criteriaClass, Map<String, String> restriction) {
        Criteria crit = getSession().createCriteria(criteriaClass.getClass());
        List<Criterion> criterionList = new ArrayList<Criterion>();
        Class objClass = criteriaClass.getClass();
        Field fields[] = objClass.getDeclaredFields();
        Method methods[] = objClass.getMethods();
        Object args[] = null;
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                try {
                    if (method.invoke(criteriaClass, args) != null) {
                        String name = method.getName().substring(3);
                        Object fieldValue = method.invoke(criteriaClass, args);
                        for (Field field : fields) {
                            if (field.getName().equalsIgnoreCase(name)) {
                                log.info("field name setting --->> " + field.getName() + " field value --->>" + fieldValue);
                                if (null == restriction.get(field.getName())) {
                                    criterionList.add(Restrictions.like(field.getName(), fieldValue));
                                    log.info("*********The field with null and set to default is LIKE" + field.getName());
                                } else {
                                    if (restriction.get(field.getName()).equals("eq")) {
                                        criterionList.add(Restrictions.eq(field.getName(), fieldValue));
                                        log.info("*********The field with eq is" + field.getName());
                                    }
                                    if (restriction.get(field.getName()).equals("ne")) {
                                        criterionList.add(Restrictions.ne(field.getName(), fieldValue));
                                        log.info("*********The field with like is" + field.getName());
                                    }
                                }
                            }
                        }
                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GenericDaoHibernateImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GenericDaoHibernateImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GenericDaoHibernateImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for (Criterion c : criterionList) {
            crit.add(c);
        }
        return crit.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> searchByCriteriaAndSort(T criteriaClass, List<?> criterion, List<?> orderBy) {
        log.info("GenericDaoHibernateImpl.searchByCriteriaAndSort() - INFO");

        Criteria crit = getSession().createCriteria(criteriaClass.getClass());
        for (Criterion c : (List<Criterion>) criterion) {
            if (c.toString().indexOf(criteriaClass.getClass().getSimpleName())>-1) {
                String[] aliasPattern = c.toString().split(LabConstants.SPLITTER_PERIOD);
                if (aliasPattern!=null && aliasPattern.length>2) {
                        String[] valuePattern = aliasPattern[2].split(LabConstants.SPLITTER_EQUAL);
                     crit.createAlias(aliasPattern[1], aliasPattern[1])
                    .add(Restrictions.eq(aliasPattern[1]+LabConstants.DOT_NOTATION+valuePattern[0],new Integer(valuePattern[1])));
                }
            } else
                crit.add(c);
        }

        if(null != orderBy) {
            for(Order s : (List<Order>) orderBy) {
                log.info("GenericDaoHibernateImpl.searchByCriteriaAndSort() - adding orderby s=" + s);
                crit.addOrder(s);
            }
        }
        return crit.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> executeFinder(Method method, final Object[] queryArgs) {
        final Query namedQuery = prepareQuery(method, queryArgs);
        return (List<T>) namedQuery.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterateFinder(Method method, final Object[] queryArgs) {
        final Query namedQuery = prepareQuery(method, queryArgs);
        return (Iterator<T>) namedQuery.iterate();
    }

    @Override
    public ScrollableResults scrollFinder(Method method, final Object[] queryArgs) {
        final Query namedQuery = prepareQuery(method, queryArgs);
        return (ScrollableResults) namedQuery.scroll();
    }

    private Query prepareQuery(Method method, Object[] queryArgs) {
        final String queryName = getNamingStrategy().queryNameFromMethod(type, method);
        ArrayList args = new ArrayList();
        Session session = getSession();

        for (Object arg : queryArgs) {
                if (arg instanceof LabType) {
                    LabType lt = (LabType)arg;
                    Filter f = null;
                    try {
                       f = session.enableFilter(method.getDeclaringClass().getSimpleName());
                       f.setParameter("pL", lt.getId());
                       f.setParameter("pR", lt.getId());
                       f.validate();
                   } catch (Exception ex) {
                        log.debug(ex.toString());
                   }
                } else
                    args.add(arg);
        }

        final Query namedQuery = session.getNamedQuery(queryName);
        String[] namedParameters = namedQuery.getNamedParameters();
        if (namedParameters.length == 0) {
            setPositionalParams(args.toArray(), namedQuery);
        } else {
            setNamedParams(namedParameters, args.toArray(), namedQuery);
        }

        return namedQuery;
    }



    private void setPositionalParams(Object[] queryArgs, Query namedQuery) {
        // Set parameter. Use custom Hibernate Type if necessary
        if (queryArgs != null) {
            for (int i = 0; i < queryArgs.length; i++) {
                Object arg = queryArgs[i];
                Type argType = getArgumentTypeFactory().getArgumentType(arg);
                if (argType != null) {
                    namedQuery.setParameter(i, arg, argType);
                } else {
                    namedQuery.setParameter(i, arg);
                }
            }
        }
    }

    private void setNamedParams(String[] namedParameters, Object[] queryArgs, Query namedQuery) {
        // Set parameter. Use custom Hibernate Type if necessary
        if (queryArgs != null) {
            for (int i = 0; i < queryArgs.length; i++) {
                Object arg = queryArgs[i];
                Type argType = getArgumentTypeFactory().getArgumentType(arg);
                if (argType != null) {
                    namedQuery.setParameter(namedParameters[i], arg, argType);
                } else {
                    if (arg instanceof Collection) {
                        namedQuery.setParameterList(namedParameters[i], (Collection) arg);
                    } else {
                        namedQuery.setParameter(namedParameters[i], arg);
                    }
                }
            }
        }
    }

    public Session getSession() {
        boolean allowCreate = true;
        return SessionFactoryUtils.getSession(getSessionFactory(),allowCreate);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public FinderNamingStrategy getNamingStrategy() {
        return namingStrategy;
    }

    public void setNamingStrategy(FinderNamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
    }

    public FinderArgumentTypeFactory getArgumentTypeFactory() {
        return argumentTypeFactory;
    }

    public void setArgumentTypeFactory(FinderArgumentTypeFactory argumentTypeFactory) {
        this.argumentTypeFactory = argumentTypeFactory;
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
