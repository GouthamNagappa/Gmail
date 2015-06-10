package com.adaptavant.gmail;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PersitenceManagerFactoryClass {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
		.getPersistenceManagerFactory("transactions-optional");
 
	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}
