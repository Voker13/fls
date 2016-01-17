package pack;

/**
 * 
 * This class represents the connection between two Locations
 *
 */
public class Edge {

    int duration;
    int distance;
    int from;
    int to;

    public Edge() {

    }

    public Edge(int duration, int distance, int from, int to) {
	this.duration = duration;
	this.distance = distance;
	this.from = from;
	this.to = to;
    }

    public int getDuration() {
	return duration;
    }

    public void setDuration(int duration) {
	this.duration = duration;
    }

    public int getDistance() {
	return distance;
    }

    public void setDistance(int distance) {
	this.distance = distance;
    }

    public int getFrom() {
	return from;
    }

    public void setFrom(int from) {
	this.from = from;
    }

    public int getTo() {
	return to;
    }

    public void setTo(int to) {
	this.to = to;
    }

    public String toString() {
	return "\nDistance: " + distance + "\nDuration: " + duration + "\nFrom: " + from + "\nTo: " + to;
    }

}
