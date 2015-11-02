package Imagenes;

import java.util.Vector;

import javax.swing.JInternalFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import ImageEditor2.ImageEditor2;

@SuppressWarnings("serial")
public class Histograma extends JInternalFrame {

	ImageEditor2 api;
	String nombre;
	Vector<Integer> hist;
	public JFreeChart grafica;
	DefaultCategoryDataset datos = new DefaultCategoryDataset();
	
	public Histograma(ImageEditor2 api, String nombre_histo, Vector<Integer> hist){
		this.api = api;
		this.nombre = nombre_histo;
		this.hist = hist;
		initComponents();
		//JOptionPane.showMessageDialog(new JFrame(), nombre_histo);
	}
	
	void initComponents(){
		String aux ="";
		for(int i = 0; i < this.hist.size();i++){
			aux = String.valueOf(i);
			datos.addValue(this.hist.get(i),"n pixels",aux);
		}
		grafica = ChartFactory.createBarChart(this.nombre, "Color", "Pixels", datos, PlotOrientation.VERTICAL, false, true, false);
	}
}