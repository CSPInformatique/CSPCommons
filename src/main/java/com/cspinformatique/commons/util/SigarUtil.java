package com.cspinformatique.commons.util;

import java.util.ArrayList;
import java.util.List;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class SigarUtil {
	private Sigar sigar;
	
	public SigarUtil(){
		this.sigar = new Sigar();
	}
	
	public long getPId(String searchString){

		try{
			for(long pid : this.sigar.getProcList()){
				try{
					for(String arg :this.sigar.getProcArgs(pid)){
						if(	arg.toLowerCase().contains(searchString.toLowerCase())){
							return pid;
						}
					}
				}catch(SigarException sigarEx){
					// No arguments. Nothing to do.
				}
			}
			return 0;
		}catch(SigarException sigarEx){
			throw new RuntimeException(sigarEx);
		}
	}
	
	public List<Long> getPIds(String searchString){
		List<Long> list = new ArrayList<Long>();
		try{
			for(long pid : this.sigar.getProcList()){
				for(String arg :this.sigar.getProcArgs(pid)){
					if(	arg.toLowerCase().contains(searchString.toLowerCase())){
						list.add(pid);
					}
				}
			}
			return list;
		}catch(SigarException sigarEx){
			throw new RuntimeException(sigarEx);
		}
	}
	
	public int getAverageCpu(){
		try{
			double sum = 0d;
			for(CpuPerc cpuPerc : this.sigar.getCpuPercList()){
				sum += cpuPerc.getCombined() * 100;
			}
			
			return (int)(sum / this.sigar.getCpuPercList().length);
		}catch(SigarException sigarEx){
			throw new RuntimeException(sigarEx);
		}
	}
	
	public int getFreeMemory(){
		try{
			return (int)(this.sigar.getMem().getActualFree() / 1000000);
		}catch(SigarException sigarEx){
			throw new RuntimeException(sigarEx);
		}
	}
	
	public int getUsedMemoryPercent(){
		try{
			return (int)this.sigar.getMem().getUsedPercent();
		}catch(SigarException sigarEx){
			throw new RuntimeException(sigarEx);
		}
	}
	
	public int getTotalMemory(){
		try{
			return (int)(this.sigar.getMem().getTotal() / 1000000);
		}catch(SigarException sigarEx){
			throw new RuntimeException(sigarEx);
		}
	}
}
