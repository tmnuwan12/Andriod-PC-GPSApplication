/**
 * 
 */
package com.dev.appxpress.gps.android_pc.runtime.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Port forwarder which will forward tcp ports to android internal port via adb.
 * 
 * @author NThusitha
 * @date 28-August-2014
 * 
 */
public class PortForwarder {

	private static final Logger log = Logger.getLogger(PortForwarder.class
			.getName());
	private static final int PORT = 38000;
	private static final String ADB_COMMAND = "C:\\adt-bundle-windows-x86-20131030\\sdk\\platform-tools\\adb forward tcp:" + PORT
			+ " tcp:" + PORT;

	/**
	 * 
	 */
	public static void forwardTCP() {

		Runtime jRuntime = Runtime.getRuntime();

		try {

			log.log(Level.FINER, "executing command: {0}", ADB_COMMAND);

			Process process = jRuntime.exec(ADB_COMMAND);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			BufferedReader errorReader = new BufferedReader(
					new InputStreamReader(process.getErrorStream()));

			if (reader.ready()) {

				StringBuffer buffer = new StringBuffer();

				String out;
				while ((out = reader.readLine()) != null) {
					buffer.append(out).append("\n");

				}

				if (buffer.length() > 0) {
					log.log(Level.FINER, "command response: {0}", buffer);
				}
			}

			if (errorReader.ready()) {

				StringBuffer eBuff = new StringBuffer();

				String eOut;
				while ((eOut = reader.readLine()) != null) {
					eBuff.append(eOut).append("\n");

				}

				if (eBuff.length() > 0) {
					log.log(Level.FINER, "command error response: {0}", eBuff);
				}

			}

		} catch (IOException e) {

			e.printStackTrace();
			// TODO: Do something

		}

	}

	/**
	 * Only for testing.
	 * @param args
	 */
	public static void main(String[] args) {
		
		PortForwarder.forwardTCP();
		
	}

}
