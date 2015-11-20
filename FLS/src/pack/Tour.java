package pack;

import java.util.ArrayList;

public class Tour {

	private int duration = 0;
	private int maxDuration = 480;
	private ArrayList<Location> tourStops = new ArrayList<>();

	public Tour() {
		tourStops.add(Main.getDepot());
	}

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

	public boolean addStop(Location location, ArrayList<Location> locations) {
		if (duration + (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F
				* Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration() < maxDuration) {
			duration += (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F
					* Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration();
			tourStops.add(location);
			locations.remove(Main.getIndex(locations,location));
//			System.out.println("remove --> Location: " + location.getLong() + " : " + location.getLat());
//			System.out.println("locations.size (after.remove): " + Main.getLocations().size());
			Main.setLastLocation(location);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Tactics here: ....
	 * @param locations 
	 * 
	 * @return
	 */

	public boolean addNextStopPizza(ArrayList<Location> locations) {
		if (!locations.isEmpty()) {
//			System.out.println("toruStops.size: " + tourStops.size());
			if (!Main.isUsed() && tourStops.size() == 1) {

				Location loc = Main.findClosestLocation(tourStops.get(tourStops.size() -1), locations);
				Main.setAngleTourStop1(loc.getAngle());
				return addStop(loc,locations);

			} else if (!Main.isUsed() && tourStops.size() == 2) {

				Location loc2 = Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations);
				Main.setAngleTourStop2(loc2.getAngle());
				Main.generateAngleToLocation(Main.getTourStop2()); //wichtig! für die winkelfunktion ---> ordnet die winkel neu!
				Main.setUsed(true); // if true: benutzt die beiden ifups nur ein einziges mal
				return addStop(loc2,locations);

			} else {
				
				Location loc = Main.getLocationWithSmalestAngle(tourStops.get(tourStops.size() - 1),locations);
//				System.out.println("Location: to tourStops " + loc.getLong() + " : " + loc.getLat());
				return addStop(loc,locations);

			}
		} else {
			return false;
		}
	}

	public boolean addNextStop(ArrayList<Location> locations) {
		return locations.isEmpty() ? false
				: addStop(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations),locations);

	}

	public boolean addNextStopCircle(ArrayList<Location> locations) {
		if (Main.getLastLocation() == null) {
			if (locations.isEmpty()) {
				return false;
			} else {
				return addStop(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations),locations);
			}
		} else {
			if (tourStops.size() == 1) {
				if (locations.isEmpty()) {
					return false;
				} else {
					return addStop(Main.findClosestLocation(
							Main.findClosestLocation(Main.getLastLocation(), locations),
							locations),locations);
				}
			} else {
				if (locations.isEmpty()) {
					return false;
				} else {
					return addStop(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations),locations);
				}
			}
		}
	}

	public boolean addNextStopRandom(ArrayList<Location> locations) {
	    int rnd = (int)(Math.random()*locations.size());
	    return locations.isEmpty() ? false
			: addStop(locations.get(rnd),locations);

	}
}
