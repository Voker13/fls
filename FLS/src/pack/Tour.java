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

    public int getDuration() {
	return duration;
    }

    public void setDuration(int duration) {
	this.duration = duration;
    }

    public void addDuration(Location location) {
	this.duration += Main.getDuration(tourStops.get(tourStops.size() - 1), location) + location.visitDuration;
    }
    
    public void addDurationFromDepot(Location location) {
	this.duration += location.timeFromDepot + location.visitDuration;
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

    public boolean addStopDepot(Location location, ArrayList<Location> locations) {
	if (duration + Main.getDuration(location, tourStops.get(tourStops.size() - 1)) + Main.getDuration(Main.depot, tourStops.get(tourStops.size() - 1)) + location.visitDuration < maxDuration) {
	    this.addDuration(location);
	    tourStops.add(location);
	    locations.remove(Main.getIndex(locations, location));
	    return true;
	}
	tourStops.add(Main.depot);
	duration += location.timeToDepot;
	return false;
    }

    public void addDepot() {
	duration += tourStops.get(tourStops.size() - 1).timeToDepot;
	tourStops.add(Main.depot);
    }

    public boolean addStopSliceDepot(Location location, ArrayList<Location> locationsSlice, ArrayList<Location> locationsAll) {
	if (duration + Main.getDuration(location, tourStops.get(tourStops.size() - 1)) + Main.getDuration(Main.depot, tourStops.get(tourStops.size() - 1)) + location.visitDuration < maxDuration) {
	    if (tourStops.size() == 1) {
		this.addDurationFromDepot(location);
		tourStops.add(location);
		locationsSlice.remove(Main.getIndex(locationsSlice, location));
		locationsAll.remove(Main.getIndex(locationsAll, location));
		return true;
	    } else {
		this.addDuration(location);
		tourStops.add(location);
		locationsSlice.remove(Main.getIndex(locationsSlice, location));
		locationsAll.remove(Main.getIndex(locationsAll, location));
		return true;
	    }
	}
	tourStops.add(Main.depot);
	duration += location.timeToDepot;
	return false;
    }

    public boolean addNextStop(ArrayList<Location> locations) {
	return locations.isEmpty() ? false : addStopDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations), locations);

    }

    public boolean addNextStopVariableSlices(ArrayList<Location> locations, int slices) {
	ArrayList<Location> locationsInSlice = new ArrayList<>();
	for (int i = 0; i < slices; i++) {
	    locationsInSlice = new ArrayList<>();
	    for (Location location : locations) {
		if (location.angle % 360 >= i * slices && location.angle % 360 < (i + 1) * (360 / slices)) {
		    locationsInSlice.add(location);
		}
	    }
	    if (locationsInSlice.isEmpty()) {

	    } else {
		return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locationsInSlice), locationsInSlice, locations);
	    }
	}
	return false;
    }

    public boolean addNextStopVariableSlicesFar(ArrayList<Location> locations, int slices) {
	ArrayList<Location> locationsInSlice = new ArrayList<>();
	for (int i = 0; i < slices; i++) {
	    locationsInSlice = new ArrayList<>();
	    for (Location location : locations) {
		if (location.angle % 360 >= i * slices && location.angle % 360 < (i + 1) * (360 / slices)) {
		    locationsInSlice.add(location);
		}
	    }
	    if (locationsInSlice.isEmpty()) {

	    } else {
		return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopSliceDepot(Main.findFarthestLocation(Main.depot, locationsInSlice), locationsInSlice, locations) : addStopSliceDepot(
			Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locationsInSlice), locationsInSlice, locations);
	    }
	}
	return false;
    }

    public boolean addNextStopFarToClose(ArrayList<Location> locations) {
	return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopDepot(Main.findFarthestLocation(tourStops.get(0), locations), locations) : addStopDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations), locations);
    }

    public String toString() {
	String returnString = "[";
	for (Location location : tourStops) {
	    returnString += location.address + " ";
	}
	returnString += "] " + duration + " Minuten";

	return returnString;
    }
}
