package pack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MyPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public MyPanel() {
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.setBounds(0, 0, 500, 500);
		this.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		System.out.println("paintComponent called()");
		for (Tour tour : Main.allTours) {
			for (int i = 0; i < tour.getTourStops().size() - 1; i++) {
				g.drawLine((int) ((tour.getTourStops().get(i).getLat() - Main.minLat) * 5000),
						(int) ((tour.getTourStops().get(i).getLong() - Main.minLong) * 4000),
						(int) ((tour.getTourStops().get(i + 1).getLat() - Main.minLat) * 5000),
						(int) ((tour.getTourStops().get(i + 1).getLong() - Main.minLong) * 4000));
			}
		}
	}
}