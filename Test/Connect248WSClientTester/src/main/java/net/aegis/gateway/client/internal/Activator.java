package net.aegis.gateway.client.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		// TODO add activation code here
		Thread.sleep(120000);
		PD248TestData pdtestdata = new PD248TestData();
		QD248TestData qdtestdata = new QD248TestData();
		RD248TestData rdtestdata = new RD248TestData();
		pdtestdata.testPD();
		Thread.sleep(150000);
		
		qdtestdata.testQD();
		Thread.sleep(120000);
		
		rdtestdata.testRD();
	}

	public void stop(BundleContext context) throws Exception {
		// TODO add deactivation code here
	}

}
