package pack;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Location {

    private int duration;
    private float lat;
    private float Long;
    private String name;

    public Location(){
	
    }
    
    public Location(int Duration, float Lat, float Long, String Name) {
	this.duration = Duration;
	this.lat = Lat;
	this.Long = Long;
	this.name = Name;
    }
    
    @XmlAttribute(name="Duration")
    public int getDuration() {
	return duration;
    }

    public void setDuration(int duration) {
	this.duration = duration;
    }

    @XmlAttribute(name="Lat")
    public float getLat() {
	return lat;
    }

    public void setLat(float lat) {
	this.lat = lat;
    }

    @XmlAttribute(name="Long")
    public float getLong() {
	return Long;
    }

    public void setLong(float Long) {
	this.Long = Long;
    }

    @XmlAttribute(name="Name")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
    
    public String toString() {
	return "Name: " + name + "\nLat: " + lat + "\nLong: " + Long + "\nDuration: " + duration + "\n";
    }
}
