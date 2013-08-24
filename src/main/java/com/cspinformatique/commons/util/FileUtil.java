package com.cspinformatique.commons.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class FileUtil {
	static final Logger logger = Logger.getLogger(FileUtil.class);
	
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = FileUtil.deleteDir(new File(dir, children[i]));
                if (!success) {
                	logger.warn("Could not delete " + dir.getAbsolutePath());
                    return false;
                }
            }
        }
    
        // The directory is now empty so delete it
        if(dir.exists()){
        	return dir.delete();
        }else{
        	return true;
        }
	}
	
	public static void downloadFile(String fileToDownload, String destinationFolder, String destinationFilename){
		try{
			// Creating folder structure.
			new File(destinationFolder).mkdirs();
			
			// Downloading the file.
			URL url = new URL(fileToDownload);
			URLConnection connection = url.openConnection();
			InputStream stream = connection.getInputStream();
			
			try(
				BufferedInputStream in = new BufferedInputStream(stream);
				FileOutputStream file = new FileOutputStream(destinationFolder + "/" + destinationFilename);
				BufferedOutputStream out = new BufferedOutputStream(file);		
			){
				int i;
				while ((i = in.read()) != -1) {
				    out.write(i);
				}
				out.flush();
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
}
