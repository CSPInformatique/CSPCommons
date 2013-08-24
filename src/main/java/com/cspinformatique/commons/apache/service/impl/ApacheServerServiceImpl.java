package com.cspinformatique.commons.apache.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.cspinformatique.commons.apache.command.ApacheCommandExecutor;
import com.cspinformatique.commons.apache.entity.ApacheServer;
import com.cspinformatique.commons.apache.entity.VirtualHost;
import com.cspinformatique.commons.apache.service.ApacheServerService;

public class ApacheServerServiceImpl implements ApacheServerService {
	static final Logger logger = Logger.getLogger(ApacheServerServiceImpl.class);
	
	private static final String CONF_FILE = "/conf/httpd.conf";
	
	private ApacheCommandExecutor apacheCommandExecutor;
	private String apacheTemplateFile;
	private String virtualHostTemplateFile;
	
	public ApacheServerServiceImpl(){
		
	}
	
	public ApacheServerServiceImpl(
		ApacheCommandExecutor apacheCommandExecutor,
		String apacheTemplateFile,
		String virtualHostTemplateFile
	){
		this.apacheCommandExecutor = apacheCommandExecutor;
		this.apacheTemplateFile = apacheTemplateFile;
		this.virtualHostTemplateFile = virtualHostTemplateFile;
	}
	
	private void configureDeployment(ApacheServer apacheServer, String pcreFolder){
		this.apacheCommandExecutor.configureDeployment(apacheServer, pcreFolder);
	}
	
	@Override
	public void configureServer(ApacheServer apacheServer) {
		try{	
			logger.info("Configuring Apache server " + apacheServer + ".");
			StringBuffer configBuffer = new StringBuffer();
			// Loading the template file.
			
			logger.debug(
				"Loading template file " + this.apacheTemplateFile
			);
			
			StringBuffer apacheBuffer = new StringBuffer();
			for(String line : FileUtils.readLines(new File(this.apacheTemplateFile))){
				apacheBuffer.append(line + "\n");
			}
			
			// Remplacement des parameters.
			String templateConfig = apacheBuffer.toString();
			
			templateConfig = templateConfig.replaceFirst("serverRoot", apacheServer.getServerRoot());
			templateConfig = templateConfig.replaceFirst("portNumber", apacheServer.getPort() + "");
			templateConfig = templateConfig.replaceFirst("adminEmail", apacheServer.getAdminEmail());
			templateConfig = templateConfig.replaceFirst("serverName", apacheServer.getServerName());
			templateConfig = templateConfig.replaceFirst("serverRoot", apacheServer.getServerRoot());
			templateConfig = templateConfig.replaceFirst("serverRoot", apacheServer.getServerRoot());
			templateConfig = templateConfig.replaceFirst("serverRoot", apacheServer.getServerRoot());
			templateConfig = templateConfig.replaceFirst("serverRoot", apacheServer.getServerRoot());
			
			configBuffer.append(templateConfig + "\n");
			
			logger.debug("Loading template file " + this.virtualHostTemplateFile);
			
			StringBuffer virtualHostBuffer = new StringBuffer();
			for(String line : FileUtils.readLines(new File(this.virtualHostTemplateFile))){
				virtualHostBuffer.append(line + "\n");
			}
			
			String virtualHostTemplate = virtualHostBuffer.toString();
			virtualHostBuffer = new StringBuffer();
			for(VirtualHost virtualHost : apacheServer.getVirtualHosts()){
				String virtualHostConfig = "" + virtualHostTemplate;
				
				virtualHostConfig = virtualHostConfig.replaceFirst("url", virtualHost.getUrl());
				virtualHostConfig = virtualHostConfig.replaceFirst("url", virtualHost.getUrl());
				
				String members = "";
				for(String member : virtualHost.getLoadBalancerMembers()){
					members += "	BalancerMember " + member + "\n";
				}
				
				virtualHostConfig = virtualHostConfig.replaceFirst("loadBalancersMembers", members);
				
				virtualHostConfig = virtualHostConfig.replaceFirst("context", virtualHost.getContext());
				virtualHostConfig = virtualHostConfig.replaceFirst("url", virtualHost.getUrl());
				virtualHostConfig = virtualHostConfig.replaceFirst("context", virtualHost.getContext());
				virtualHostConfig = virtualHostConfig.replaceFirst("url", virtualHost.getUrl());
				
				virtualHostConfig = virtualHostConfig.replaceFirst("url", virtualHost.getUrl());
				virtualHostConfig = virtualHostConfig.replaceFirst("url", virtualHost.getUrl());
				
				virtualHostBuffer.append(virtualHostConfig + "\n");
			}
			
			templateConfig =	configBuffer.toString().replaceFirst(
									"virtualHosts", 
									virtualHostBuffer.toString()
								);
			
			// Writing the config file.
			File configFile = new File(apacheServer.getServerRoot() + CONF_FILE);
			
			logger.debug(
				"Writing configuration to file " + 
					configFile + ".\nConfiguration :\n" + templateConfig
			);
			
			FileUtils.write(configFile, templateConfig, false);
			
			logger.info("Apache server " + apacheServer + " configured.");
		}catch(IOException ioEx){
			throw new RuntimeException(ioEx);
		}
	}
	
	private void copySources(ApacheServer apacheServer, String sourcesFolder){
		try{
			File dest =	new File(apacheServer.getServerRoot() + "/src");
			dest.mkdirs();
			
			FileUtils.copyDirectory(new File(sourcesFolder), dest);
			
			logger.info("Sources for Apache server " + apacheServer + " copied.");
		}catch(IOException ioEx){
			throw new RuntimeException(ioEx);
		}
	}
	
	private void changeSourcesRights(ApacheServer apacheServer){
		this.apacheCommandExecutor.changeSourcesRights(apacheServer);
	}
	
	/*
	this.makeInstallApache(loadBalancerServer);
*/
	private void deleteSources(ApacheServer apacheServer){
		try{			
			FileUtils.deleteDirectory(new File(apacheServer.getServerRoot() + "/src"));
			logger.info("Sources for Apache server " + apacheServer + " cleaned.");
		}catch(IOException ioEx){
			throw new RuntimeException(ioEx);
		}
	}
	
	public void deployApache(ApacheServer apacheServer, String sourcesFolder, String pcreFolder){
		this.copySources(apacheServer, sourcesFolder);
		this.changeSourcesRights(apacheServer);
		this.configureDeployment(apacheServer, pcreFolder);
		this.makeDeployment(apacheServer);
		this.installDeployment(apacheServer);
		this.deleteSources(apacheServer);
	}
	
	private void installDeployment(ApacheServer apacheServer){
		this.apacheCommandExecutor.installDeployment(apacheServer);
	}
	
	private void makeDeployment(ApacheServer apacheServer){
		this.apacheCommandExecutor.makeDeployment(apacheServer);
	}
	
	public void restartApache(ApacheServer apacheServer){
		this.apacheCommandExecutor.restartApache(apacheServer);
	}
}
