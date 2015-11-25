package pack;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private GraphFrame parent;
    JCheckBox box1 = new JCheckBox("Strategy: Closest Location");
    JCheckBox box2 = new JCheckBox("Strategy: 'Circle'");
    JCheckBox box3 = new JCheckBox("Strategy: Pizza");
    JCheckBox box4 = new JCheckBox("Strategy: Random");
    JCheckBox box5 = new JCheckBox("Strategy: Slices (10)");
    JCheckBox box6 = new JCheckBox("Strategy: Far To Close");
    JCheckBox box7 = new JCheckBox("Strategy: Slice Plus Far");

    
    public ControlPanel(int width, int height, GraphFrame graphFrame) {
	this.setLayout(null);
	this.setSize(new Dimension(width,height));
	this.setBackground(Color.WHITE);
	this.parent = graphFrame;	

	box1.setSize(new Dimension(200, 50));
	box1.setLocation(width - 230, height / 2 - 350);
	box1.setBackground(Color.WHITE);
	box1.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		parent.repaint();
		parent.requestFocus();
	    }
	});
	this.add(box1);

	box2.setSize(new Dimension(200, 50));
	box2.setLocation(width - 230, height / 2 - 300);
	box2.setBackground(Color.WHITE);
	box2.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		parent.repaint();
		parent.requestFocus();
	    }
	});
	this.add(box2);

	box3.setSize(new Dimension(200, 50));
	box3.setLocation(width - 230, height / 2 - 250);
	box3.setBackground(Color.WHITE);
	box3.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		parent.repaint();
		parent.requestFocus();
	    }
	});
	this.add(box3);

	box4.setSize(new Dimension(200, 50));
	box4.setLocation(width - 230, height / 2 - 200);
	box4.setBackground(Color.WHITE);
	box4.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		parent.repaint();
		parent.requestFocus();
	    }
	});
	this.add(box4);

	box5.setSize(new Dimension(200, 50));
	box5.setLocation(width - 230, height / 2 - 150);
	box5.setBackground(Color.WHITE);
	box5.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		parent.repaint();
		parent.requestFocus();
	    }
	});
	this.add(box5);

	box6.setSize(new Dimension(200, 50));
	box6.setLocation(width - 230, height / 2 - 100);
	box6.setBackground(Color.WHITE);
	box6.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		parent.repaint();
		parent.requestFocus();
	    }
	});
	this.add(box6);
	
	box7.setSize(new Dimension(200, 50));
	box7.setLocation(width - 230, height / 2 - 50);
	box7.setBackground(Color.WHITE);
	box7.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		parent.repaint();
		parent.requestFocus();
	    }
	});
	this.add(box7);
	
	JButton showLocations = new JButton("Show Locations");
	showLocations.setSize(new Dimension(200, 50));
	showLocations.setLocation(width - 230, height / 2 + 25);
	showLocations.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		GraphFrame.flipLocations();
		parent.repaint();
		parent.requestFocus();
	    }
	});
	this.add(showLocations);

	JButton showNumbersButton = new JButton("Show Numbers");
	showNumbersButton.setSize(new Dimension(200, 50));
	showNumbersButton.setLocation(width - 230, height / 2 + 100);
	showNumbersButton.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		GraphFrame.flipNumbers();
		parent.repaint();
		parent.requestFocus();
	    }
	});
	this.add(showNumbersButton);
    }

}