package net.aegis.lab.genericdao.finder;

import java.lang.reflect.Method;

/**
 * @author Ram Ghattu
 * Used to locate a named query based on the called finder method
 */
public interface FinderNamingStrategy
{
    public String queryNameFromMethod(Class findTargetType, Method finderMethod);
}
