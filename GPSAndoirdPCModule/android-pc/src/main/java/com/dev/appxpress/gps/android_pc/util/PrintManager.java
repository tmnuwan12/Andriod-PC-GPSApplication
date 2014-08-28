/**
 * 
 */
package com.dev.appxpress.gps.android_pc.util;

/**
 * @author NThusitha
 *
 */
public class PrintManager {

	
	public static boolean PRINT_HEADER = true;
	
	/**
	 * Print columns if not printed (happens once).
	 * 
	 * @return
	 */
	public static void printHeader() {
		if (PRINT_HEADER) {
		/*	System.out.printf("%1$s                 %2$s     %3$s    %4$s      %5$s\n",
					"Time", "Latitude", "Longitude",
					"Average Number of SVs per Reading",
					"Average Number of Fixes Reachable");
			PRINT_HEADER = true;*/
			
			System.out.printf("%1$8s %2$22s %3$15s %4$20s %5$15s %6$15s %7$15s\n",
					"Time(UTC)", 
					"Latitude", 
					"Longitude",
					"Elevation",
					"Accuracy(m)",
					"Bearing",
					"Speed");
			PRINT_HEADER = false;
		}
	}
	
}
