package pack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class MyPanel extends JPanel {

    private boolean drawLocations = true;
    private int frameWidth;
    private int frameHeight;
    private ArrayList<ArrayList<Tour>> tours;
    JCheckBox box1 = new JCheckBox("Strategy 1");
    JCheckBox box2 = new JCheckBox("Strategy 2");
    JCheckBox box3 = new JCheckBox("Strategy 3");
    JCheckBox box4 = new JCheckBox("Strategy 4");

    private static final long serialVersionUID = 1L;

    public MyPanel(int width, int height, ArrayList<ArrayList<Tour>> tours) {
	this.tours = tours;
	this.setBackground(Color.WHITE);
	this.setLayout(null);
	this.setVisible(true);
	this.setFrameWidth(width - 16);
	this.setFrameHeight(height - 34);
	
	box1.setSize(new Dimension(200,50));
	box1.setLocation(width-230,height/2 - 200);
	box1.setBackground(Color.WHITE);
	box1.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box1);
	
	
	box2.setSize(new Dimension(200,50));
	box2.setLocation(width-230,height/2 - 150);
	box2.setBackground(Color.WHITE);
	box2.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box2);
	
	box3.setSize(new Dimension(200,50));
	box3.setLocation(width-230,height/2 - 100);
	box3.setBackground(Color.WHITE);
	box3.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box3);
	
	box4.setSize(new Dimension(200,50));
	box4.setLocation(width-230,height/2 - 50);
	box4.setBackground(Color.WHITE);
	box4.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box4);
	
	/*
	JButton showTours = new JButton("Show Tours");
	showTours.setSize(new Dimension(200,50));
	showTours.setLocation(width - 230,height/2 - 50);
	showTours.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		drawTours = !drawTours;
		repaint();
	    }
	    
	});
	this.add(showTours);
	*/
	
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
	if (box1.isSelected()) {
	    g.setColor(Color.BLACK);
	    for (Tour tour : tours.get(0)) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    g.drawLine(20+(int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),20+ (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())),20+ (int) ((tour.getTourStops()
			    .get(i + 1).getLat() - Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),20+ (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())));
		}
	    }
	}
	if (box2.isSelected()) {
	    g.setColor(Color.BLACK);
	    for (Tour tour : tours.get(1)) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    g.drawLine(20+(int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),20+ (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())),20+ (int) ((tour.getTourStops()
			    .get(i + 1).getLat() - Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),20+ (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())));
		}
	    }
	}
	if (box3.isSelected()) {
	    g.setColor(Color.BLACK);
	    for (Tour tour : tours.get(2)) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    g.drawLine(20+(int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),20+ (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())),20+ (int) ((tour.getTourStops()
			    .get(i + 1).getLat() - Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),20+ (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())));
		}
	    }
	}
	if (box4.isSelected()) {
	    g.setColor(Color.BLACK);
	    for (Tour tour : tours.get(3)) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    g.drawLine(20+(int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),20+ (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())),20+ (int) ((tour.getTourStops()
			    .get(i + 1).getLat() - Main.getMinLat()) * (frameWidth-250)/((Main.getMaxLat()-Main.getMinLat()))),20+ (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight-40)/(Main.getMaxLong()-Main.getMinLong())));
		}
	    }
	}
	if (drawLocations) {
	    g.setColor(Color.BLACK);
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