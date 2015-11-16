package TramosLineal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class MyDrawPanel extends JPanel {
	private Vector<Point> points;

	MyDrawPanel(){
		super();
		setPreferredSize( new Dimension(300,300) );
		setBackground(Color.BLUE);
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
		this.setVisible(true);
		//this.setAlignmentX(SwingConstants.LEFT);
		this.points = new Vector<Point>(0);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, posY(0), 255, 255);
		if(points.size() != 0){
			g2d.setColor(Color.RED);
			for(int i = 0; i < points.size()/2; i++){
				g2d.drawLine(points.get(i*2).x, posY(points.get(i*2).y), points.get(i*2 + 1).x, posY(points.get(i*2 + 1).y));
			}
		}
	}
	
	private int posY(int y) {
		return 300 - y;
	}
	
	public void draw(Vector<Point> points){
		this.points = points;
		repaint();
	}
	
}
