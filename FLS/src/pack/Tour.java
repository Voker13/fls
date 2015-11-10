package pack;

import java.util.ArrayList;

public class Tour {
    
    int duration = 0;
    int maxDuration = 480;
    ArrayList<Location> tourStops = new ArrayList<>();
    
    public Tour() {
	tourStops.add(Main.depot);
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
	if (duration+(Main.getDistance(location, tourStops.get(tourStops.size()-1))*1000F*Main.groundAirQuotient/Main.meterPerSecond)/60 + location.duration < maxDuration) {
	    duration += (Main.getDistance(location, tourStops.get(tourStops.size()-1))*1000F*Main.groundAirQuotient/Main.meterPerSecond)/60 + location.duration;
	    tourStops.add(location);
	    Main.locations.remove(Main.getIndex(location));
	    return true;
	}
	return false;
    }
    
    public boolean addNextStop() {
	return Main.locations.isEmpty() ? false: addStop(Main.findClosestLocation(tourStops.get(tourStops.size()-1), Main.locations));
    }

}
