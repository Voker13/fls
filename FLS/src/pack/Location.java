package pack;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Location {

    private int duration;
    private float lat;
    private float Long;
    private String name;
    private double angle;

	public Location(){
	
    }
    
    public Location(int Duration, float Lat, float Long, String Name) {
	this.duration = Duration;
	this.lat = Lat;
	this.Long = Long;
	while(Long > 20) {
	    Long /= 10;
	}
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
	while (Long > 20) {
	    Long /= 10;
	}
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
    
    public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
    
    public String toString() {
	return "Name: " + name + "\nLat: " + lat + "\nLong: " + Long + "\nDuration: " + duration + "\n";
    }
}
