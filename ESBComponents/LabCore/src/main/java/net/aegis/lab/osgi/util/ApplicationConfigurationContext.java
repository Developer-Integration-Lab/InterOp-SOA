package net.aegis.lab.osgi.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.springframework.context.ApplicationContext;
import org.springframework.osgi.context.BundleContextAware;

/**
 * 
 * @author Venkat.Keesara
 * 
 */
public class ApplicationConfigurationContext implements BundleContextAware {

	private static final Log log = LogFactory.getLog(ApplicationConfigurationContext.class);
	// SINGLETON

	private static final ApplicationConfigurationContext instance = new ApplicationConfigurationContext();
	// INSTANCE VARIABLES
	private BundleContext bundleContext;

	public void setBundleContext(BundleContext bundleContext) {
		log.info("setBundleContext called before init:::");
		this.bundleContext = bundleContext;
		log.info("instance===" + instance);
	}

	private ApplicationContext applicationContext;
	private boolean isInitialized = false;

	/**
	 * Constructs a ApplicationContext.
	 */
	private ApplicationConfigurationContext() {
		log.info("Private Constructor called==instance==" +instance );
	}
	
	public ApplicationConfigurationContext getInstance(){
		log.info("getInstance() | instance ===" + instance);
		return instance ; 
	}
	
	public static ApplicationContext   getApplicationContext(){
		log.info("getApplicationContext() called==instance==" +instance );
		if (!instance.isInitialized) {
			log.info("getApplicationContext() | Before Calling initialize()");
			initialize();
		}
		log.info("getApplicationContext() | instance available == " + instance);
		return instance.applicationContext;
		
	}

	public void init() {

		log.info("Init method is called after setBundleContext()");
		log.info("Init() | instance===" + instance);
		log.info("Init() | this.bundleContext==" + this.bundleContext);

		log.info("Init() | bundleContext.hashCode==" + bundleContext.hashCode());
		Bundle bundle = bundleContext.getBundle();
		log.info("Init() | bundleContext.getBundle==" + bundle);
		log.info("Init() | vBundle sysmbolic name ==" + bundle.getSymbolicName());
		instance.bundleContext = this.bundleContext;
	}

	public static synchronized void initialize() {
		
		log.info("initialize method is called after setBundleContext()");
		log.info("initialize() | instance===" + instance);
		BundleContext instanceBundleContext = instance.bundleContext;
		log.info("initialize() | instance.bundleContext==" + instanceBundleContext);
		log.info("initialize() | instanceBundleContext.hashCode==" + instanceBundleContext.hashCode());
		Bundle bundle = instanceBundleContext.getBundle();
		log.info("initialize() | bundleContext.getBundle==" + bundle);
		log.info("initialize() | vBundle sysmbolic name ==" + bundle.getSymbolicName());
		String bundleName = bundle.getSymbolicName();
		ServiceReference webContext;
		StringBuffer filter = new StringBuffer("(org.springframework.context.service.name=");
		filter.append(bundleName);
		filter.append(")");
		try {
			webContext = instanceBundleContext.getAllServiceReferences(
					"org.springframework.context.ApplicationContext", filter.toString())[0];
			ApplicationContext ac = (ApplicationContext) instanceBundleContext.getService(webContext);
			ApplicationConfigurationContext.initialize(ac);
			
		} catch (Exception e) {
			log.error("bundleContext.getServiceReference error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static synchronized void initialize(ApplicationContext applicationContextVar)

	{
		if (instance.isInitialized) {
			return;
		}
		instance.applicationContext = applicationContextVar;
		log.info("ApplicationContext|BeanDefinitionNames ==");
		log.info(applicationContextVar.getBeanDefinitionNames());
		instance.isInitialized = true;
		log.info("initialized");
	}

	// RELEASE

	/**
	 * Releases the resources held onto by the ServiceLayerContext.
	 */
	public static void release() {
		instance.applicationContext = null;
		instance.isInitialized = false;
		log.info("released");
	}

	// ACCESSING - STATIC

	public static Object getBean(String name) {
		log.info("getBean() called==instance==" +instance );
		if (!instance.isInitialized) {
			log.info("getBean() | Before Calling initialize()");
			initialize();
		}
		log.info("getBean() | instance available == " + instance);
		return instance.applicationContext.getBean(name);
	}

}
