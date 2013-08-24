package com.cspinformatique.commons.apache.service;

import com.cspinformatique.commons.apache.entity.ApacheServer;

public interface ApacheServerService {
	public void configureServer(ApacheServer apacheServer);
	
	public void deployApache(ApacheServer apacheServer, String sourcesFolder, String pcreFolder);
	
	public void restartApache(ApacheServer apacheServer);
}
