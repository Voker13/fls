package pack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Main {

    private static int timePerWorkday = 480;
    private static ArrayList<Location> locations;
    private static ArrayList<Location> locCopy;
    private static Instance instance;
    private static ArrayList<Edge> edges;
    //private static ArrayList<Tour> allTours;
    private static double groundAirQuotient;
    private static double kilometerPerHour;
    private static double meterPerSecond;
    private static float minLat = 1000;
    private static float maxLat = 0;
    private static float minLong = 1000;
    private static float maxLong = 0;
    private static Location depot;
    private static Location lastLocation = null;
    private static double AngleTourStop1;  // wichtig, ob es rechtsherum oder linksherum gehen soll
    private static double AngleTourStop2;  // wichtig, ob es rechtsherum oder linksherum gehen soll
    private static Location TourStop2;
    private static boolean isUsed = false;


	@SuppressWarnings("unchecked")
    public static void main(String[] args) throws JAXBException, FileNotFoundException {
	JAXBContext jc = JAXBContext.newInstance(Instance.class);

	Unmarshaller unmarshaller = jc.createUnmarshaller();
	File xml = new File("Instance-400.xml");

	// load instance from File
	instance = (Instance) unmarshaller.unmarshal(xml);

	// take current system time / start time
	long startTime = System.currentTimeMillis();

	// save depot as special Locaion
	depot = instance.getLocations().get(0);

	// clone Array to have a cop for drawing
	locations = (ArrayList<Location>) instance.getLocations();
	locCopy = (ArrayList<Location>) locations.clone();
	// remove depot from Locations
	locations.remove(0);

	generateAngleToLocation();

	// get all Edges
	edges = (ArrayList<Edge>) instance.getEdges();

	// Calculate Quotient and get Min-/max Long and Lat
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

	// Control to see how much the Quotient fails
	int distanceGroundCalculated = 0;
	distanceGround = 0;
	for (int i = 0; i < locations.size(); i++) {
	    if (!(i == 0)) {
		distanceGround += getEdge(0, i).distance / 1000;
		distanceGroundCalculated += getDistance(depot, locations.get(i)) * groundAirQuotient;
	    }
	}
	System.out.println("Calculated Value: " + distanceGroundCalculated + " " + "Real Value: " + distanceGround);

	// Calculate avarage speed
	int time = 0;
	for (int i = 0; i < locations.size(); i++) {
	    if (!(i == 0)) {
		time += getEdge(0, i).getDuration();
	    }
	}

	kilometerPerHour = (distanceGround * 60) / (time);
	meterPerSecond = kilometerPerHour / 3.6F;

	System.out.println("Liste zum 1.: ");
	for (int i=0; i<locations.size(); i++) {
		System.out.println("location"+(i+1)+".-->   "+locations.get(i).getLat()+" : "+locations.get(i).getLong()+" : "+locations.get(i).getAngle());
	}
	
	ArrayList<Tour> allToursClosest = new ArrayList<Tour>();
	ArrayList<Tour> allToursCircle = new ArrayList<Tour>();
	ArrayList<Tour> allToursPizza = new ArrayList<Tour>();
	ArrayList<Tour> allToursRandom = new ArrayList<Tour>();
	
	// Here is the Strategy, Tours are built until locations is empty
	ArrayList<Location> workCopy = (ArrayList<Location>) locations.clone();
	while (!workCopy.isEmpty()) {
	    allToursClosest.add(findWorkDay(workCopy));
	}
	
	workCopy = (ArrayList<Location>) locations.clone();
	while (!workCopy.isEmpty()) {
	    allToursCircle.add(findWorkDayCircle(workCopy));
	}
	
	workCopy = (ArrayList<Location>) locations.clone();
	while (!workCopy.isEmpty()) {
	    allToursPizza.add(findWorkDayPizza(workCopy));
	}
	workCopy = (ArrayList<Location>) locations.clone();
	while (!workCopy.isEmpty()) {
	    allToursRandom.add(findWorkDayRandom(workCopy));
	}

	// Counts time for all Tours
	int durationOverallClosest = 0;
	for (Tour tour : allToursClosest) {
	    durationOverallClosest += tour.getDuration();
	}
	
	int durationOverallCircle = 0;
	for (Tour tour : allToursCircle) {
	    durationOverallCircle += tour.getDuration();
	}
	
	int durationOverallPizza = 0;
	for (Tour tour : allToursPizza) {
	    durationOverallPizza += tour.getDuration();
	}
	
	int durationOverallRandom = 0;
	for (Tour tour : allToursRandom) {
	    durationOverallRandom += tour.getDuration();
	}

	// Some Debug info, like time and Graph
	System.out.println("Closest Strategy: " + allToursClosest.size() + " Touren mit einer Gesamtfahrzeit von " + durationOverallClosest + " Minuten");
	System.out.println("Circle Strategy: " + allToursCircle.size() + " Touren mit einer Gesamtfahrzeit von " + durationOverallCircle + " Minuten");
	System.out.println("Pizza Strategy: " + allToursPizza.size() + " Touren mit einer Gesamtfahrzeit von " + durationOverallPizza + " Minuten");
	System.out.println("Random Strategy: " + allToursRandom.size() + " Touren mit einer Gesamtfahrzeit von " + durationOverallRandom + " Minuten");
	long endTime = System.currentTimeMillis();
	System.out.println("Elapsed Time: " + (endTime - startTime) + "ms");
	ArrayList<ArrayList<Tour>> tours = new ArrayList<>();
	tours.add(allToursClosest);
	tours.add(allToursCircle);
	tours.add(allToursPizza);
	tours.add(allToursRandom);
	GraphFrame gf = new GraphFrame(tours);
	gf.repaint();
    }

	/*
	 * Default
	 * 
	 */
	
	private static Tour findWorkDayRandom(ArrayList<Location> locations) {
	    Tour tour = new Tour();
		while (tour.addNextStopRandom(locations)) {

		}
		return tour;
	}

	private static Tour findWorkDay(ArrayList<Location> locations) {
		Tour tour = new Tour();
		while (tour.addNextStop(locations)) {

		}
		return tour;
	    }
	
    /*
     * PizzaTactic
     */

    private static Tour findWorkDayPizza(ArrayList<Location> locations) {
	Tour tour = new Tour();
	generateAngleToLocation();
	while (tour.addNextStopPizza(locations)) {

	}
	System.out.println(tour.getDuration() + " " + tour.getTourStops());
	return tour;
    }

    /*
     * The solution needs even longer than findWorkDay()
     */
    private static Tour findWorkDayCircle(ArrayList<Location> locations) {
	Tour tour = new Tour();
	while (tour.addNextStopCircle(locations)) {

	}
	System.out.println(tour.getDuration() + " " + tour.getTourStops());
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

    public static int getIndex(ArrayList<Location> locations, Location location) {
	for (int i = 0; i < locations.size(); i++) {
	    if (locations.get(i).equals(location)) {
	    	
		return i;
	    }
	}
	
	return -1;
    }

    /**
	 * 
	 *  
	 * 
	 */
    
    public static Location findLocationWithSmalestAngle() {
    	double smalestAngle = 720;
    	Location locWithSmalestAngle = locations.get(0);  //nicht das depot
    	for (Location loc : locations) {
    		if (loc.getAngle()<smalestAngle) {
    			smalestAngle = loc.getAngle();
    			locWithSmalestAngle = loc;
    		}
    	}
    	return locWithSmalestAngle;
    }
    

	public static Location getLocationWithSmalestAngle(Location currentLocation, ArrayList<Location> locations) {
//		double currentAngle = currentLocation.getAngle();
//		System.out.println("currentAngle: " + currentAngle);

		Location smalestAngleLocation = currentLocation;
		double smalestAngle = 720;

		for (Location location : locations) {
			double angle = location.getAngle();
			if (angle < smalestAngle) {
				smalestAngle = angle;
				smalestAngleLocation = location;
			}
		}
		
//		for (Location location : locations) {
//			double angle = location.getAngle();
//			double DeltaAngle = betrag(angle - currentAngle);
//			if (DeltaAngle < closestDeltaAngle) { 
//				closestDeltaAngle = DeltaAngle;
//				closestAngleLocation = location;
//			}
//		}

		return smalestAngleLocation;
	}
    
    
    
    public static void generateAngleToLocation(Location loc) {
    	for (Location location : locations) {
		    double angle = location.getAngle();
			if (angle<Main.getAngleTourStop2()) {
		    	location.setAngle(angle+360);
		    }
		}
    	System.out.println("AngleTourStop 1&2: " + Main.AngleTourStop1 + " : " + Main.getAngleTourStop2());
    	if (Main.getAngleTourStop1()>Main.getAngleTourStop2()) { //wenn wahr, dann dreht er die reihenfolge der winkel um
    		for (Location location : locations) {
    		    location.setAngle(720-location.getAngle());
    		}
    		System.out.println(true);
    	}
    	
    }	
    
    
    
    public static void generateAngleToLocation() {
	double x0 = depot.getLong();
	double y0 = depot.getLat();
	for (Location location : locations) {
	    double dx = x0 - location.getLong();
	    double dy = y0 - location.getLat();
	    if (dx >= 0 && dy >= 0) {
		location.setAngle(Math.toDegrees(Math.atan(dy / dx)));
	    } else if (dx < 0 && dy >= 0) {
		location.setAngle(Math.toDegrees(Math.atan(dy / dx)) + 180);
	    } else if (dx < 0 && dy < 0) {
		location.setAngle(Math.toDegrees(Math.atan(dy / dx)) + 180);
	    } else if (dx >= 0 && dy < 0) {
		location.setAngle(Math.toDegrees(Math.atan(dy / dx)) + 360);
	    }
	    // System.out.println(location.getAngle());
	}
    }

    public static double getBigger(double x, double y) {
	if (x < y) {
	    return y;
	} else {
	    return x;
	}
    }

    public static double betrag(double x) {
	if (x <= 0) {
	    x *= (-1);
	}
	return x;
    }

    public static int getTimePerWorkday() {
	return timePerWorkday;
    }

    public static void setTimePerWorkday(int timePerWorkday) {
	Main.timePerWorkday = timePerWorkday;
    }

    public static ArrayList<Location> getLocations() {
	return locations;
    }

    public static void setLocations(ArrayList<Location> locations) {
	Main.locations = locations;
    }

    public static ArrayList<Location> getLocCopy() {
	return locCopy;
    }

    public static void setLocCopy(ArrayList<Location> locCopy) {
	Main.locCopy = locCopy;
    }

    public static Instance getInstance() {
	return instance;
    }

    public static void setInstance(Instance instance) {
	Main.instance = instance;
    }

    public static ArrayList<Edge> getEdges() {
	return edges;
    }

    public static void setEdges(ArrayList<Edge> edges) {
	Main.edges = edges;
    }

    public static double getGroundAirQuotient() {
	return groundAirQuotient;
    }

    public static void setGroundAirQuotient(double groundAirQuotient) {
	Main.groundAirQuotient = groundAirQuotient;
    }

    public static double getKilometerPerHour() {
	return kilometerPerHour;
    }

    public static void setKilometerPerHour(double kilometerPerHour) {
	Main.kilometerPerHour = kilometerPerHour;
    }

    public static double getMeterPerSecond() {
	return meterPerSecond;
    }

    public static void setMeterPerSecond(double meterPerSecond) {
	Main.meterPerSecond = meterPerSecond;
    }

    public static float getMinLat() {
	return minLat;
    }

    public static void setMinLat(float minLat) {
	Main.minLat = minLat;
    }

    public static float getMaxLat() {
	return maxLat;
    }

    public static void setMaxLat(float maxLat) {
	Main.maxLat = maxLat;
    }

    public static float getMinLong() {
	return minLong;
    }

    public static void setMinLong(float minLong) {
	Main.minLong = minLong;
    }

    public static float getMaxLong() {
	return maxLong;
    }

    public static void setMaxLong(float maxLong) {
	Main.maxLong = maxLong;
    }

    public static Location getDepot() {
	return depot;
    }

    public static void setDepot(Location depot) {
	Main.depot = depot;
    }

    public static Location getLastLocation() {
	return lastLocation;
    }

    public static void setLastLocation(Location lastLocation) {
	Main.lastLocation = lastLocation;
    }
    
    public static double getAngleTourStop1() {
		return AngleTourStop1;
	}

	public static void setAngleTourStop1(double angleTourStop1) {
		AngleTourStop1 = angleTourStop1;
	}

	public static double getAngleTourStop2() {
		return AngleTourStop2;
	}

	public static void setAngleTourStop2(double angleTourStop2) {
		AngleTourStop2 = angleTourStop2;
	}

	public static Location getTourStop2() {
		return TourStop2;
	}

	public static void setTourStop2(Location tourStop2) {
		TourStop2 = tourStop2;
	}

	public static boolean isUsed() {
		return isUsed;
	}

	public static void setUsed(boolean isUsed) {
		Main.isUsed = isUsed;
	}


}
