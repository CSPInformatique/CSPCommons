package com.cspinformatique.commons.apache.entity;

import java.util.List;

public class VirtualHost {
	public final static String TYPE_AJP = "ajp";
	public final static String TYPE_HTTP = "http";
	
	private String context;
	private String type;
	private List<String> loadBalancerMembers;
	private String url;
	
	public VirtualHost(){
		
	}
	
	public VirtualHost(String context, String type, List<String> loadBalancerMembers, String url){
		this.context = context;
		this.type = type;
		this.loadBalancerMembers = loadBalancerMembers;
		this.url = url;
	}
	
	public String getContext(){
		return this.context;
	}
	
	public String getType(){
		return this.type;
	}
	
	public List<String> getLoadBalancerMembers(){
		return this.loadBalancerMembers;
	}
	
	public String getUrl(){
		return this.url;
	}
}
