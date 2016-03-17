package pack;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static ArrayList<Location> nodes;
	public static Location depot;
	public static int speed = 0;
	public static int solution = 0;

	public static void main(String[] args) throws IOException {

		String file = null;

		System.out.println("INSTANCE txt");

		file = readFromSystemIn();

		nodes = (ArrayList<Location>) Location.readInstance(file);

		setDepot();

		calculateSpeed();

		generateAngleToLocation();

		variableSliceFarStrategy(12);
	}

	private static String readFromSystemIn() throws IOException {
		String response = "";
		int nextByte = System.in.read();
		while (nextByte != -1 && nextByte != '\n') {
			response += (char) nextByte;
			nextByte = System.in.read();
		}
		return response;
	}

	private static void setDepot() {
		Location remove = null;
		for (Location location : nodes) {
			if (location.isDepto()) {
				depot = location;
				remove = location;
			}
		}
		nodes.remove(remove);
	}

	public static int getIndex(ArrayList<Location> locations, Location location) {
		for (int i = 0; i < locations.size(); i++) {
			if (locations.get(i) == location) {
				return i;
			} else {

			}
		}
		return 0;
	}

	public static double getDistance(Location location1, Location location2) {
		double lat1 = location1.latitude;
		double long1 = location1.longitude;
		double lat2 = location2.latitude;
		double long2 = location2.longitude;
		double lat = (lat1 + lat2) / 2 * 0.01745;
		float latDifference = (float) (111.3 * (lat1 - lat2));
		float longDifference = (float) (111.3 * Math.cos(lat) * (long1 - long2));
		return Math.sqrt(latDifference * latDifference + longDifference * longDifference);
	}

	public static int getDuration(Location location1, Location location2) {
		double distance = getDistance(location1, location2);
		int duration = (int) ((distance / speed) * 60);
		return duration;
	}

	public static void generateAngleToLocation() {
		double x0 = depot.longitude;
		double y0 = depot.latitude;
		for (Location location : nodes) {
			double dx = x0 - location.longitude;
			double dy = y0 - location.latitude;
			if (dx >= 0 && dy >= 0) {
				location.angle = (Math.toDegrees(Math.atan(dy / dx)));
			} else if (dx < 0 && dy >= 0) {
				location.angle = (Math.toDegrees(Math.atan(dy / dx)) + 180);
			} else if (dx < 0 && dy < 0) {
				location.angle = (Math.toDegrees(Math.atan(dy / dx)) + 180);
			} else if (dx >= 0 && dy < 0) {
				location.angle = (Math.toDegrees(Math.atan(dy / dx)) + 360);
			}
		}
	}

	private static void calculateSpeed() {
		int distance = 0;
		int duration = 0;
		for (int i = 0; i < nodes.size(); i++) {
			distance += getDistance(depot, nodes.get(i));
			duration += ((nodes.get(i).timeToDepot + nodes.get(i).timeFromDepot) / 2);
		}
		speed = distance / (duration / 60);
	}

	@SuppressWarnings("unchecked")
	private static void variableSliceFarStrategy(int slices) {

		ArrayList<Tour> allToursSliceVariableFar = new ArrayList<>();

		ArrayList<Location> workCopy = (ArrayList<Location>) nodes.clone();

		workCopy = (ArrayList<Location>) nodes.clone();
		while (!workCopy.isEmpty()) {
			allToursSliceVariableFar.add(findWorkDayVariableSlicesFar(workCopy, slices));
		}

		int durationOverallSliceVariableFar = 0;
		for (Tour tour : allToursSliceVariableFar) {
			durationOverallSliceVariableFar += tour.getDuration();
		}

		postSolution(durationOverallSliceVariableFar);
	}

	private static void postSolution(int s) {
		solution = s;
		System.out.println("SOLUTION " + solution);
	}

	private static Tour findWorkDayVariableSlicesFar(ArrayList<Location> locations, int slices) {
		Tour tour = new Tour();
		while (tour.addNextStopVariableSlicesFar(locations, slices)) {

		}
		if (locations.isEmpty()) {
			tour.addDepot();
		}

		return tour;
	}

	public static Location findClosestLocation(Location location, ArrayList<Location> locations) {
		Location returnLocation = locations.get(0);
		for (int i = 1; i < locations.size(); i++) {
			if ((getDistance(location, locations.get(i)) < getDistance(returnLocation, location))) {
				returnLocation = locations.get(i);
			}
		}
		return returnLocation;
	}

	public static Location findFarthestLocation(Location location, ArrayList<Location> locations) {
		Location returnLocation = locations.get(0);
		for (int i = 1; i < locations.size(); i++) {
			if ((getDistance(location, locations.get(i)) > getDistance(returnLocation, location))) {
				returnLocation = locations.get(i);
			}
		}
		return returnLocation;
	}

}
