package com.cspinformatique.commons.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class ZipUtil {
	static public void extractFolder(String stringZipFile, String destinationFolder) {
		try{
		int BUFFER = 2048;
	    File sourceZipFile = new File(stringZipFile);
	    File unzipDestinationDirectory = new File(destinationFolder);
	    unzipDestinationDirectory.mkdir();

	    ZipFile zipFile;
	    // Open Zip file for reading
	    zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);

	    // Create an enumeration of the entries in the zip file
	    Enumeration<?> zipFileEntries = zipFile.entries();

	    // Process each entry
	    while (zipFileEntries.hasMoreElements()) {
	        // grab a zip file entry
	        ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();

	        File destFile = new File(unzipDestinationDirectory, entry.getName());

	        // grab file's parent directory structure
	        File destinationParent = destFile.getParentFile();

	        // create the parent directory structure if needed
	        destinationParent.mkdirs();

            // extract file if not a directory
            if (!entry.isDirectory()) {
                BufferedInputStream is =
                        new BufferedInputStream(zipFile.getInputStream(entry));
                int currentByte;
                // establish buffer for writing file
                byte data[] = new byte[BUFFER];

                // write the current file to disk
                FileOutputStream fos = new FileOutputStream(destFile);
                BufferedOutputStream dest =
                        new BufferedOutputStream(fos, BUFFER);

                // read and write until last byte is encountered
                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, currentByte);
                }
                dest.flush();
                dest.close();
                is.close();
            }else{
            	new File(destinationFolder + "/" + entry.getName()).mkdirs();
            }
	    }
	    zipFile.close();
	    
        if(!System.getProperty("os.name").toUpperCase().contains("WINDOWS")){
        	Runtime.getRuntime().exec("chmod -R 770 " + destinationFolder);
        }
		}catch(IOException ioEx){
			throw new RuntimeException(ioEx);
		}
	}
}
