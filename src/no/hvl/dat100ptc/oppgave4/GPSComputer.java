package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		// TODO - START

		for(int i = 0; i < (gpspoints.length-1); i++) {
			distance = distance + GPSUtils.distance(gpspoints[i], gpspoints[i+1]);
		}
		// TODO - SLUTT
		return distance;
	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		// TODO - START

		for(int i = 1; i < (gpspoints.length); i++) {
			if(gpspoints[i].getElevation() > gpspoints[i-1].getElevation()) {
				elevation = elevation + gpspoints[i].getElevation() - gpspoints[i - 1].getElevation();
			}
		}
		// TODO - SLUTT
		return elevation;
	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		int totalTime = 0;
		int startTime = gpspoints[0].getTime();
		int endTime = gpspoints[gpspoints.length - 1].getTime();
		totalTime = endTime - startTime;
		
		return totalTime;

	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		// TODO - START		// OPPGAVE - START
		
		double[] avgSpeeds = new double[gpspoints.length - 1];
		
		for(int i = 0; i < gpspoints.length - 1; i++) {
			avgSpeeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}

		// TODO - SLUTT
		return avgSpeeds;
	}
	
	public double maxSpeed() {
		
		// TODO - SLUTT
		return GPSUtils.findMax(speeds());
	}

	public double averageSpeed() {

		double average = 0;
		
		// TODO - START
		average = (totalDistance() / totalTime()) * 3.6;
		
		// TODO - SLUTT
		return average;
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal = 0;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;
		double h = 3600.0;

		// TODO - START
		if(speedmph < 10) {
			met = 4.0;
		}
		else if (speedmph > 10 && speedmph < 12) {
			met = 6.0;
		}
		else if(speedmph > 12 && speedmph < 14) {
			met = 8.0;
		}
		else if(speedmph > 14 && speedmph < 16) {
			met = 10.0;
		}
		else if(speedmph > 16 && speedmph < 20) {
			met = 12.0;
		}
		else if(speedmph > 20) {
			met = 16.0;
		}
		kcal = met * weight * (secs/h);
		// TODO - SLUTT
		return kcal;
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		// TODO - START
		
		double[] totalSpeeds = speeds();
		
		for(int i=0; i < gpspoints.length - 1; i++) {
			totalkcal = totalkcal + kcal(weight, gpspoints[i +1].getTime() - gpspoints[i].getTime(), totalSpeeds[i]);
		}
		// TODO - SLUTT
		return totalkcal;
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");

		// TODO - START
		System.out.print("Total time");
		System.out.printf("%6s",":");
		System.out.printf("%11s %n", GPSUtils.formatTime(totalTime()));
		
		System.out.print("Total distance");
		System.out.printf("%2s", ":");
		System.out.printf("%11s", GPSUtils.formatDouble(totalDistance()/1000));
		System.out.printf("%3s %n", "km");
		
		System.out.print("Total elevation");
		System.out.printf("%1s", ":");
		System.out.printf("%11s", GPSUtils.formatDouble(totalElevation()));
		System.out.printf("%2s %n", "m");
		
		System.out.print("Max speed");
		System.out.printf("%7s", ":");
		System.out.printf("%11s", GPSUtils.formatDouble(maxSpeed()));
		System.out.printf("%5s %n", "km/t");
		
		System.out.print("Average speed");
		System.out.printf("%3s", ":");
		System.out.printf("%11s", GPSUtils.formatDouble(averageSpeed()));
		System.out.printf("%5s %n", "km/t");
		
		System.out.print("Energy");
		System.out.printf("%10s", ":");
		System.out.printf("%11s", GPSUtils.formatDouble(totalKcal(WEIGHT)));
		System.out.printf("%5s %n", "kcal");

		// TODO - SLUTT
		System.out.println("==============================================");
	}

}
