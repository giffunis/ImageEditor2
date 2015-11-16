package TramosLineal;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MyLeftPanel extends JPanel{

	private Vector<JTextField> fields;
	public Vector <Point> points;
	private int nTramos;
	
	MyLeftPanel(int nTramos){
		super(new GridLayout(nTramos + 1,3));
		this.setVisible(true);
		this.setSize(200, 400);
		this.nTramos = nTramos;
		//this.setAlignmentX(SwingConstants.LEFT);
		initPrivateComponents();
	}

	private void initPrivateComponents() {
		fields = new Vector<JTextField>(0);
		
		add(new JLabel("Tramo"));
		add(new JLabel("Punto 1"));
		add(new JLabel("Punto 2"));
		
		for(int i = 0; i < nTramos; i++){
			add(new JLabel(Integer.toString(i + 1)));
			fields.addElement(new JTextField());
			add(fields.get(fields.size() - 1));
			fields.addElement(new JTextField());
			add(fields.get(fields.size() - 1));
		}
	}
	
	private Point sTringToPoint(String cadena){
		String y = cadena.substring(cadena.lastIndexOf(',') + 1).trim();
		String x = cadena.substring(0, cadena.length() - (1 + y.length()));
		return new Point(Integer.parseInt(x),Integer.parseInt(y));
	}
	
	private void calculatePoints(){
		points = new Vector<Point>(0);
		
		for(int i = 0; i < fields.size(); i++){
			points.addElement(sTringToPoint(fields.get(i).getText()));
			System.out.println(points.get(i));
		}
	}
	
	public Vector<Point> getPoints(){
		calculatePoints();
		return points;
	}
}
