package net.aegis.lab.genericdao.finder.impl;

import net.aegis.lab.genericdao.finder.FinderExecutor;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DefaultIntroductionAdvisor;

/**
 * 
 * @author Ram Ghattu
 */
public class FinderIntroductionAdvisor extends DefaultIntroductionAdvisor {

    public FinderIntroductionAdvisor() {
        super(new FinderIntroductionInterceptor());
    }

    @Override
    public ClassFilter getClassFilter() {
        return new ClassFilter() {

            @Override
            public boolean matches(Class clazz) {
                //log.debug("Intercepted class type: " + clazz.getCanonicalName());
                //log.debug("FinderExecutor.class.isAssignableFrom(clazz) = " + FinderExecutor.class.isAssignableFrom(clazz));
                return FinderExecutor.class.isAssignableFrom(clazz);
            }
        };
    }
}
