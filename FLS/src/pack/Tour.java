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

	public boolean addStop(Location location) {
		if (duration + (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F
				* Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration() < maxDuration) {
			duration += (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F
					* Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration();
			tourStops.add(location);
			Main.getLocations().remove(Main.getIndex(location));
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
	 * 
	 * @return
	 */

	public boolean addNextStopPizza() {
		if (!Main.getLocations().isEmpty()) {
//			System.out.println("toruStops.size: " + tourStops.size());
			if (!Main.isUsed() && tourStops.size() == 1) {

				Location loc = Main.findClosestLocation(tourStops.get(tourStops.size() -1), Main.getLocations());
				Main.setAngleTourStop1(loc.getAngle());
				return addStop(loc);

			} else if (!Main.isUsed() && tourStops.size() == 2) {

				Location loc2 = Main.findClosestLocation(tourStops.get(tourStops.size() - 1), Main.getLocations());
				Main.setAngleTourStop2(loc2.getAngle());
				Main.generateAngleToLocation(Main.getTourStop2()); //wichtig! für die winkelfunktion ---> ordnet die winkel neu!
				Main.setUsed(true); // if true: benutzt die beiden ifups nur ein einziges mal
				return addStop(loc2);

			} else {
				
				Location loc = Main.getLocationWithSmalestAngle(tourStops.get(tourStops.size() - 1),Main.getLocations());
//				System.out.println("Location: to tourStops " + loc.getLong() + " : " + loc.getLat());
				return addStop(loc);

			}
		} else {
			return false;
		}
	}

	public boolean addNextStop() {
		return Main.getLocations().isEmpty() ? false
				: addStop(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), Main.getLocations()));

	}

	public boolean addNextStopCircle() {
		if (Main.getLastLocation() == null) {
			if (Main.getLocations().isEmpty()) {
				return false;
			} else {
				return addStop(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), Main.getLocations()));
			}
		} else {
			if (tourStops.size() == 1) {
				if (Main.getLocations().isEmpty()) {
					return false;
				} else {
					return addStop(Main.findClosestLocation(
							Main.findClosestLocation(Main.getLastLocation(), Main.getLocations()),
							Main.getLocations()));
				}
			} else {
				if (Main.getLocations().isEmpty()) {
					return false;
				} else {
					return addStop(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), Main.getLocations()));
				}
			}
		}
	}
}
