/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.mockmsgreceiver.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
*
* Venkat.Keesara
* Nov 23, 2011
*/
public class ApplicationContextProvider implements ApplicationContextAware {

	private static final Log log = LogFactory.getLog(ApplicationContextProvider.class);

	private static ApplicationContext applicationContext;

	/**
	 * Constructs a ApplicationContext.
	 */
	public ApplicationContextProvider() {
		System.out.println("Public Constructor called==applicationContext===" + applicationContext);
	}

	public void setApplicationContext(ApplicationContext ctx)throws BeansException {
		System.out.println("*** ApplicationContextProvider::setApplicationContext()===" + ctx);
		applicationContext = ctx;
		System.out.println("*** ApplicationContextProvider::setApplicationContext()==applicationContext.hashcode===" + applicationContext.hashCode());
		
	}

	public static ApplicationContext getApplicationContext() {
		System.out.println("getApplicationContext() called==applicationContext==" + applicationContext);
		if(applicationContext!=null){
			System.out.println("getApplicationContext() called==applicationContext.hashcode==" + applicationContext.hashCode());
		}
		return applicationContext;
	}

	// ACCESSING - STATIC

	public static Object getBean(String name) {
		System.out.println("getBean() called==applicationContext==" + applicationContext);
		if(applicationContext!=null){
			System.out.println("getBean() called==applicationContext.hashcode==" + applicationContext.hashCode());
			return applicationContext.getBean(name);
		}
		return null;
		
	}

}
