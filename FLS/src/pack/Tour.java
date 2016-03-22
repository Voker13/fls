package pack;

import java.util.ArrayList;

/**
 * 
 * This class represents a Workday
 *
 */
public class Tour {

	private int duration = 0;
	private int maxDuration = 480;
	private ArrayList<Location> tourStops = new ArrayList<>();

	public Tour() {
		tourStops.add(Main.depot);
	}

	public boolean findingNextStop(ArrayList<Location> locations, int slices) {
		ArrayList<Location> locationsInSlice = new ArrayList<>();
		for (int i = 0; i < slices; i++) {
			locationsInSlice = new ArrayList<>();
			int iSlices = i * (360 / slices);
			int i1360slices = (i + 1) * (360 / slices);
			for (Location location : locations) {
				double locAngle = location.angle % 360;
				if (locAngle >= iSlices && locAngle < i1360slices) {
					locationsInSlice.add(location);
				}
			}
			if (locationsInSlice.isEmpty()) {

			} else {
				return locations.isEmpty() ? false
						: tourStops.size() == 1
								? addStopFirstLocation(Main.findFarthestLocation(Main.depot, locationsInSlice),
										locationsInSlice, locations)
								: addStop(
										Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locationsInSlice),
										locationsInSlice, locations);
			}
		}
		return false;
	}
	
	public boolean addStop(Location location, ArrayList<Location> locationsSlice, ArrayList<Location> locationsAll) {
		if (duration + location.durationToBacker + tourStops.get(tourStops.size() - 1).timeToDepot + location.visitDuration < maxDuration) {
			this.addDuration(location);
			tourStops.add(location);
			locationsSlice.remove(location);
			locationsAll.remove(location);
			return true;
		}
		tourStops.add(Main.depot);
		duration += location.timeToDepot;
		return false;
	}
	
	public boolean addStopFirstLocation(Location location, ArrayList<Location> locationsSlice, ArrayList<Location> locationsAll) {
//	    System.out.println(location.durationToBacker);
		if (duration + location.durationToBacker + tourStops.get(tourStops.size() - 1).timeToDepot + location.visitDuration < maxDuration) {
			this.addDurationFromDepot(location);
			tourStops.add(location);
			locationsSlice.remove(location);
			locationsAll.remove(location);
			return true;
		}
		tourStops.add(Main.depot);
		duration += location.timeToDepot;
		locationsSlice.remove(location);
		locationsAll.remove(location);
		return false;
	}

	public String toString() {
		String returnString = "[";
		for (Location location : tourStops) {
			returnString += location.address + " ";
		}
		returnString += "] " + duration + " Minuten";

		return returnString;
	}
	
	public void addDuration(Location location) {
		this.duration += location.durationToBacker + location.visitDuration;
	}

	public void addDurationFromDepot(Location location) {
		this.duration += location.timeFromDepot + location.visitDuration;
	}
	
	public void addDepot() {
		duration += tourStops.get(tourStops.size() - 1).timeToDepot;
		tourStops.add(Main.depot);
	}

	// Getter and Setter --->>>
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(int maxDuration) {
		this.maxDuration = maxDuration;
	}

	public ArrayList<Location> getTourStops() {
		return tourStops;
	}

	public void setTourStops(ArrayList<Location> tourStops) {
		this.tourStops = tourStops;
	}
	
}
