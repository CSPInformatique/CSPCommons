package com.cspinformatique.commons.apache.command;

import com.cspinformatique.commons.apache.entity.ApacheServer;

public interface ApacheCommandExecutor {	
	
	public void changeSourcesRights(ApacheServer apacheServer);
	
	public void configureDeployment(ApacheServer apacheServer, String pcreFolder);
	
	public void makeDeployment(ApacheServer apacheServer);
	
	public void installDeployment(ApacheServer apacheServer);
	
	public void restartApache(ApacheServer apacheServer);
}
