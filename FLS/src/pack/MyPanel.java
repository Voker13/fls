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
    private boolean showNumbers = true;
    private boolean showInterlace = true;
    private int frameWidth;
    private int frameHeight;
    private ArrayList<ArrayList<Tour>> tours;
    JCheckBox box1 = new JCheckBox("Strategy: Closest Location");
    JCheckBox box2 = new JCheckBox("Strategy: 'Circle'");
    JCheckBox box3 = new JCheckBox("Strategy: Pizza");
    JCheckBox box4 = new JCheckBox("Strategy: Random");
    JCheckBox box5 = new JCheckBox("Strategy: Slices (10)");
    JCheckBox box6 = new JCheckBox("Strategy: Far To Close");
    JCheckBox box7 = new JCheckBox("Strategy: Slice Plus Far");
    JCheckBox box8 = new JCheckBox("Strategy: Slice Plus Far Plus ForeC");

    private static final long serialVersionUID = 1L;

    public MyPanel(int width, int height, ArrayList<ArrayList<Tour>> tours) {
	this.tours = tours;
	this.setBackground(Color.WHITE);
	this.setLayout(null);
	this.setVisible(true);
	this.setFrameWidth(width - 16);
	this.setFrameHeight(height - 34);

	box1.setSize(new Dimension(200, 50));
	box1.setLocation(width - 230, height / 2 - 350);
	box1.setBackground(Color.WHITE);
	box1.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box1);

	box2.setSize(new Dimension(200, 50));
	box2.setLocation(width - 230, height / 2 - 300);
	box2.setBackground(Color.WHITE);
	box2.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box2);

	box3.setSize(new Dimension(200, 50));
	box3.setLocation(width - 230, height / 2 - 250);
	box3.setBackground(Color.WHITE);
	box3.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box3);

	box4.setSize(new Dimension(200, 50));
	box4.setLocation(width - 230, height / 2 - 200);
	box4.setBackground(Color.WHITE);
	box4.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box4);

	box5.setSize(new Dimension(200, 50));
	box5.setLocation(width - 230, height / 2 - 150);
	box5.setBackground(Color.WHITE);
	box5.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box5);

	box6.setSize(new Dimension(200, 50));
	box6.setLocation(width - 230, height / 2 - 100);
	box6.setBackground(Color.WHITE);
	box6.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box6);

	box7.setSize(new Dimension(200, 50));
	box7.setLocation(width - 230, height / 2 - 50);
	box7.setBackground(Color.WHITE);
	box7.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box7);

	box8.setSize(new Dimension(200, 50));
	box8.setLocation(width - 230, height / 2 - 0);
	box8.setBackground(Color.WHITE);
	box8.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		repaint();
	    }
	});
	this.add(box8);

	/*
	 * JButton showTours = new JButton("Show Tours"); showTours.setSize(new
	 * Dimension(200,50)); showTours.setLocation(width - 230,height/2 - 50);
	 * showTours.addActionListener(new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent arg0) { drawTours =
	 * !drawTours; repaint(); }
	 * 
	 * }); this.add(showTours);
	 */

	JButton showLocations = new JButton("Show Locations");
	showLocations.setSize(new Dimension(200, 50));
	showLocations.setLocation(width - 230, height / 2 + 75);
	showLocations.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		drawLocations = !drawLocations;
		repaint();
	    }
	});
	this.add(showLocations);

	JButton showNumbersButton = new JButton("Show Numbers");
	showNumbersButton.setSize(new Dimension(200, 50));
	showNumbersButton.setLocation(width - 230, height / 2 + 150);
	showNumbersButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		showNumbers = !showNumbers;
		repaint();
	    }
	});
	this.add(showNumbersButton);
    
    
    //TODO new button interlace on/off
	JButton showInterlaceButton = new JButton("Show Interlace");
	showNumbersButton.setSize(new Dimension(200, 50));
	showNumbersButton.setLocation(width - 230, height / 2 + 225);
	showNumbersButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		showInterlace = !showInterlace;
		repaint();
	    }
	});
	this.add(showNumbersButton);
    }
    

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	if (drawLocations) {
	    g.setColor(Color.BLACK);
	    for (Location location : Main.getLocCopy()) {
		g.fillOval(20 + (int) ((location.getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))),
			18 + (int) ((location.getLong() - Main.getMinLong()) * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())), 4, 4);
		g.drawOval(20 + (int) ((location.getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))),
			18 + (int) ((location.getLong() - Main.getMinLong()) * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())), 4, 4);
	    }
	    g.setColor(Color.RED);
	    g.fillOval(20 + (int) ((Main.getDepot().getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))),
		    18 + (int) ((Main.getDepot().getLong() - Main.getMinLong()) * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())), 4, 4);
	    g.drawOval(20 + (int) ((Main.getDepot().getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))),
		    18 + (int) ((Main.getDepot().getLong() - Main.getMinLong()) * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())), 4, 4);
	}
	if (box1.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    for (Tour tour : tours.get(0)) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    if (showNumbers) {
			g.drawString((cnt) + "", 15 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))),
				15 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    }
		    g.drawLine(20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())), 20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour
			    .getTourStops().get(i + 1).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    cnt++;
		}
	    }
	}
	if (box2.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    for (Tour tour : tours.get(1)) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    if (showNumbers) {
			g.drawString((cnt) + "", 15 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))),
				15 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    }
		    g.drawLine(20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())), 20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour
			    .getTourStops().get(i + 1).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    cnt++;
		}
	    }
	}
	if (box3.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    for (Tour tour : tours.get(2)) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    if (showNumbers) {
			g.drawString((cnt) + "", 15 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))),
				15 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    }
		    g.drawLine(20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())), 20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour
			    .getTourStops().get(i + 1).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    cnt++;
		}
	    }
	}
	if (box4.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    for (Tour tour : tours.get(3)) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    if (showNumbers) {
			g.drawString((cnt) + "", 15 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))),
				15 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    }
		    g.drawLine(20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())), 20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour
			    .getTourStops().get(i + 1).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    cnt++;
		}
	    }
	}
	if (box5.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    for (Tour tour : tours.get(4)) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    if (showNumbers) {
			g.drawString((cnt) + "", 15 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))),
				15 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    }
		    g.drawLine(20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())), 20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour
			    .getTourStops().get(i + 1).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    cnt++;
		}
	    }
	}
	if (box6.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    for (Tour tour : tours.get(5)) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    if (showNumbers) {
			g.drawString((cnt) + "", 15 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))),
				15 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    }
		    g.drawLine(20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())), 20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour
			    .getTourStops().get(i + 1).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    cnt++;
		}
	    }
	}
	if (box7.isSelected()) {
	    g.setColor(Color.BLACK);
	    int cnt = 1;
	    for (Tour tour : tours.get(6)) {
		for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
		    if (showNumbers) {
			g.drawString((cnt) + "", 15 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))),
				15 + (int) ((tour.getTourStops().get(i + 1).getLong() - Main.getMinLong()) * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    }
		    g.drawLine(20 + (int) ((tour.getTourStops().get(i).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour.getTourStops().get(i).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())), 20 + (int) ((tour.getTourStops().get(i + 1).getLat() - Main.getMinLat()) * (frameWidth - 250) / ((Main.getMaxLat() - Main.getMinLat()))), 20 + (int) ((tour
			    .getTourStops().get(i + 1).getLong() - Main.getMinLong())
			    * (frameHeight - 40) / (Main.getMaxLong() - Main.getMinLong())));
		    cnt++;
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
}