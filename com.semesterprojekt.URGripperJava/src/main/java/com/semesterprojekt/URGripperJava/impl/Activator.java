package com.semesterprojekt.URGripperJava.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;

/**
 * Hello world activator for the OSGi bundle URCAPS contribution
 *
 */
public class Activator implements BundleActivator {
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Activator says Hello World!");
		
		bundleContext.registerService(SwingProgramNodeService.class, new URGripperProgramNodeService(), null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		
	}
}