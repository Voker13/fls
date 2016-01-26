package pack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 * This is the panel where the Tours get drawn
 *
 */
public class DrawPanel extends JPanel {

    private int frameWidth;
    private int frameHeight;
    private ArrayList<ArrayList<Tour>> tours;
    private double zoomFactor = 1.0;
    private ControlPanel panel2;

    private static final long serialVersionUID = 1L;

    public DrawPanel(int width, int height, ArrayList<ArrayList<Tour>> tours, ControlPanel panel2) {
	this.tours = tours;
	this.setBackground(Color.WHITE);
	this.setLayout(null);
	this.setVisible(true);
	this.setFrameWidth(width - 16);
	this.setFrameHeight(height - 34);
	this.setPreferredSize(new Dimension(width, height));
	this.panel2 = panel2;
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	if (GraphFrame.getDrawLocations()) {
	    g.setColor(Color.BLACK);
	    for (Location location : Main.getLocCopy()) {
		g.fillOval((int) (zoomFactor * (18 + (int) ((location.getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
			(int) (zoomFactor * (18 + (int) ((location.getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * 4), (int) (zoomFactor * 4));
		g.drawOval((int) (zoomFactor * (18 + (int) ((location.getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
			(int) (zoomFactor * (18 + (int) ((location.getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * 4), (int) (zoomFactor * 4));
	    }
	    g.setColor(Color.RED);
	    g.fillOval((int) (zoomFactor * (18 + (int) ((Main.getDepot().getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (18 + (int) ((Main.getDepot().getLong() - Main.getMinLong())
		    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * 4), (int) (zoomFactor * 4));
	    g.drawOval((int) (zoomFactor * (18 + (int) ((Main.getDepot().getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (18 + (int) ((Main.getDepot().getLong() - Main.getMinLong())
		    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * 4), (int) (zoomFactor * 4));
	}
	if (panel2.box1.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    if (GraphFrame.getInterlace()) {
	    	for (Tour tour : tours.get(0)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	    else {
	    	for (Tour tour : tours.get(8)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	}
	if (panel2.box2.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    if (GraphFrame.getInterlace()) {
	    	for (Tour tour : tours.get(1)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	    else {
	    	for (Tour tour : tours.get(9)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	}
	if (panel2.box3.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    if (GraphFrame.getInterlace()) {
	    	for (Tour tour : tours.get(2)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	    else {
	    	for (Tour tour : tours.get(10)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	}
	if (panel2.box4.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    if (GraphFrame.getInterlace()) {
	    	for (Tour tour : tours.get(3)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	    else {
	    	for (Tour tour : tours.get(11)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	}
	if (panel2.box5.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    if (GraphFrame.getInterlace()) {
	    	for (Tour tour : tours.get(4)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	    else {
	    	for (Tour tour : tours.get(12)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	}
	if (panel2.box6.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    if (GraphFrame.getInterlace()) {
	    	for (Tour tour : tours.get(5)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	    else {
	    	for (Tour tour : tours.get(13)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	}
	if (panel2.box7.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    if (GraphFrame.getInterlace()) {
	    	for (Tour tour : tours.get(6)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	    else {
	    	for (Tour tour : tours.get(14)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	}
	if (panel2.box8.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    if (GraphFrame.getInterlace()) {
	    	for (Tour tour : tours.get(7)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
	    else {
	    	for (Tour tour : tours.get(15)) {
	    		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
	    		    if (GraphFrame.getShowNumbers()) {
	    			g.drawString((cnt) + "", (int) (zoomFactor * (14 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (14 + (int) ((tour
	    				.getTourStops().get(i + 1).getLong() - Main.getMinLong())
	    				* (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    }
	    		    g.drawLine((int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i)
	    			    .getLong() - Main.getMinLong())
	    			    * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))), (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth) / ((Main.getMaxLat() - Main.getMinLat()))))),
	    			    (int) (zoomFactor * (20 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight) / (Main.getMaxLong() - Main.getMinLong())))));
	    		    cnt++;
	    		}
	    	    }
	    }
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

    public void incZoomFactor() {
	if (zoomFactor <= 1000) {
	    zoomFactor *= 1.1;
	    repaint();
	}
    }

    public void decrZoomFactor() {
	if (zoomFactor > 1) {
	    zoomFactor *= 0.9;
	    repaint();
	}
	if (zoomFactor < 1) {
	    zoomFactor = 1;
	}
    }

    public double getZoomFactor() {
	return zoomFactor;
    }

}