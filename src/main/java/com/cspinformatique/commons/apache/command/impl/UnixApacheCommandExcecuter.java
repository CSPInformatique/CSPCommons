package com.cspinformatique.commons.apache.command.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cspinformatique.commons.apache.command.ApacheCommandExecutor;
import com.cspinformatique.commons.apache.entity.ApacheServer;
import com.cspinformatique.commons.command.CommandExecutor;

public class UnixApacheCommandExcecuter extends CommandExecutor implements ApacheCommandExecutor {
	static final Logger logger = Logger.getLogger(UnixApacheCommandExcecuter.class);
	
	private static final String BIN_FILE = "/bin/apachectl";
	
	@Override
	public void changeSourcesRights(ApacheServer apacheServer){
		List<String> commands = new ArrayList<String>();
		commands.add("chmod");
		commands.add("-R");
		commands.add("777");
		commands.add(apacheServer.getServerRoot());
		
		this.runCommand(commands, apacheServer.getServerRoot() + "/src");
	}
	
	@Override
	public void configureDeployment(ApacheServer apacheServer, String pcreFolder){
		List<String> commands = new ArrayList<String>();
		commands.add("./configure");
		commands.add("--silent");
		commands.add("--enable-proxy");
		commands.add("--enable-proxy-ajp");
		commands.add("--enable-proxy-balancer");
		commands.add("--enable-rewrite");
		commands.add("--prefix=" + apacheServer.getServerRoot());
		commands.add("--with-port=" + apacheServer.getPort());
		commands.add("--with-pcre=" + pcreFolder);
		
		this.runCommand(commands, apacheServer.getServerRoot() + "/src");
		
		logger.info("Apache server " + apacheServer + " deployment configured.");
	}
	
	@Override
	public void installDeployment(ApacheServer apacheServer){
		List<String> commands = new ArrayList<String>();
		commands.add("make");
		commands.add("--silent");
		commands.add("install");
		
		this.runCommand(commands, apacheServer.getServerRoot() + "/src");
		
		logger.info("Apache server " + apacheServer + " installed.");
	}
	
	@Override
	public void makeDeployment(ApacheServer apacheServer){
		List<String> commands = new ArrayList<String>();
		commands.add("make");
		commands.add("--silent");
		
		this.runCommand(commands, apacheServer.getServerRoot() + "/src");
		
		logger.info("Apache server " + apacheServer + " instaler built.");
	}

	@Override
	public void restartApache(ApacheServer apacheServer) {
		List<String> commands = new ArrayList<String>();
		commands.add(apacheServer.getServerRoot() + BIN_FILE);
		commands.add("-k");
		commands.add("graceful");
		
		
		
		this.runCommand(commands);
		
		logger.info("Apache server " + apacheServer + " restarted.");
	}

}
