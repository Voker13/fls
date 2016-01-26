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
	private int interlaceAt; //interlaceAt is the position in the tour, witch should become the new FirstStop of the tour
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
	
	public void addDuration(Location location) {
		this.duration += (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration();
	}

	public int getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(int maxDuration) {
		this.maxDuration = maxDuration;
	}

	public int getInterlaceAt() {
		return interlaceAt;
	}

	public void setInterlaceAt(int interlaceAt) {
		this.interlaceAt = interlaceAt;
	}

	public ArrayList<Location> getTourStops() {
		return tourStops;
	}

	public void setTourStops(ArrayList<Location> tourStops) {
		this.tourStops = tourStops;
	}
	
	/**
     * A method to prove if there is a interlace in a Tour, causing unnecessary extra time.
     * Sets the interlaceAt of this.tour.
     * Prints the place of the Tour, where the interlace is.
     * 
     * @return returns true if there is a interlace, else false
     */
    public boolean interlace() {
    	if (this.getTourStops().size() <= 2) {
    		System.err.println("interlace: tour.getTourStops().size() <= 2 !");
    		return false;
    	}
    	double angleOfFirstStop = this.getTourStops().get(1).getAngle();
    	if (angleOfFirstStop < this.getTourStops().get(2).getAngle()) {
    		for (int i=2; i<this.getTourStops().size(); i++) {
    			if (!this.getTourStops().get(i).equals(Main.getDepot()) && this.getTourStops().get(i).getAngle() < angleOfFirstStop) {
//    				System.out.println("interlace from position "+(i-1)+" to "+i);
//    				System.out.println("The new Startlocation of this Tour should be @"+(i-1));
    				this.setInterlaceAt(i-1);
    				return true;
    			}
    		}
    	}
    	else if (angleOfFirstStop > this.getTourStops().get(2).getAngle()) {
    		for (int i=2; i<this.getTourStops().size(); i++) {
    			if (!this.getTourStops().get(i).equals(Main.getDepot()) && this.getTourStops().get(i).getAngle() > angleOfFirstStop) {
//    				System.out.println("interlace from position "+(i-1)+" to "+i);
//    				System.out.println("The new Startlocation of this Tour should be @"+(i-1));
    				this.setInterlaceAt(i-1);
    				return true;
    			}
    		}
    	}
    	return false; // if here are no interlaces
    }
    
    public void solveInterlace() {
    	this.duration = 0;
    	ArrayList<Location> zwischenspeicherLocations = new ArrayList<>();
    	zwischenspeicherLocations.add(Main.getDepot());
    	for (int i=this.getInterlaceAt(); i>=1; i--) {
    		zwischenspeicherLocations.add(this.getTourStops().get(i));
    	}
    	for (int i=this.getInterlaceAt()+1; i<this.getTourStops().size(); i++) {
    		zwischenspeicherLocations.add(this.getTourStops().get(i));
    	}
    	this.setTourStops(zwischenspeicherLocations);
    }
    
    public void addDurationEntireTour() {
    	for (int i=0; i<this.getTourStops().size(); i++) {
    		this.addDuration(this.getTourStops().get(i));
    	}
    }

	public boolean createGivenTour(int[] useMethod, ArrayList<Location> locations) {
		addStopDepot(Main.findFarthestLocation(tourStops.get(tourStops.size() - 1), locations), locations);
		if (!locations.isEmpty()) {
			for (int i = 0; i < useMethod.length; i++) {
				if (useMethod[i] == 0) {
					addStopDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations), locations);
				}
				if (useMethod[i] == 1) {
					if (locations.size() < 2) {
						addStopDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations),
								locations);
					} else {
						addStopDepot(Main.findSecondClosestLocation(tourStops.get(tourStops.size() - 1), locations),
								locations);
					}
				}
			}
		} else {
			return false;
		}
		return true;
	}

	public boolean addStopDepot(Location location, ArrayList<Location> locations) {
		if (duration
				+ (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient()
						/ Main.getMeterPerSecond()) / 60
				+ (Main.getDistance(Main.getDepot(), tourStops.get(tourStops.size() - 1)) * 1000F
						* Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60
				+ location.getDuration() < maxDuration) {
//			duration += (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F
//					* Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60 + location.getDuration();
			this.addDuration(location);
			tourStops.add(location);
			System.out.println("hello");
			locations.remove(Main.getIndex(locations, location));
			Main.setLastLocation(location);
			return true;
		}
		tourStops.add(Main.getDepot());
		duration += (Main.getDistance(Main.getDepot(), tourStops.get(tourStops.size() - 1)) * 1000F
				* Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60;
		return false;
	}

	public void addDepot() {
		duration += (Main.getDistance(Main.getDepot(), tourStops.get(tourStops.size() - 1)) * 1000F
				* Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60;
		tourStops.add(Main.getDepot());
	}

	public boolean addStopSliceDepot(Location location, ArrayList<Location> locationsSlice,
			ArrayList<Location> locationsAll) {
		if ((duration
				+ (Main.getDistance(location, tourStops.get(tourStops.size() - 1)) * 1000F * Main.getGroundAirQuotient()
						/ Main.getMeterPerSecond()) / 60
				+ (Main.getDistance(Main.getDepot(), location) * 1000F * Main.getGroundAirQuotient()
						/ Main.getMeterPerSecond()) / 60
				+ location.getDuration()) < maxDuration) {
			this.addDuration(location);
			tourStops.add(location);
			locationsSlice.remove(Main.getIndex(locationsSlice, location));
			locationsAll.remove(Main.getIndex(locationsAll, location));
			return true;
		}
		duration += (Main.getDistance(Main.getDepot(), tourStops.get(tourStops.size() - 1)) * 1000F
				* Main.getGroundAirQuotient() / Main.getMeterPerSecond()) / 60;
		tourStops.add(Main.getDepot());
		return false;
	}

	public boolean addNextStopPizza(ArrayList<Location> locations) {
		if (!locations.isEmpty()) {
			if (!Main.isUsed() && tourStops.size() == 1) {
				Location loc = Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations);
				Main.setAngleTourStop1(loc.getAngle());
				return addStopDepot(loc, locations);
			} else if (!Main.isUsed() && tourStops.size() == 2) {
				Location loc2 = Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations);
				Main.setAngleTourStop2(loc2.getAngle());
				Main.generateAngleToLocation(Main.getTourStop2());
				return addStopDepot(loc2, locations);
			} else {
				Location loc = Main.getLocationWithSmalestAngle(tourStops.get(tourStops.size() - 1), locations);
				return addStopDepot(loc, locations);
			}
		} else {
			return false;
		}
	}

	public boolean addNextStop(ArrayList<Location> locations) {
		return locations.isEmpty() ? false
				: addStopDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations), locations);

	}

	public boolean addNextStopCircle(ArrayList<Location> locations) {
		if (Main.getLastLocation() == null) {
			if (locations.isEmpty()) {
				return false;
			} else {
				return addStopDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations),
						locations);
			}
		} else {
			if (tourStops.size() == 1) {
				if (locations.isEmpty()) {
					return false;
				} else {
					return addStopDepot(Main.findClosestLocation(
							Main.findClosestLocation(Main.getLastLocation(), locations), locations), locations);
				}
			} else {
				if (locations.isEmpty()) {
					return false;
				} else {
					return addStopDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations),
							locations);
				}
			}
		}
	}

	public boolean addNextStopRandom(ArrayList<Location> locations) {
		int rnd = (int) (Math.random() * locations.size());
		return locations.isEmpty() ? false : addStopDepot(locations.get(rnd), locations);

	}

	// needs to be checked for a few values
	public boolean addNextStopVariableSlices(ArrayList<Location> locations, int slices) {
		ArrayList<Location> locationsInSlice = new ArrayList<>();
		for (int i = 0; i < slices; i++) {
			locationsInSlice = new ArrayList<>();
			for (Location location : locations) {
				if (location.getAngle() % 360 >= i * slices && location.getAngle() % 360 < (i + 1) * (360 / slices)) {
					locationsInSlice.add(location);
				}
			}
			if (locationsInSlice.isEmpty()) {

			} else {
				return addStopSliceDepot(
						Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locationsInSlice),
						locationsInSlice, locations);
			}
		}
		return false;
	}

	public boolean addNextStopVariableSlicesFar(ArrayList<Location> locations, int slices) {
		ArrayList<Location> locationsInSlice = new ArrayList<>();
		for (int i = 0; i < slices; i++) {
			locationsInSlice = new ArrayList<>();
			for (Location location : locations) {
				if (location.getAngle() % 360 >= i * slices && location.getAngle() % 360 < (i + 1) * (360 / slices)) {
					locationsInSlice.add(location);
				}
			}
			if (locationsInSlice.isEmpty()) {

			} else {
				return locations.isEmpty() ? false
						: tourStops.size() == 1
								? addStopSliceDepot(Main.findFarthestLocation(tourStops.get(0), locationsInSlice),
										locationsInSlice, locations)
								: addStopSliceDepot(
										Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locationsInSlice),
										locationsInSlice, locations);
			}
		}
		if (slices == 0) {
			locationsInSlice = new ArrayList<>();
			for (Location location : locations) {
				locationsInSlice.add(location);
			}
			if (!locationsInSlice.isEmpty()) {
				return locations.isEmpty() ? false
						: tourStops.size() == 1
								? addStopSliceDepot(Main.findFarthestLocation(tourStops.get(0), locationsInSlice),
										locationsInSlice, locations)
								: addStopSliceDepot(
										Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locationsInSlice),
										locationsInSlice, locations);
			}
		}
		return false;
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
		if (!slice1.isEmpty()) {
			return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice1), slice1,
					locations);
		} else {
			if (!slice2.isEmpty()) {
				return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice2), slice2,
						locations);
			} else {
				if (!slice3.isEmpty()) {
					return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice3),
							slice3, locations);
				} else {
					if (!slice4.isEmpty()) {
						return addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice4),
								slice4, locations);
					} else {
						if (!slice5.isEmpty()) {
							return addStopSliceDepot(
									Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice5), slice5,
									locations);
						} else {
							if (!slice6.isEmpty()) {
								return addStopSliceDepot(
										Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice6), slice6,
										locations);
							} else {
								if (!slice7.isEmpty()) {
									return addStopSliceDepot(
											Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice7),
											slice7, locations);
								} else {
									if (!slice8.isEmpty()) {
										return addStopSliceDepot(
												Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice8),
												slice8, locations);
									} else {
										if (!slice9.isEmpty()) {
											return addStopSliceDepot(Main.findClosestLocation(
													tourStops.get(tourStops.size() - 1), slice9), slice9, locations);
										} else {
											if (!slice10.isEmpty()) {
												return addStopSliceDepot(
														Main.findClosestLocation(tourStops.get(tourStops.size() - 1),
																slice10),
														slice10, locations);
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
		return locations.isEmpty() ? false
				: tourStops.size() == 1
						? addStopDepot(Main.findFarthestLocation(tourStops.get(0), locations), locations)
						: addStopDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), locations),
								locations);
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
		if (!slice1.isEmpty()) {
			return locations.isEmpty() ? false
					: tourStops.size() == 1
							? addStopSliceDepot(Main.findFarthestLocation(tourStops.get(0), slice1), slice1, locations)
							: addStopSliceDepot(Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice1),
									slice1, locations);
		} else {
			if (!slice2.isEmpty()) {
				return locations.isEmpty() ? false
						: tourStops.size() == 1
								? addStopSliceDepot(Main.findFarthestLocation(tourStops.get(0), slice2), slice2,
										locations)
								: addStopSliceDepot(
										Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice2), slice2,
										locations);
			} else {
				if (!slice3.isEmpty()) {
					return locations.isEmpty() ? false
							: tourStops.size() == 1
									? addStopSliceDepot(Main.findFarthestLocation(tourStops.get(0), slice3), slice3,
											locations)
									: addStopSliceDepot(
											Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice3),
											slice3, locations);
				} else {
					if (!slice4.isEmpty()) {
						return locations.isEmpty() ? false
								: tourStops.size() == 1
										? addStopSliceDepot(Main.findFarthestLocation(tourStops.get(0), slice4), slice4,
												locations)
										: addStopSliceDepot(
												Main.findClosestLocation(tourStops.get(tourStops.size() - 1), slice4),
												slice4, locations);
					} else {
						if (!slice5.isEmpty()) {
							return locations.isEmpty() ? false
									: tourStops.size() == 1
											? addStopSliceDepot(Main.findFarthestLocation(tourStops.get(0), slice5),
													slice5, locations)
											: addStopSliceDepot(Main.findClosestLocation(
													tourStops.get(tourStops.size() - 1), slice5), slice5, locations);
						} else {
							if (!slice6.isEmpty()) {
								return locations
										.isEmpty()
												? false
												: tourStops.size() == 1
														? addStopSliceDepot(
																Main.findFarthestLocation(tourStops.get(0), slice6),
																slice6, locations)
														: addStopSliceDepot(
																Main.findClosestLocation(
																		tourStops.get(tourStops.size() - 1), slice6),
																slice6, locations);
							} else {
								if (!slice7.isEmpty()) {
									return locations
											.isEmpty()
													? false
													: tourStops.size() == 1
															? addStopSliceDepot(
																	Main.findFarthestLocation(tourStops.get(0), slice7),
																	slice7, locations)
															: addStopSliceDepot(Main.findClosestLocation(
																	tourStops.get(tourStops.size() - 1), slice7),
																	slice7, locations);
								} else {
									if (!slice8.isEmpty()) {
										return locations
												.isEmpty()
														? false
														: tourStops.size() == 1
																? addStopSliceDepot(Main.findFarthestLocation(
																		tourStops.get(0), slice8), slice8, locations)
																: addStopSliceDepot(Main.findClosestLocation(
																		tourStops.get(tourStops.size() - 1), slice8),
																		slice8, locations);
									} else {
										if (!slice9.isEmpty()) {
											return locations.isEmpty() ? false
													: tourStops.size() == 1
															? addStopSliceDepot(
																	Main.findFarthestLocation(tourStops.get(0), slice9),
																	slice9, locations)
															: addStopSliceDepot(Main.findClosestLocation(
																	tourStops.get(tourStops.size() - 1), slice9),
																	slice9, locations);
										} else {
											if (!slice10.isEmpty()) {
												return locations
														.isEmpty()
																? false
																: tourStops
																		.size() == 1
																				? addStopSliceDepot(
																						Main.findFarthestLocation(
																								tourStops.get(0),
																								slice10),
																						slice10, locations)
																				: addStopSliceDepot(
																						Main.findClosestLocation(
																								tourStops.get(
																										tourStops.size()
																												- 1),
																								slice10),
																						slice10, locations);
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

	public String toString() {
		String returnString = "[";
		for (Location location : tourStops) {
			returnString += location.getName() + " ";
		}
		returnString += "] " + duration + " Minuten";

		return returnString;
	}
}
