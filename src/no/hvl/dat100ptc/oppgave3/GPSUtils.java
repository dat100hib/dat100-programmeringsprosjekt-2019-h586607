package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;
		
		min = da[0];

		// TODO - START
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		// TODO - SLUTT
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		// TODO - START
		double[] latitudes = new double[gpspoints.length];
		
		for (int i = 0; i < latitudes.length; i++) {
			latitudes[i] = gpspoints[i].getLatitude();
		}
		
		return latitudes;
		
		
		// TODO - SLUTT
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		// TODO - START
		double[] longitudes = new double[gpspoints.length];
		
		for (int i = 0; i < longitudes.length; i++) {
			longitudes[i] = gpspoints[i].getLongitude();
		}
		return longitudes;

		// TODO - SLUTT

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {
		
		// TODO - START

		double latitude1 = Math.toRadians(gpspoint1.getLatitude());
		double latitude2 = Math.toRadians(gpspoint2.getLatitude());

		double latdif = Math.toRadians(gpspoint2.getLatitude() - gpspoint1.getLatitude());
		double longdif = Math.toRadians(gpspoint2.getLongitude() - gpspoint1.getLongitude());
		
		double a = 	Math.pow(Math.sin(latdif/2), 2) + 
					Math.cos(latitude1) * Math.cos(latitude2) *
					Math.pow(Math.sin(longdif/2), 2);
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		double d = R * c;
		
		// TODO - SLUTT
		return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;

		// TODO - START

		secs = (gpspoint2.getTime() - gpspoint1.getTime());
		speed = ((distance(gpspoint1, gpspoint2) / secs) * 18) / 5;

		// TODO - SLUTT
		return speed;
	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";

		// TODO - START
		
		int timer = secs / 3600;
		int rest = secs % 3600;
		int minutter = rest / 60;
		int rest2 = secs % 60;
		
		// TODO - SLUTT
		timestr =
				(String.format("%1$10s", String.format("%02d", timer) + TIMESEP + 
				String.format("%02d", minutter) + TIMESEP + String.format("%02d", rest2)));
		
		return timestr;
		
	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;

		// TODO - START
		
		double a = (double) Math.round(d*100)/100;
		
		str = String.format("%1$" + TEXTWIDTH + "s", a);
		
		// TODO - SLUTT
		return str;
		
	}
}
