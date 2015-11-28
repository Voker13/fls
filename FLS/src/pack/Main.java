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
    private static double groundAirQuotient;
    private static double kilometerPerHour;
    private static double meterPerSecond;
    private static float minLat = 1000;
    private static float maxLat = 0;
    private static float minLong = 1000;
    private static float maxLong = 0;
    private static Location depot;
    private static Location lastLocation = null;
    private static double AngleTourStop1; // wichtig, ob es rechtsherum oder
					  // linksherum gehen soll
    private static double AngleTourStop2; // wichtig, ob es rechtsherum oder
					  // linksherum gehen soll
    private static Location TourStop2;
    private static boolean isUsed = false;
    private static ArrayList<ArrayList<Tour>> tours = new ArrayList<>();
    private static int distanceAir = 0;
    private static int distanceGround = 0;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws JAXBException, FileNotFoundException {
	JAXBContext jc = JAXBContext.newInstance(Instance.class);

	Unmarshaller unmarshaller = jc.createUnmarshaller();
	File xml = null;
	if (args.length == 0) {
	    xml = new File("Instance-400.xml");
	} else {
	    xml = new File(args[0]);
	}

	// load instance from File
	instance = (Instance) unmarshaller.unmarshal(xml);

	// save depot as special Locaion
	depot = instance.getLocations().get(0);

	// clone Array to have a cop for drawing
	locations = (ArrayList<Location>) instance.getLocations();
	locCopy = (ArrayList<Location>) locations.clone();
	
	// remove depot from Locations
	locations.remove(0);

	generateAngleToLocation();
	generateDistance0ToLocation();

	// get all Edges
	edges = (ArrayList<Edge>) instance.getEdges();

	calculateGroundToAirQuotient();

	calculateAvarageSpeed();
	
	// run Strategies
	closestStrategy();
	circleStrategy();
	pizzaStrategy();
	randomStrategy();
	sliceStrategy();
	farToCloseStrategy();
	slicesPlusFarStrategy();
	//variableSliceStrategy(10);
	slicePlusFarPlusForecastStrategy();
	
	GraphFrame gf = new GraphFrame(tours);
	gf.repaint();
    }

    private static void calculateGroundToAirQuotient() {
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
    }

    private static void calculateAvarageSpeed() {
	int time = 0;
	for (int i = 0; i < locations.size(); i++) {
	    if (!(i == 0)) {
		time += getEdge(0, i).getDuration();
	    }
	}

	kilometerPerHour = (distanceGround * 60) / (time);
	meterPerSecond = kilometerPerHour / 3.6F;
    }
    
    @SuppressWarnings("unchecked")
    private static void closestStrategy() {
	ArrayList<Tour> allToursClosest = new ArrayList<Tour>();
	
	ArrayList<Location> workCopy = (ArrayList<Location>) locations.clone();
	while (!workCopy.isEmpty()) {
	    allToursClosest.add(findWorkDay(workCopy));
	}
	
	int durationOverallClosest = 0;
	for (Tour tour : allToursClosest) {
	    durationOverallClosest += tour.getDuration();
	}
	
	tours.add(allToursClosest);
	
	System.err.println("Closest Strategy: " + (allToursClosest.size()) + " Touren mit einer Gesamtfahrzeit von " + durationOverallClosest + " Minuten");
    }
    
    @SuppressWarnings("unchecked")
    private static void circleStrategy() {
	ArrayList<Tour> allToursCircle = new ArrayList<Tour>();
	
	ArrayList<Location> workCopy = (ArrayList<Location>) locations.clone();
	while (!workCopy.isEmpty()) {
	    allToursCircle.add(findWorkDayCircle(workCopy));
	}

	int durationOverallCircle = 0;
	for (Tour tour : allToursCircle) {
	    durationOverallCircle += tour.getDuration();
	}

	tours.add(allToursCircle);
	
	System.err.println("Circle Strategy: " + (allToursCircle.size()) + " Touren mit einer Gesamtfahrzeit von " + durationOverallCircle + " Minuten");
    }
    
    private static void pizzaStrategy() {
	ArrayList<Tour> allToursPizza = new ArrayList<Tour>();
	
	ArrayList<Location> radius1 = new ArrayList<>();
	ArrayList<Location> radius2 = new ArrayList<>();
	for (Location loc : locations) {
	    if (loc.getDistance0() < 0.03) {
		radius1.add(loc);
	    } else {
		radius2.add(loc);
	    }
	}
	while (!radius1.isEmpty()) {
	    allToursPizza.add(findWorkDayPizza(radius1));
	}
	while (!radius2.isEmpty()) {
	    allToursPizza.add(findWorkDayPizza(radius2));
	}
	
	int durationOverallPizza = 0;
	for (Tour tour : allToursPizza) {
	    durationOverallPizza += tour.getDuration();
	}

	tours.add(allToursPizza);
	
	System.err.println("Pizza Strategy: " + (allToursPizza.size()) + " Touren mit einer Gesamtfahrzeit von " + durationOverallPizza + " Minuten");
    }
    
    @SuppressWarnings("unchecked")
    private static void randomStrategy() {
	ArrayList<Tour> allToursRandom = new ArrayList<Tour>();
	
	ArrayList<Location> workCopy = (ArrayList<Location>) locations.clone();
	while (!workCopy.isEmpty()) {
	    allToursRandom.add(findWorkDayRandom(workCopy));
	}
	
	int durationOverallRandom = 0;
	for (Tour tour : allToursRandom) {
	    durationOverallRandom += tour.getDuration();
	}
	
	tours.add(allToursRandom);
	
	System.err.println("Random Strategy: " + (allToursRandom.size()) + " Touren mit einer Gesamtfahrzeit von " + durationOverallRandom + " Minuten");
    }
    
    @SuppressWarnings("unchecked")
    private static void sliceStrategy() {
	ArrayList<Tour> allToursSlices = new ArrayList<Tour>();
	
	ArrayList<Location> workCopy = (ArrayList<Location>) locations.clone();
	while (!workCopy.isEmpty()) {
	    allToursSlices.add(findWorkDaySlices(workCopy));
	}
	
	int durationOverallSlices = 0;
	for (Tour tour : allToursSlices) {
	    durationOverallSlices += tour.getDuration();
	}
	
	tours.add(allToursSlices);
	
	System.err.println("Slices Strategy: " + (allToursSlices.size()) + " Touren mit einer Gesamtfahrzeit von " + durationOverallSlices + " Minuten");
    }
    
    @SuppressWarnings("unchecked")
    private static void farToCloseStrategy() {
	ArrayList<Tour> allToursFarToClose = new ArrayList<Tour>();
	
	ArrayList<Location> workCopy = (ArrayList<Location>) locations.clone();
	while (!workCopy.isEmpty()) {
	    allToursFarToClose.add(findWorkDayFarToClose(workCopy));
	}
	
	int durationOverallFarToClose = 0;
	for (Tour tour : allToursFarToClose) {
	    durationOverallFarToClose += tour.getDuration();
	}
	
	tours.add(allToursFarToClose);
	
	System.err.println("FarToClose Strategy: " + (allToursFarToClose.size()) + " Touren mit einer Gesamtfahrzeit von " + durationOverallFarToClose + " Minuten");
    }
    
    @SuppressWarnings("unchecked")
    private static void slicesPlusFarStrategy() {
	ArrayList<Tour> allToursSlicePlusFar = new ArrayList<Tour>();
	
	ArrayList<Location> workCopy = (ArrayList<Location>) locations.clone();
	while (!workCopy.isEmpty()) {
	    allToursSlicePlusFar.add(findWorkDaySlicePlusFar(workCopy));
	}
	
	int durationOverallSlicePlusFar = 0;
	for (Tour tour : allToursSlicePlusFar) {
	    durationOverallSlicePlusFar += tour.getDuration();
	}
	
	tours.add(allToursSlicePlusFar);
	
	System.err.println("SlicePlusFar Strategy: " + (allToursSlicePlusFar.size()) + " Touren mit einer Gesamtfahrzeit von " + durationOverallSlicePlusFar + " Minuten");
    }
    
    @SuppressWarnings("unused")
    private static void slicePlusFarPlusForecastStrategy() {
	ArrayList<Tour> allToursSlicePlusFarPlusForecasting = new ArrayList<Tour>();
	
	ArrayList<Location> slice1 = new ArrayList<>();
	for (Location location : locations) {
	    if (location.getAngle() % 360 >= 0 && location.getAngle() % 360 < 36) {
		slice1.add(location);
	    }
	}

	while (!slice1.isEmpty()) {
	    allToursSlicePlusFarPlusForecasting.add(findWorkDaySlicePlusFarPlusForecasting(slice1));
	}

	int durationOverallSlicePlusFarPlusForecasting = 0;
	for (Tour tour : allToursSlicePlusFarPlusForecasting) {
	    durationOverallSlicePlusFarPlusForecasting += tour.getDuration();
	}
	
	tours.add(allToursSlicePlusFarPlusForecasting);
	
	System.err.println("SlicePlusFarPlusForecasting Strategy: " + (allToursSlicePlusFarPlusForecasting.size()) + " Touren mit einer Gesamtfahrzeit von " + durationOverallSlicePlusFarPlusForecasting + " Minuten");
    }
    
    @SuppressWarnings("unchecked")
    private static void variableSliceStrategy(int slices) {
	ArrayList<Tour> allToursSliceVariable = new ArrayList<>();
	
	ArrayList<Location> workCopy = (ArrayList<Location>) locations.clone();
			
	workCopy = (ArrayList<Location>) locations.clone();
	while (!workCopy.isEmpty()) {
	    allToursSliceVariable.add(findWorkDayVariableSlices(workCopy,slices));
	}	
	
	int durationOverallSliceVariable = 0;
	for (Tour tour : allToursSliceVariable) {
	    durationOverallSliceVariable += tour.getDuration();
	}
	
	tours.add(allToursSliceVariable);
	
	System.err.println("SlicePlusVariable(" + slices + "): " + (allToursSliceVariable.size()) + " Touren mit einer Gesamtfahrzeit von " + durationOverallSliceVariable + " Minuten");
    }
    
    private static Tour findWorkDaySlices(ArrayList<Location> locations) {
	Tour tour = new Tour();
	while (tour.addNextStopSlices(locations)) {

	}
	if (locations.isEmpty()) {
	    tour.addDepot();
	}
	return tour;
    }

    private static Tour findWorkDayFarToClose(ArrayList<Location> locations) {
	Tour tour = new Tour();
	while (tour.addNextStopFarToClose(locations)) {

	}
	return tour;
    }

    private static Tour findWorkDaySlicePlusFar(ArrayList<Location> locations) {
	Tour tour = new Tour();
	while (tour.addNextStopSlicePlusFar(locations)) {

	}
	return tour;
    }

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
	if (locations.isEmpty()) {
	    tour.addDepot();
	}
	return tour;
    }
    
    private static Tour findWorkDayVariableSlices(ArrayList<Location> locations,int slices) {
	Tour tour = new Tour();
	while (tour.addNextStopVariableSlices(locations,slices)) {

	}
	if (locations.isEmpty()) {
	    tour.addDepot();
	}
	return tour;
    }

    private static Tour findWorkDaySlicePlusFarPlusForecasting(ArrayList<Location> locations) {
	
	//generateAngleToLocation();
	
	Tour tour = plusForecastingTobi(locations);
	for (int i = 0; i < tour.getTourStops().size(); i++) {
		locations.remove(tour.getTourStops().get(i));
	}
	System.out.println("locations.size(): " + locations.size());
	//while (tour.addNextStopSlicePlusFarPlusForecasting(locations)) {
	//while (tour.addNextStopPizza(slice1));
	// System.err.println(tour.getDuration() + " " + tour.getTourStops());
	
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
	// System.err.println(tour.getDuration() + " " + tour.getTourStops());
	return tour;
    }

    private static Tour findWorkDayCircle(ArrayList<Location> locations) {
	Tour tour = new Tour();
	while (tour.addNextStopCircle(locations)) {

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
    
    public static Location findSecondClosestLocation(Location location, ArrayList<Location> locations) {
	Location closestLocation = findClosestLocation(location,locations);
	locations.remove(closestLocation);
	Location secondClosestLocation = findClosestLocation(location,locations);
	return secondClosestLocation;
    }
    
    public static Location findClosestLocation(Location location, Location withoutLocation, ArrayList<Location> locations) {
    	Location returnLocation = locations.get(0);
    	for (int i = 1; i < locations.size(); i++) {
    		if (locations.get(i)!=withoutLocation && getDistance(location, locations.get(i)) < getDistance(returnLocation, location)) {
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

    public static Location findFarthestLocation(Location location, Location withoutLoc, ArrayList<Location> locations) {
	Location returnLocation = locations.get(0);
	System.out.println(Main.getDepot().getLat() + " : " + Main.getDepot().getLong());
	for (int i = 1; i < locations.size(); i++) {
	    if (locations.get(i) != withoutLoc && (getDistance(location, locations.get(i)) > getDistance(returnLocation, location))) {
		    returnLocation = locations.get(i);
	    }
	}
	return returnLocation;
    }
    
    public static Location findFarthestLocationToDepot(ArrayList<Location> locations) {
    	Location returnLocation = Main.getDepot();
    	Location origin = Main.getDepot();
    	for (int i = 1; i < locations.size(); i++) {
    	    if ((getDistance(origin, locations.get(i)) > getDistance(returnLocation, origin))) {
    		returnLocation = locations.get(i);
    	    }
    	}
    	return returnLocation;
        }

    public static Location findFarthestLocationToDepot(Location location, Location withoutLoc, ArrayList<Location> locations) {
    	Location returnLocation = Main.getDepot();
    	System.out.println(Main.getDepot().getLat() + " : " + Main.getDepot().getLong());
    	for (int i = 1; i < locations.size(); i++) {
    	    if (locations.get(i) != withoutLoc && (getDistance(location, locations.get(i)) > getDistance(returnLocation, location))) {
    		    returnLocation = locations.get(i);
    	    }
    	}
    	return returnLocation;
        }

    @SuppressWarnings("unchecked")
    public static Location findClosestLocationGoingToTheMiddle(Location location, ArrayList<Location> locations) {
	ArrayList<Location> copy = (ArrayList<Location>) locations.clone();
	Location returnLocation = locations.get(0);
	Location closestLocation = locations.get(0);
	for (int i = 1; i < copy.size(); i++) {
	    if ((getDistance(location, copy.get(i)) < getDistance(returnLocation, location))) {
		closestLocation = copy.get(i);
	    }
	}
	if (getDistance(closestLocation, depot) < getDistance(location, depot)) {
	    returnLocation = closestLocation;
	} else {
	    copy.remove(getIndex(copy, closestLocation));
	    returnLocation = findClosestLocation(location, copy);
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

    /*
     * 
     * BAUSTELLE!!
     * 
     */
    @SuppressWarnings("unchecked")
    public static Tour plusForecastingTobi(ArrayList<Location> locations) {
	System.out.println("locations(forecasting) size: "+ locations.size());
	int deltaTourlänge = 5;
	int erg = (int) Math.pow(2, deltaTourlänge); // length
	
	int[][] methodUse = new int[erg][deltaTourlänge];
	for (int i = 0; i < erg; i++) {
	    for (int j = 0; j < deltaTourlänge; j++) {
		methodUse[i][j]=(int) (i/(Math.pow(2, deltaTourlänge-1-j))) %2;
	    }
	}
	
	for (int i = 0; i < erg; i++) {
	    for (int j = 0; j < deltaTourlänge; j++) {
		System.out.print(methodUse[i][j]);
	    }
	    System.out.println();
	}
	
	Tour[] possibleTours = new Tour[erg];
	
	for (int i = 0; i < erg; i++) {
	    Tour tour = new Tour();
	    tour.createGivenTour(methodUse[i],(ArrayList<Location>) locations.clone());
	    possibleTours[i] = tour;
	}
	
	for (int i = 0; i < possibleTours.length; i++) {
	    System.out.println(possibleTours[i].getDuration());
	}
	
	// calculates the shortest tour
	Tour shortestTour = possibleTours[0];
	int time = shortestTour.getDuration();
	for (int i = 0; i < possibleTours.length; i++) {
		if (possibleTours[i].getDuration() < time) {
			time = possibleTours[i].getDuration();
			shortestTour = possibleTours[i];
		}
	}
	
	System.out.println("shortest: "+time);
	return shortestTour;
	
    }
    
    public static void plusForecasting(ArrayList<Location> locations) {

	int deltaTourlänge = 4;
	int expo = deltaTourlänge - 1; // tourlänge //width
	int erg = (int) Math.pow(2, expo); // length

	Location[][] locArray = new Location[erg][deltaTourlänge];
	Location locO0 = Main.findFarthestLocation(Main.getDepot(), locations);  

	for (int i = 0; i < deltaTourlänge; i++) {
	    locArray[i][0] = locO0;
	}
	int s = 0; //Stelle im locArray
	for (int j = 1; j < expo; j++) {
	    int i = (int) Math.pow(2, expo - j);
	    s = 0;
	    for (int t = 0; t < erg / i; t++) {
		for (int r = 0; r < i; r++) {
			Location loc = Main.findClosestLocation(locArray[s][j - 1], locations);
		    if (t % 2 == 0) {
		    	locArray[s][j] = Main.findClosestLocation(loc, loc, locations);
		    }
		    else {
		    	locArray[s][j] = loc;
		    }
		    s++;
		}
	    }
	}
	System.out.println("locArray.length: " + locArray.length);
	for (int i = 0; i < locArray.length; i++) {
	    System.out.println("locArray[" + 1 + "][" + locArray[1][i].getName() + "]");
	}
	@SuppressWarnings("unused")
	Location returnLocation;
	// for ()
	// return

    }

    public static Location findLocationWithSmalestAngle() {
	double smalestAngle = 720;
	Location locWithSmalestAngle = locations.get(0); // nicht das depot
	for (Location loc : locations) {
	    if (loc.getAngle() < smalestAngle) {
		smalestAngle = loc.getAngle();
		locWithSmalestAngle = loc;
	    }
	}
	return locWithSmalestAngle;
    }

    public static Location getLocationWithSmalestAngle(Location currentLocation, ArrayList<Location> locations) {

	Location smalestAngleLocation = currentLocation;
	double smalestAngle = 720;

	for (Location location : locations) {
	    double angle = location.getAngle();
	    if (angle < smalestAngle) {
		smalestAngle = angle;
		smalestAngleLocation = location;
	    }
	}

	return smalestAngleLocation;
    }

    public static void generateAngleToLocation(Location loc) {
	for (Location location : locations) {
	    double angle = location.getAngle();
	    if (angle < Main.getAngleTourStop2()) {
		location.setAngle(angle + 360);
	    }
	}
	// System.err.println("AngleTourStop 1&2: " + Main.AngleTourStop1 +
	// " : " + Main.getAngleTourStop2());
	if (Main.getAngleTourStop1() > Main.getAngleTourStop2()) { // wenn wahr,
								   // deht die
								   // reihenfolge
								   // der winkel
								   // um
	    for (Location location : locations) {
		location.setAngle(720 - location.getAngle());
	    }
	    // System.err.println(true);
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
	    // //System.err.println(location.getAngle());
	}
    }

    public static void bla() {
	ArrayList<Location> radius1 = new ArrayList<>();
	ArrayList<Location> radius2 = new ArrayList<>();
	for (Location loc : locations) {
	    if (loc.getDistance0() < 0.03) {
		radius1.add(loc);
	    } else {
		radius2.add(loc);
	    }
	}
    }

    public static void generateDistance0ToLocation() {
	double x0 = depot.getLong();
	double y0 = depot.getLat();
	for (Location location : locations) {
	    double dx = x0 - location.getLong();
	    double dy = y0 - location.getLat();
	    location.setDistance0(Math.sqrt((dx * dx) + (dy * dy)));
	    // //System.err.println("distance0: "+location.getDistance0());
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
