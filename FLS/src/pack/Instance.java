package pack;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Instance")
public class Instance {
    
    int numberOfLocations;
    List<Location> locations = new ArrayList<Location>();
    List<Edge> edges = new ArrayList<Edge>();
    
    public Instance(){
    }
    
    @XmlElement(name="NumberOfLocations")
    public int getNumberOfLocations() {
	return numberOfLocations;
    }
    
    public void setNumberOfLocations(int NumberOfLocations) {
	this.numberOfLocations = NumberOfLocations;
    }
    
    @XmlElementWrapper(name="Locations")
    @XmlElement(name="Location")
    public List<Location> getLocations() {
	return locations;
    }
    
    public void setLocations(List<Location> Locations) {
	this.locations = Locations;
    }
    
    public void addLocation(Location location) {
	locations.add(location);
    }
    
    @XmlElementWrapper(name="Edges")
    @XmlElement(name="Edge")
    public List<Edge> getEdges() {
	return edges;
    }
    
    public void setEdges(List<Edge> Edges) {
	this.edges = Edges;
    }
    
    public String toString() {
	String listLoc = "";
	String listEdge = "";
	for (Location loc : locations) {
	    listLoc += loc;
	}
	for (Edge edge : edges) {
	    listEdge += edge;
	}
	return "Number of Locations: " + numberOfLocations + "\n" + "Locations: " + "\n" + listLoc + "\nEdges: " + listEdge;
    }
    
}
