package com.cspinformatique.commons.command;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class InputHandler implements Runnable{
	static final Logger logger = Logger.getLogger(InputHandler.class);
	
	private InputStream inputStream;
	
	public InputHandler(InputStream inputStream){
		this.inputStream = inputStream;
	}
	@Override
	public void run() {
		try{
			StringWriter writer = new StringWriter();
			
			IOUtils.copy(inputStream, writer);
			logger.debug(writer.toString());
		} catch(Exception ioEx){
			throw new RuntimeException(ioEx);
		}
	}

}
