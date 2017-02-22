package com.wso2telco.tip.balance.conf;

import io.dropwizard.setup.Environment;

import java.io.Serializable;

public class ConfigReader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8945346314273239957L;
	private ApplicationConfiguration applicationConfiguration;
	private static ConfigReader reader;
	private Environment env;


	private ConfigReader(final ApplicationConfiguration applicationConfiguration, final Environment env) {
		this.applicationConfiguration = applicationConfiguration;
		this.env= env;

	}
	public static void init(final ApplicationConfiguration applicationConfiguration,final Environment env){
		reader = new ConfigReader(applicationConfiguration,env);
	}


	public static synchronized ConfigReader getInstance() {
		return reader;
	}
	
	public ApplicationConfiguration getApplicationConfiguration(){
		return applicationConfiguration;
	}
	
	public Environment getEnvironment(){
		return env;
	}
}
