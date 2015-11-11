package pack;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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

	@XmlAttribute(name = "Duration")
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@XmlAttribute(name = "Distance")
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@XmlAttribute(name = "From")
	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	@XmlAttribute(name = "To")
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
