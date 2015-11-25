package pack;

public class Square {

	@SuppressWarnings("unused")
	private static final double SQUARESIZE = 0.03;
	private float xCoordinate;
	private float yCoordinate;
	private int plenty;

	public Square() {

	}

	public float getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(float xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public float getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(float yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public int getPlenty() {
		return plenty;
	}

	public void setPlenty(int plenty) {
		this.plenty = plenty;
	}

}
