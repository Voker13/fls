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
	
	public boolean addStopSliceDepot(Location location, ArrayList<Location> locationsSlice,
			ArrayList<Location> locationsAll) {
		if (duration + Main.getDuration(location, tourStops.get(tourStops.size() - 1))
				+ Main.getDuration(Main.depot, tourStops.get(tourStops.size() - 1))
				+ location.visitDuration < maxDuration) {
			if (tourStops.size() == 1) {
				this.addDurationFromDepot(location);
			} else {
				this.addDuration(location);
			}
			tourStops.add(location);
			locationsSlice.remove(location);
			locationsAll.remove(location);
			return true;
		}
		tourStops.add(Main.depot);
		duration += location.timeToDepot;
		return false;
	}

	public boolean addNextStopVariableSlicesFar(ArrayList<Location> locations, int slices) {
		ArrayList<Location> locationsInSlice = new ArrayList<>();
		for (int i = 0; i < slices; i++) {
			locationsInSlice = new ArrayList<>();
			for (Location location : locations) {
				double locAngle = location.angle % 360;
				if (locAngle >= i * slices && locAngle < (i + 1) * (360 / slices)) {
					locationsInSlice.add(location);
				}
			}
			if (locationsInSlice.isEmpty()) {

			} else {
				return locations.isEmpty() ? false
						: tourStops.size() == 1
								? addStopSliceDepot(Main.findFarthestLocation(Main.depot, locationsInSlice),
										locationsInSlice, locations)
								: addStopSliceDepot(
										Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locationsInSlice),
										locationsInSlice, locations);
			}
		}
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
		this.duration += Main.getDuration(tourStops.get(tourStops.size() - 1), location) + location.visitDuration;
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
