/**
 * 
 */
package com.dev.appxpress.gps.android_pc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dev.appxpress.gps.android_pc.data.handler.GPSDataHandler;

/**
 * @author NThusitha
 * 
 */
public class AndroidDataReader {

	private static final Logger log = Logger.getLogger(AndroidDataReader.class.getName());
	private final static int SERVER_PORT = 38000;
	private final static String EOD_FLAG = "END";
	private GPSDataHandler handler = new GPSDataHandler();
	

	public void doConnect() {

		try {
			Socket client = new Socket("localhost", SERVER_PORT);
			
			log.log(Level.FINER, "opened connection to android device");
			
			//no need to using writer for now.
			PrintWriter writer = new PrintWriter(client.getOutputStream());
			
			
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (buffReader.ready()) {

				//StringBuffer dataBuff = new StringBuffer();

				String input;
				while ((input = buffReader.readLine()) != null) {
					if(input.contains(EOD_FLAG)){
						break;
					}
					//dataBuff.append(input);
					//dataBuff.append("\n");
					
					log.log(Level.FINER, "reading line {0}", input);
					handler.readData(input);
					//tempBuff.add(input);
					
				}

			//	System.out.print(dataBuff.toString());

			}else{
				log.log(Level.FINER, "device is not ready");
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	
	
	
}
