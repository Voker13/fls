package pack;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class GraphFrame extends JFrame implements MouseWheelListener, KeyListener, MouseListener {

    private static final long serialVersionUID = 1L;
    private static boolean drawLocations = true;
    private static boolean showNumbers = true;
    DrawPanel drawPanel;
    JScrollPane scroller;
    int width;
    int height;
    boolean ctrl = false;

    public GraphFrame(ArrayList<ArrayList<Tour>> tours) {
	this.setLayout(null);
	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	width = this.getWidth() - 230;
	height = this.getHeight() - 38;

	int width2 = this.getWidth() - width;
	int height2 = this.getHeight() - 38;

	ControlPanel controlPanel = new ControlPanel(width2, height2, this);
	controlPanel.setLocation(width, 0);

	drawPanel = new DrawPanel(width-18, height-18, tours, controlPanel);

	scroller = new JScrollPane(drawPanel);
	scroller.setBounds(0, 0, width, height);
	scroller.addMouseWheelListener(this);
	scroller.addKeyListener(this);
	scroller.getVerticalScrollBar().setUnitIncrement(20);
	scroller.getHorizontalScrollBar().setUnitIncrement(20);
	scroller.setFocusable(true);

	this.add(controlPanel);
	this.add(scroller);
	this.addMouseWheelListener(this);
	this.addKeyListener(this);
	this.revalidate();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
	if (ctrl) {
	    int notches = e.getWheelRotation();
	    int x = e.getX();
	    int y = e.getY();
	    if (notches < 0) {
		drawPanel.incZoomFactor();
		drawPanel.setSize((int) (width * drawPanel.getZoomFactor()), (int) (width * drawPanel.getZoomFactor()));
		drawPanel.setPreferredSize(new Dimension((int) (width * drawPanel.getZoomFactor()), (int) (height * drawPanel.getZoomFactor())));
	    } else {
		drawPanel.decrZoomFactor();
		drawPanel.setSize((int) (width * drawPanel.getZoomFactor()), (int) (width * drawPanel.getZoomFactor()));
		drawPanel.setPreferredSize(new Dimension((int) (width * drawPanel.getZoomFactor()), (int) (height * drawPanel.getZoomFactor())));
	    }
	}
	else {
	    
	}
    }

    public static void flipLocations() {
	drawLocations = !drawLocations;
    }

    public static void flipNumbers() {
	showNumbers = !showNumbers;
    }

    public static boolean getDrawLocations() {
	return drawLocations;
    }

    public static boolean getShowNumbers() {
	return showNumbers;
    }

    @Override
    public void keyPressed(KeyEvent e) {
	if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
	    //System.err.println("CTRL pressed");
	    ctrl = true;
	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
	//System.err.println("CTRL released");
	ctrl = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
    }
}
