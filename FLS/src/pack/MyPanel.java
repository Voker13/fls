package pack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MyPanel extends JPanel {

    private boolean drawTours = true;
    private boolean drawLocations = true;
    private int frameWidth;
    private int frameHeight;

    private static final long serialVersionUID = 1L;

    public MyPanel(int width, int height) {
	this.setBackground(Color.WHITE);
	this.setLayout(null);
	this.setVisible(true);
	this.setFrameWidth(width - 16);
	this.setFrameHeight(height - 34);
	
	JButton showTours = new JButton("Show Tours");
	showTours.setSize(new Dimension(200,50));
	System.out.println(width + " " + height);
	showTours.setLocation(width - 230,height/2 - 50);
	showTours.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		drawTours = !drawTours;
		repaint();
	    }
	    
	});
	this.add(showTours);
	
	JButton showLocations = new JButton("Show Locations");
	showLocations.setSize(new Dimension(200,50));
	showLocations.setLocation(width - 230,height/2 + 25);
	showLocations.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		drawLocations = !drawLocations;
		repaint();
	    }
	});
	this.add(showLocations);
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	if (drawTours) {
	    g.setColor(Color.BLACK);
	    System.out.println("paintComponent called()");
	    for (Tour tour : Main.getAllTours()) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    g.drawLine(20+(int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),20+ (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())),20+ (int) ((tour.getTourStops()
			    .get(i + 1).getLat() - Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),20+ (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())));
		}
	    }
	}
	if (drawLocations) {
	    g.setColor(Color.BLACK);
	    System.out.println("paintComponent called()");
	    for (Location location : Main.getLocCopy()) {
		g.fillOval(20+(int) ((location.getLat()-Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),18+ (int) ((location.getLong()-Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())), 4, 4);
		g.drawOval(20+(int) ((location.getLat()-Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),18+ (int) ((location.getLong()-Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())), 4, 4);
	    }
	    g.setColor(Color.RED);
	    g.fillOval(20+(int) ((Main.getDepot().getLat()-Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),18+ (int) ((Main.getDepot().getLong()-Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())), 4, 4);
	    g.drawOval(20+(int) ((Main.getDepot().getLat()-Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),18+ (int) ((Main.getDepot().getLong()-Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())), 4, 4);
	}
    }

    public int getFrameWidth() {
	return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
	this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
	return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
	this.frameHeight = frameHeight;
    }
}