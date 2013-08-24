package com.cspinformatique.commons.apache.entity;

import java.util.List;

public class ApacheServer {
	private String adminEmail;
	private int port;
	private String serverName;
	private String serverRoot;
	private List<VirtualHost> virtualHosts;
	
	public ApacheServer(){
		
	}
	
	public ApacheServer(
		String adminEmail, 
		int port, 
		String serverName, 
		String serverRoot, 
		List<VirtualHost> virtualHosts
	){
		this.adminEmail = adminEmail;
		this.port = port;
		this.serverName = serverName;
		this.serverRoot = serverRoot;
		this.virtualHosts = virtualHosts;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public int getPort() {
		return port;
	}

	public String getServerName() {
		return serverName;
	}

	public String getServerRoot() {
		return serverRoot;
	}

	public List<VirtualHost> getVirtualHosts() {
		return virtualHosts;
	}
	
	public String toString(){
		return this.getServerName();
	}
}
