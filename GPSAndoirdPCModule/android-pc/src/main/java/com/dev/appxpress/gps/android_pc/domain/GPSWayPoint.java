/**
 * 
 */
package com.dev.appxpress.gps.android_pc.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 
 * Object represents a GPS coordinate at given specific time.
 * 
 * @author NThusitha
 * @date 21-August-2014
 *
 */
public class GPSWayPoint implements GPSAware{

	private Date time;
	private double lat;
	private double lon;
	private double elevation;
	private double accuracy;
	private double bearing;
	private double speed;
	
	
	public GPSWayPoint(Date time, double lat, double lon, double elevation,
			double accuracy, double bearing, double speed) {
		super();
		this.time = time;
		this.lat = lat;
		this.lon = lon;
		this.elevation = elevation;
		this.accuracy = accuracy;
		this.bearing = bearing;
		this.speed = speed;
	}


	public Date getTime() {
		return time;
	}


	public double getLat() {
		return lat;
	}


	public double getLon() {
		return lon;
	}


	public double getElevation() {
		return elevation;
	}


	public double getAccuracy() {
		return accuracy;
	}


	public double getBearing() {
		return bearing;
	}


	public double getSpeed() {
		return speed;
	}
	
	
	/**
	 * Collection of waypoints for given period of time.
	 * 
	 * @author NThusitha
	 *
	 */
	public static class GPSWayPoints implements GPSAware{
		
		private List<GPSWayPoint> waypoints = new ArrayList<GPSWayPoint>();
		
		public GPSWayPoints(){
			
		}
		
		public void addWayPoints(GPSWayPoint waypoint){
			if(waypoint != null){
				waypoints.add(waypoint);
			}
			
		}

		/**
		 * @return
		 */
		public List<GPSWayPoint> getWaypoints() {
			return waypoints;
		}
		
		
		/**
		 * 
		 * This is an expensive operation, invoke it less frequently.
		 * @return
		 */
		public List<GPSWayPoint> getSortedWaypoints(){
			Collections.sort(waypoints, new GPSWayPointCompartor());
			return waypoints;
		}
		
	
		
		
		
	}
	
	
	/**
	 * GPS Way Point comparator which compare way points for ordering.
	 * 
	 * Baseline ordering is performed on time stamp.
	 * If time stamp equality has found then fall back to the way point equality.
	 * @author NThusitha
	 *
	 */
	public static class GPSWayPointCompartor implements Comparator<GPSWayPoint>{

		@Override
		public int compare(GPSWayPoint o1, GPSWayPoint o2) {

			//order the GPSWayPoints according to the time stamp
			int comparison = 0;
			if(o1 != null && o2 != null){
				
				int dateComparison = 0;
				dateComparison = o1.getTime().compareTo(o2.getTime());
				// 0 is a special case.
				if(dateComparison == 0){
					
					if(o1.getLat() == o2.getLat() && o1.getLon() == o2.getLon()){
						//the two objects regarded as equal.
						comparison = 0;
					}else{
						//if lat & lon equality mismatch fall back to date comparison
						comparison = dateComparison;
					}
				}else{
					
					return dateComparison;
				}
			}
			
			return comparison;
		}
		
	}
	
	
	
	
}
