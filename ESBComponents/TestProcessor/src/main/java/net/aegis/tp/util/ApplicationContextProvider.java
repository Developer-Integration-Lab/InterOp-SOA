package net.aegis.tp.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author Venkat.Keesara
 * 
 */
public class ApplicationContextProvider implements ApplicationContextAware {

	private static final Log log = LogFactory.getLog(ApplicationContextProvider.class);

	private static ApplicationContext applicationContext;

	/**
	 * Constructs a ApplicationContext.
	 */
	public ApplicationContextProvider() {
		log.info("Public Constructor called==applicationContext===" + applicationContext);
	}

	public void setApplicationContext(ApplicationContext ctx)throws BeansException {
		log.info("*** ApplicationContextProvider::setApplicationContext()===" + ctx);
		applicationContext = ctx;
		log.info("*** ApplicationContextProvider::setApplicationContext()==applicationContext.hashcode===" + applicationContext.hashCode());
		
	}

	public static ApplicationContext getApplicationContext() {
		log.info("getApplicationContext() called==applicationContext==" + applicationContext);
		if(applicationContext!=null){
			log.info("getApplicationContext() called==applicationContext.hashcode==" + applicationContext.hashCode());
		}
		return applicationContext;
	}

	// ACCESSING - STATIC

	public static Object getBean(String name) {
		log.info("getBean() called==applicationContext==" + applicationContext);
		if(applicationContext!=null){
			log.info("getBean() called==applicationContext.hashcode==" + applicationContext.hashCode());
			return applicationContext.getBean(name);
		}
		return null;
		
	}

}
