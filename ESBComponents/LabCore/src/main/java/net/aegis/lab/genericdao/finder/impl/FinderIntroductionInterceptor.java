package net.aegis.lab.genericdao.finder.impl;

import net.aegis.lab.genericdao.finder.FinderExecutor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.IntroductionInterceptor;

/**
 * @author Ram Ghattu
 * Connects the Spring AOP with the Hibernate DAO
 * For any method beginning with "find" this interceptor will use the FinderExecutor to call a Hibernate named query
 */
public class FinderIntroductionInterceptor implements IntroductionInterceptor {

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(FinderIntroductionInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        FinderExecutor executor = (FinderExecutor) methodInvocation.getThis();

        String methodName = methodInvocation.getMethod().getName();
        if (methodName.startsWith("find") || methodName.startsWith("list")) {
            log.info("invoking finder ..." + methodName);
            Object[] arguments = methodInvocation.getArguments();
            return executor.executeFinder(methodInvocation.getMethod(), arguments);
        } else if (methodName.startsWith("iterate")) {
            Object[] arguments = methodInvocation.getArguments();
            return executor.iterateFinder(methodInvocation.getMethod(), arguments);
        } else if (methodName.startsWith("scroll")) {
            Object[] arguments = methodInvocation.getArguments();
            return executor.scrollFinder(methodInvocation.getMethod(), arguments);
        } else {
            return methodInvocation.proceed();
        }
    }

    @Override
    public boolean implementsInterface(Class intf) {
        return intf.isInterface() && FinderExecutor.class.isAssignableFrom(intf);
    }
}
