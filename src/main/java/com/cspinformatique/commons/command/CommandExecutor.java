package com.cspinformatique.commons.command;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class CommandExecutor {
	static final Logger logger = Logger.getLogger(CommandExecutor.class);
	
	protected void runCommand(List<String> commands){
		this.runCommand(commands, null);
	}
	
	protected void runCommand(List<String> commands, String workingDirectory){
		try{
			String command = "";
			ProcessBuilder processBuilder = new ProcessBuilder(commands);
			if(workingDirectory != null){
				processBuilder = processBuilder.directory(new File(workingDirectory));
				command = workingDirectory + "/";
			}
			for(String arg : commands){
				command += arg + " ";
			}
			
			logger.debug("Executing : " + command);
			
			Process process = processBuilder.start();
			
			new Thread(new InputHandler(process.getInputStream())).start();
			new Thread(new InputHandler(process.getErrorStream())).start();
			
			process.waitFor();
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}
