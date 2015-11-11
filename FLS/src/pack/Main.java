package pack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Main {

	static int timePerWorkday = 480;
	static ArrayList<Location> locations;
	static ArrayList<Edge> edges;
	static ArrayList<Tour> allTours;
	static double groundAirQuotient;
	static double kilometerPerHour;
	static double meterPerSecond;
	static float minLat = 1000;
	static float maxLat = 0;
	static float minLong = 1000;
	static float maxLong = 0;
	static Location depot;
	static Location lastLocation = null;

	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		JAXBContext jc = JAXBContext.newInstance(Instance.class);

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		File xml = new File("Instance-400.xml");
		Instance instance = (Instance) unmarshaller.unmarshal(xml);

		long startTime = System.currentTimeMillis();

		depot = instance.getLocations().get(0);

		locations = (ArrayList<Location>) instance.getLocations();
		locations.remove(0);

		edges = (ArrayList<Edge>) instance.getEdges();

		allTours = new ArrayList<Tour>();

		int distanceAir = 0;
		int distanceGround = 0;
		for (int i = 0; i < locations.size(); i++) {
			if (!(i == 0)) {
				distanceGround += getEdge(0, i).distance / 1000;
				distanceAir += getDistance(depot, locations.get(i));
			}
			if (locations.get(i).getLat() < minLat) {
				minLat = locations.get(i).getLat();
			}
			if (locations.get(i).getLat() > maxLat) {
				maxLat = locations.get(i).getLat();
			}
			float LLong = locations.get(i).getLong();
			while (LLong > 20) {
				LLong /= 10;
			}
			if (LLong < minLong) {
				minLong = LLong;
			}
			if (LLong > maxLong) {
				maxLong = LLong;
			}
		}
		groundAirQuotient = (float) distanceGround / distanceAir;

		System.out.println(maxLat - minLat + " " + (maxLong - minLong));

		distanceAir = 0;
		distanceGround = 0;
		for (int i = 0; i < locations.size(); i++) {
			if (!(i == 0)) {
				distanceGround += getEdge(0, i).distance / 1000;
				distanceAir += getDistance(depot, locations.get(i)) * groundAirQuotient;
			}
		}
		System.out.println(distanceAir + " " + distanceGround);

		int time = 0;
		for (int i = 0; i < locations.size(); i++) {
			if (!(i == 0)) {
				time += getEdge(0, i).getDuration();
			}
		}

		kilometerPerHour = (distanceGround * 60) / (time);
		meterPerSecond = kilometerPerHour / 3.6F;

		while (!locations.isEmpty()) {
			// allTours.add(findWorkDay());
			allTours.add(findWorkDayCircle());
		}

		int durationOverall = 0;
		for (Tour tour : allTours) {
			durationOverall += tour.getDuration();
		}

		System.out.println(allTours.size() + " Touren mit einer Gesamtfahrzeit von " + durationOverall + " Minuten");
		long endTime = System.currentTimeMillis();
		System.out.println("Elapsed Time: " + (endTime - startTime) + "ms");
		GraphFrame gf = new GraphFrame();
		gf.repaint();
	}

	private static Tour findWorkDay() {
		Tour tour = new Tour();
		while (tour.addNextStop()) {

		}
		System.out.println(tour.getDuration() + " " + tour.getTourStops());
		return tour;
	}

	/*
	 * The solution needs even longer than findWorkDay()
	 * 
	 */
	private static Tour findWorkDayCircle() {
		Tour tour = new Tour();
		while (tour.addNextStopCircle()) {

		}
		System.out.println(tour.getDuration() + " " + tour.getTourStops());
		return tour;
	}

	public static Location findClosestLocation(Location location, ArrayList<Location> locations) {
		long startTime = System.currentTimeMillis();
		Location returnLocation = locations.get(0);
		for (int i = 1; i < locations.size(); i++) {
			if ((getDistance(location, locations.get(i)) < getDistance(returnLocation, location))) {
				returnLocation = locations.get(i);
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println("Elapsed Time for finding closest Place: " + (endTime - startTime) + "ms");
		return returnLocation;
	}

	public static float getDistance(Location location1, Location location2) {
		float lat1 = location1.getLat();
		float long1 = location1.getLong() / 10;
		while (long1 > 20) {
			long1 /= 10;
		}
		float lat2 = location2.getLat();
		float long2 = location2.getLong() / 10;
		while (long2 > 20) {
			long2 /= 10;
		}
		double lat = (lat1 + lat2) / 2 * 0.01745;
		float latDifference = (float) (111.3 * (lat1 - lat2));
		float longDifference = (float) (111.3 * Math.cos(lat) * (long1 - long2));
		return (float) Math.sqrt(latDifference * latDifference + longDifference * longDifference);
	}

	private static Edge getEdge(int one, int two) {
		for (Edge edge : edges) {
			if ((edge.from == one && edge.to == two) || (edge.from == two && edge.to == one)) {
				return edge;
			}
		}
		return null;
	}

	public static int getIndex(Location location) {
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i).equals(location)) {
				return i;
			}
		}
		return -1;
	}
}
