package TramosLineal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyDrawPanel extends JPanel {
	private Vector<Point> points;

	MyDrawPanel(Vector<Point> points){
		super();
		this.setSize(300, 300);
		setBackground(Color.BLUE);
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
		this.setVisible(true);
		this.points = points;
	}
	MyDrawPanel(){
		super();
		this.setSize(400, 400);
		setBackground(Color.BLUE);
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
		this.setVisible(true);
		this.points = new Vector<Point>(0);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
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
