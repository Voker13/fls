package pack;

import javax.swing.JFrame;

public class GraphFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public GraphFrame() {
	this.setLayout(null);
	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	MyPanel panel = new MyPanel(this.getWidth(),this.getHeight());
	
	this.setContentPane(panel);
	
	this.revalidate();
    }

}
