package com.dev.appxpress.gps.android_pc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

import com.dev.appxpress.gps.android_pc.runtime.exec.PortForwarder;

/**
 * Read data stored in an Android device via USB cable.
 * @author NThusitha
 * @Date 21-August-2014
 *
 */
public class App 
{
    public static void main( String[] args ) throws SecurityException, FileNotFoundException, IOException
    {
    	
		LogManager logManager = LogManager.getLogManager();

		// logManager.readConfiguration(new
		// FileInputStream(C:\sandbox\ethicalsourcing\gps-reader\src\main\resources\log\log.config"));
		logManager
				.readConfiguration(new FileInputStream(
						"C:\\sandbox\\ethicalsourcing\\GPSAndoirdPCModule\\android-pc\\src\\main\\resources\\log\\log.config"));
    	
		
		PortForwarder.forwardTCP();
        AndroidDataReader reader = new AndroidDataReader();
        
        
        for(;;){
        	
        	reader.doConnect();
        	try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
