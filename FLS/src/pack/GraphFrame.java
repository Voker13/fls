package pack;

import javax.swing.JFrame;

public class GraphFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private int width = 1016;
    private int height = 1040;
    
    public GraphFrame() {
	this.setLayout(null);
	this.setSize(width, height);
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	MyPanel panel = new MyPanel();
	panel.setBounds(0, 0, 1000, 1000);	
	
	this.setContentPane(panel);
	
	this.revalidate();
    }

}
