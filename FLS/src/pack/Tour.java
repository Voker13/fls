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
	if (duration + (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration() < maxDuration) {
	    duration += (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration();
	    tourStops.add(location);
	    locations.remove(Main.getIndex(locations, location));
	    // System.out.println("remove --> Location: " + location.getLong() +
	    // " : " + location.getLat());
	    // System.out.println("locations.size (after.remove): " +
	    // Main.getLocations().size());
	    Main.setLastLocation(location);
	    return true;
	}
	return false;
    }

    public boolean addStopDepot(Location location, ArrayList<Location> locations) {
	if (duration + (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60
		+ (Main.getDistance(Main.getDepot(), tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration() < maxDuration) {
	    duration += (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration();
	    tourStops.add(location);
	    locations.remove(Main.getIndex(locations, location));
	    // System.out.println("remove --> Location: " + location.getLong() +
	    // " : " + location.getLat());
	    // System.out.println("locations.size (after.remove): " +
	    // Main.getLocations().size());
	    Main.setLastLocation(location);
	    return true;
	}
	tourStops.add(Main.getDepot());
	duration += (Main.getDistance(Main.getDepot(), tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60;
	return false;
    }

    public void addDepot() {
	duration += (Main.getDistance(Main.getDepot(), tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60;
	tourStops.add(Main.getDepot());
    }

    public boolean addStopSlice(Location location, ArrayList<Location> locationsSlice, ArrayList<Location> locationsAll) {
	if (duration + (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration() < maxDuration) {
	    duration += (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration();
	    tourStops.add(location);
	    locationsSlice.remove(Main.getIndex(locationsSlice, location));
	    locationsAll.remove(Main.getIndex(locationsAll, location));
	    return true;
	}
	return false;
    }

    public boolean addStopSliceDepot(Location location, ArrayList<Location> locationsSlice, ArrayList<Location> locationsAll) {
	if ((duration + (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60
		+ (Main.getDistance(Main.getDepot(), location) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration()) < maxDuration) {
	    duration += (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration();
	    tourStops.add(location);
	    locationsSlice.remove(Main.getIndex(locationsSlice, location));
	    locationsAll.remove(Main.getIndex(locationsAll, location));
	    return true;
	}
	duration += (Main.getDistance(Main.getDepot(), tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60;
	tourStops.add(Main.getDepot());
	return false;
    }

    /**
     * 
     * Tactics here: ....
     * 
     * @param locations
     * 
     * @return
     */

    public boolean addNextStopPizza(ArrayList<Location> locations) {
	if (!locations.isEmpty()) {
	    // System.out.println("toruStops.size: " + tourStops.size());
	    if (!Main.isUsed() && tourStops.size() == 1) {

		Location loc = Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations);
		Main.setAngleTourStop1(loc.getAngle());
		return addStop(loc, locations);

	    } else if (!Main.isUsed() && tourStops.size() == 2) {

		Location loc2 = Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations);
		Main.setAngleTourStop2(loc2.getAngle());
		Main.generateAngleToLocation(Main.getTourStop2()); // wichtig!
		// Main.setUsed(true); // if true: benutzt die beiden ifups nur
		// ein einziges mal
		return addStop(loc2, locations);

	    } else {

		Location loc = Main.getLocationWithSmalestAngle(tourStops.get(tourStops.size() - 1), locations);
		// System.out.println("Location: to tourStops " + loc.getLong()
		// + " : " + loc.getLat());
		return addStop(loc, locations);

	    }
	} else {
	    return false;
	}
    }

    public boolean addNextStop(ArrayList<Location> locations) {
	return locations.isEmpty() ? false : addStopDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations), locations);

    }

    public boolean addNextStopCircle(ArrayList<Location> locations) {
	if (Main.getLastLocation() == null) {
	    if (locations.isEmpty()) {
		return false;
	    } else {
		return addStopDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations), locations);
	    }
	} else {
	    if (tourStops.size() == 1) {
		if (locations.isEmpty()) {
		    return false;
		} else {
		    return addStopDepot(Main.findClosestLocation(Main.findClosestLocation(Main.getLastLocation(), locations), locations), locations);
		}
	    } else {
		if (locations.isEmpty()) {
		    return false;
		} else {
		    return addStopDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations), locations);
		}
	    }
	}
    }

    public boolean addNextStopRandom(ArrayList<Location> locations) {
	int rnd = (int) (Math.random() * locations.size());
	return locations.isEmpty() ? false : addStopDepot(locations.get(rnd), locations);

    }

    public boolean addNextStopSlices(ArrayList<Location> locations) {
	ArrayList<Location> slice1 = new ArrayList<>();
	ArrayList<Location> slice2 = new ArrayList<>();
	ArrayList<Location> slice3 = new ArrayList<>();
	ArrayList<Location> slice4 = new ArrayList<>();
	ArrayList<Location> slice5 = new ArrayList<>();
	ArrayList<Location> slice6 = new ArrayList<>();
	ArrayList<Location> slice7 = new ArrayList<>();
	ArrayList<Location> slice8 = new ArrayList<>();
	ArrayList<Location> slice9 = new ArrayList<>();
	ArrayList<Location> slice10 = new ArrayList<>();
	for (Location location : locations) {
	    if (location.getAngle() % 360 >= 0 && location.getAngle() % 360 < 36) {
		slice1.add(location);
	    }
	    if (location.getAngle() % 360 >= 36 && location.getAngle() % 360 < 72) {
		slice2.add(location);
	    }
	    if (location.getAngle() % 360 >= 72 && location.getAngle() % 360 < 108) {
		slice3.add(location);
	    }
	    if (location.getAngle() % 360 >= 108 && location.getAngle() % 360 < 144) {
		slice4.add(location);
	    }
	    if (location.getAngle() % 360 >= 144 && location.getAngle() % 360 < 180) {
		slice5.add(location);
	    }
	    if (location.getAngle() % 360 >= 180 && location.getAngle() % 360 < 216) {
		slice6.add(location);
	    }
	    if (location.getAngle() % 360 >= 216 && location.getAngle() % 360 < 252) {
		slice7.add(location);
	    }
	    if (location.getAngle() % 360 >= 252 && location.getAngle() % 360 < 288) {
		slice8.add(location);
	    }
	    if (location.getAngle() % 360 >= 288 && location.getAngle() % 360 < 324) {
		slice9.add(location);
	    }
	    if (location.getAngle() % 360 >= 324 && location.getAngle() % 360 < 360) {
		slice10.add(location);
	    }
	}
	// System.out.println(slice1.size()+slice2.size()+slice3.size()+slice4.size()+slice5.size()+slice6.size()+slice7.size()+slice8.size()+slice9.size()+slice10.size());
	if (!slice1.isEmpty()) {
	    return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice1), slice1, locations);
	} else {
	    if (!slice2.isEmpty()) {
		return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice2), slice2, locations);
	    } else {
		if (!slice3.isEmpty()) {
		    return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice3), slice3, locations);
		} else {
		    if (!slice4.isEmpty()) {
			return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice4), slice4, locations);
		    } else {
			if (!slice5.isEmpty()) {
			    return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice5), slice5, locations);
			} else {
			    if (!slice6.isEmpty()) {
				return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice6), slice6, locations);
			    } else {
				if (!slice7.isEmpty()) {
				    return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice7), slice7, locations);
				} else {
				    if (!slice8.isEmpty()) {
					return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice8), slice8, locations);
				    } else {
					if (!slice9.isEmpty()) {
					    return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice9), slice9, locations);
					} else {
					    if (!slice10.isEmpty()) {
						return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice10), slice10, locations);
					    } else {
						return false;
					    }
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	}
    }

    public boolean addNextStopFarToClose(ArrayList<Location> locations) {
	return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopDepot(Main.findFarthestLocation(tourStops.get(0), locations), locations) : addStopDepot(
		Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations), locations);
	// return locations.isEmpty() ? false : tourStops.size()==1 ?
	// addStop(Main.findFarthestLocation(tourStops.get(0),
	// locations),locations) :
	// addStop(Main.findClosestLocationGoingToTheMiddle(tourStops.get(tourStops.size()-1),locations),
	// locations);
    }

    public boolean addNextStopSlicePlusFar(ArrayList<Location> locations) {
	ArrayList<Location> slice1 = new ArrayList<>();
	ArrayList<Location> slice2 = new ArrayList<>();
	ArrayList<Location> slice3 = new ArrayList<>();
	ArrayList<Location> slice4 = new ArrayList<>();
	ArrayList<Location> slice5 = new ArrayList<>();
	ArrayList<Location> slice6 = new ArrayList<>();
	ArrayList<Location> slice7 = new ArrayList<>();
	ArrayList<Location> slice8 = new ArrayList<>();
	ArrayList<Location> slice9 = new ArrayList<>();
	ArrayList<Location> slice10 = new ArrayList<>();
	for (Location location : locations) {
	    if (location.getAngle() % 360 >= 0 && location.getAngle() % 360 < 36) {
		slice1.add(location);
	    }
	    if (location.getAngle() % 360 >= 36 && location.getAngle() % 360 < 72) {
		slice2.add(location);
	    }
	    if (location.getAngle() % 360 >= 72 && location.getAngle() % 360 < 108) {
		slice3.add(location);
	    }
	    if (location.getAngle() % 360 >= 108 && location.getAngle() % 360 < 144) {
		slice4.add(location);
	    }
	    if (location.getAngle() % 360 >= 144 && location.getAngle() % 360 < 180) {
		slice5.add(location);
	    }
	    if (location.getAngle() % 360 >= 180 && location.getAngle() % 360 < 216) {
		slice6.add(location);
	    }
	    if (location.getAngle() % 360 >= 216 && location.getAngle() % 360 < 252) {
		slice7.add(location);
	    }
	    if (location.getAngle() % 360 >= 252 && location.getAngle() % 360 < 288) {
		slice8.add(location);
	    }
	    if (location.getAngle() % 360 >= 288 && location.getAngle() % 360 < 324) {
		slice9.add(location);
	    }
	    if (location.getAngle() % 360 >= 324 && location.getAngle() % 360 < 360) {
		slice10.add(location);
	    }
	}
	// System.out.println(slice1.size()+slice2.size()+slice3.size()+slice4.size()+slice5.size()+slice6.size()+slice7.size()+slice8.size()+slice9.size()+slice10.size());
	if (!slice1.isEmpty()) {
	    return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopSlice(Main.findFarthestLocation(tourStops.get(0), slice1), slice1, locations) : addStopSlice(
		    Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice1), slice1, locations);
	} else {
	    if (!slice2.isEmpty()) {
		return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopSlice(Main.findFarthestLocation(tourStops.get(0), slice2), slice2, locations) : addStopSlice(
			Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice2), slice2, locations);
	    } else {
		if (!slice3.isEmpty()) {
		    return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopSlice(Main.findFarthestLocation(tourStops.get(0), slice3), slice3, locations) : addStopSlice(
			    Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice3), slice3, locations);
		} else {
		    if (!slice4.isEmpty()) {
			return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopSlice(Main.findFarthestLocation(tourStops.get(0), slice4), slice4, locations) : addStopSlice(
				Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice4), slice4, locations);
		    } else {
			if (!slice5.isEmpty()) {
			    return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopSlice(Main.findFarthestLocation(tourStops.get(0), slice5), slice5, locations) : addStopSlice(
				    Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice5), slice5, locations);
			} else {
			    if (!slice6.isEmpty()) {
				return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopSlice(Main.findFarthestLocation(tourStops.get(0), slice6), slice6, locations) : addStopSlice(
					Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice6), slice6, locations);
			    } else {
				if (!slice7.isEmpty()) {
				    return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopSlice(Main.findFarthestLocation(tourStops.get(0), slice7), slice7, locations) : addStopSlice(
					    Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice7), slice7, locations);
				} else {
				    if (!slice8.isEmpty()) {
					return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopSlice(Main.findFarthestLocation(tourStops.get(0), slice8), slice8, locations)
						: addStopSlice(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice8), slice8, locations);
				    } else {
					if (!slice9.isEmpty()) {
					    return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopSlice(Main.findFarthestLocation(tourStops.get(0), slice9), slice9, locations)
						    : addStopSlice(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice9), slice9, locations);
					} else {
					    if (!slice10.isEmpty()) {
						return locations.isEmpty() ? false : tourStops.size() == 1 ? addStopSlice(Main.findFarthestLocation(tourStops.get(0), slice10), slice10, locations)
							: addStopSlice(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice10), slice10, locations);
					    } else {
						return false;
					    }
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	}
    }
}
