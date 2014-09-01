package com.dev.appxpress.gps.android_pc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;

import com.dev.appxpress.gps.android_pc.runtime.exec.PortForwarder;

/**
 * Read data stored in an Android device via USB cable.
 * 
 * @author NThusitha
 * @Date 21-August-2014
 * 
 */
public class AndroidPCClientLauncher {

	private static long QUERY_INTERVAL = 30000;

	public static void main(String[] args) throws SecurityException,
			FileNotFoundException, IOException {

		if (args.length > 0) {
			QUERY_INTERVAL = Long.parseLong(args[0]);
		}
		

		/*LogManager logManager = LogManager.getLogManager();
		logManager
				.readConfiguration(new FileInputStream(
						"C:\\sandbox\\ethicalsourcing\\GPSAndoirdPCModule\\android-pc\\src\\main\\resources\\log\\log.config"));*/

		PortForwarder.forwardTCP();
		AndroidDataReader reader = new AndroidDataReader();

		for (;;) {

			reader.doConnect();
			try {
				Thread.sleep(QUERY_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
