/**
 * 
 */
package com.dev.appxpress.gps.android_pc.data.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import com.dev.appxpress.gps.android_pc.domain.GPSWayPoint;
import com.dev.appxpress.gps.android_pc.domain.GPSWayPoint.GPSWayPoints;
import com.dev.appxpress.gps.android_pc.util.DateTimeUtil;
import com.dev.appxpress.gps.android_pc.util.PrintManager;

/**
 * GPS Data Handler.
 * 
 * @author NThusitha
 * @date 21-August-2014
 * 
 */
public class GPSDataHandler {

	private static final Logger log = Logger.getLogger(GPSDataHandler.class
			.getName());
	
	// last time the system read data from the device.
	private static long LAST_UPDATED_AT = 0;
	private static int DATA_COLUMN_COUNT = 7;
	private static final int CACHE_DURATION_MS = 1000 * 60;

	private GPSWayPoints wayPoints = new GPSWayPoints();

	private static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

	private static DescriptiveStatistics latStats = new DescriptiveStatistics();
	private static DescriptiveStatistics lonStats = new DescriptiveStatistics();
	private static DescriptiveStatistics elevationStats = new DescriptiveStatistics();
	private static DescriptiveStatistics accuracyStats = new DescriptiveStatistics();
	private static DescriptiveStatistics bearingStats = new DescriptiveStatistics();
	private static DescriptiveStatistics speedStats = new DescriptiveStatistics();

	/**
	 * @param data
	 */
	public void readData(String data) {

		if (!data.isEmpty()) {

			String[] dataElements = data.split(",");

			try {
				if (dataElements.length == DATA_COLUMN_COUNT
						&& !dataElements[0].startsWith("time")) {

					Date time = SIMPLE_DATE_FORMATTER.parse(dataElements[0]);
					double lat = Double.parseDouble(dataElements[1]);
					double lon = Double.parseDouble(dataElements[2]);
					double elevation = Double.parseDouble(dataElements[3]);
					double accuracy = Double.parseDouble(dataElements[4]);
					double bearing = Double.parseDouble(dataElements[5]);
					double speed = Double.parseDouble(dataElements[6]);

					GPSWayPoint point = new GPSWayPoint(time, lat, lon,
							elevation, accuracy, bearing, speed);
					// wayPoints.addWayPoints(point);
					addGPSWayPoint(point);

				}

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

	}
	
	
	
	
	

	/**
	 * 
	 * Need to do this time agnostic way.
	 * 
	 * @param wayPoint
	 */
	private synchronized void addGPSWayPoint(final GPSWayPoint wayPoint) {

		if (wayPoint != null) {

			if (LAST_UPDATED_AT == 0) {

				log.log(Level.FINER, "initializing LAST_UPDATED_AT to {0}",
						wayPoint.getTime().getTime());
				LAST_UPDATED_AT = wayPoint.getTime().getTime();
				// process data in buffer for each overflow timing
			} else if ((wayPoint.getTime().getTime() - LAST_UPDATED_AT) >= CACHE_DURATION_MS) {
				
				log.log(Level.FINER, "processing waypoints, current waypoint size {0}", wayPoints.getWaypoints().size());
				filter(wayPoint);
				process(wayPoints);
				// flush data after each process
				wayPoints.getWaypoints().clear();
				LAST_UPDATED_AT = wayPoint.getTime().getTime();
				return;
			}
			filter(wayPoint);
		}

	}

	/**
	 * @param point
	 */
	private void filter(final GPSWayPoint point) {

		if (point != null && point.getTime() != null) {

			if (point.getTime().getTime() >= LAST_UPDATED_AT) {
				log.log(Level.FINER, "inside filter, adding point to waypoints");
				wayPoints.addWayPoints(point);
			}
		}

	}

	/**
	 * 
	 * Process incoming gps data sentence
	 * 
	 * @param wayPoints
	 */
	private static void process(GPSWayPoints wayPoints) {

		if (wayPoints != null) {
			if (!wayPoints.getWaypoints().isEmpty()) {

				double meanLat = 0;
				double meanLon = 0;
				double meanElevation = 0;
				double meanAccuracy = 0;
				double meanBearing = 0;
				double meanSpeed = 0;

				resetStats();

				for (GPSWayPoint point : wayPoints.getWaypoints()) {
					latStats.addValue(point.getLat());
					lonStats.addValue(point.getLon());
					elevationStats.addValue(point.getElevation());
					accuracyStats.addValue(point.getAccuracy());
					bearingStats.addValue(point.getBearing());
					speedStats.addValue(point.getSpeed());
				}

				meanLat = latStats.getMean();
				meanLon = lonStats.getMean();
				meanElevation = elevationStats.getMean();
				meanAccuracy = accuracyStats.getMean();
				meanBearing = bearingStats.getMean();
				meanSpeed = speedStats.getMean();

				print(meanLat, meanLon, meanElevation, meanAccuracy,
						meanBearing, meanSpeed);

			}
		}

	}

	/**
	 * Reset all stats
	 */
	private static void resetStats() {
		latStats.clear();
		lonStats.clear();
		elevationStats.clear();
		accuracyStats.clear();
		bearingStats.clear();
		speedStats.clear();
	}

	/**
	 * Print with formatting on
	 * 
	 * @param args
	 */
	private static void print(double... args) {

		PrintManager.printHeader();
		if (args.length == 6) {
			Object[] printables = new Object[] { DateTimeUtil.nowUTCString(),
					args[0], args[1], args[2], args[3], args[4], args[5] };

			System.out
					.printf("%1$s %2$15.3f %3$15.3f %4$15.3f %5$20.3f %6$15.3f %7$15.3f\n",
							printables);

		}

	}

}
