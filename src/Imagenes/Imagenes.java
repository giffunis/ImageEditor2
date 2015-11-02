package Imagenes;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartPanel;

import javax.swing.filechooser.FileFilter;



import ImageEditor2.*;

public class Imagenes{
	
	static final int SIZE = 256;
	static final String HISTO_ABSO = "Histograma Absoluto";
	static final String HISTO_ACUM = "Histograma Acumulado";
	JPanel panel;
	public BufferedImage imagenReal;
	ImageEditor2 api;
	public JInternalFrame internalFrame;
	

	
	public Imagenes(ImageEditor2 api){
		this.api = api;
	}
	public Imagenes(ImageEditor2 api, BufferedImage imagen){
		this.api = api;
		this.imagenReal = imagen;
	}
	
	void init_internalFrame(){
		internalFrame = new JInternalFrame("imagen"+(api.imagenes.size() + 1) ,true,true,true,true);
		internalFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		internalFrame.setLayout(new BorderLayout());
	}
	
	void init_panel(){
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(imagenReal));
		panel = new JPanel();
		panel.add(label);
	}
	
	void empaquetarImagen(){
		init_internalFrame();
		init_panel();
		internalFrame.add(panel, BorderLayout.CENTER);	
		internalFrame.pack();
		internalFrame.setVisible(true);
		this.api.desktopPane.add(internalFrame);
		this.api.imagenes.addElement(this);
	}
	
	BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
	
	public void abrirImagen(){	
        BufferedImage auxImage = null;
        JFileChooser selector=new JFileChooser();
        selector.setDialogTitle("Seleccione una imagen");
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG & GIF & BMP & PNG", "jpg", "gif", "bmp", "png");
        selector.setFileFilter(filtroImagen);
        int flag = selector.showOpenDialog(null);
        if(flag == JFileChooser.APPROVE_OPTION){
            try {
                File imagenSeleccionada=selector.getSelectedFile();
                auxImage = ImageIO.read(imagenSeleccionada);
            } catch (Exception e) {
            	JOptionPane.showMessageDialog(new JFrame(), "Se produjo un error al cargar la imagen");
            }    
        }
        this.imagenReal = auxImage;  
        empaquetarImagen();
    }
	
	public void guardarImagen(){
        JFileChooser selector=new JFileChooser();
        selector.setDialogTitle("Guardar como..."); 
        FileFilter filter1 = new ExtensionFileFilter("JPG", new String[] { "JPG"});
        FileFilter filter2 = new ExtensionFileFilter("GIF", new String[] { "GIF"});
        FileFilter filter3 = new ExtensionFileFilter("BMP", new String[] { "BMP"});
        FileFilter filter4 = new ExtensionFileFilter("PNG", new String[] { "PNG"});
        
        selector.setAcceptAllFileFilterUsed(false);
        selector.addChoosableFileFilter(filter1);
        selector.addChoosableFileFilter(filter2);
        selector.addChoosableFileFilter(filter3);
        selector.addChoosableFileFilter(filter4);
        
        int flag=selector.showSaveDialog(null);

        if(flag==JFileChooser.APPROVE_OPTION){
            try {
            	String name = selector.getSelectedFile().getAbsolutePath();
            	String name1 = selector.getFileFilter().getDescription();
            	String ext = "png";
            	if (name1.equals("JPG")){
                    ext = "jpg";
                    name = name +"."+ ext;
                    System.out.println(name);
                }
                else if(name1.equals("PNG")){
                    ext = "png";
                    name = name +"."+ ext;
                    System.out.println(name);
                }
                else if(name1.equals("GIF")){
                    ext = "gif";
                    name = name +"."+ ext;
                    System.out.println(name);
                }
                else if(name1.equals("BMP")){
                    ext = "bmp";
                    name = name +"."+ ext;
                    System.out.println(name);
                }
                else if(name1.equals("All Files")){     
                    System.out.println(name);
                }
                else{
                	JOptionPane.showMessageDialog(new JFrame(), "Error guardando la imagen");
                }
            	try{
            		ImageIO.write(imagenReal, ext, new File(name));
            	} catch(Exception e){
            		JOptionPane.showMessageDialog(new JFrame(), "Error guardando la imagen");
            	}	  	
            } catch (Exception e) {
            	JOptionPane.showMessageDialog(new JFrame(), "Error guardando la imagen");
            }          
        }  	
    }
	
	public void escalaGrises(){
		BufferedImage outImage = deepCopy(this.imagenReal);
        int media;
        Color colorAux;
                 
        for( int i = 0; i < outImage.getWidth(); i++ ){
            for( int j = 0; j < outImage.getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorAux=new Color(outImage.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                media =(int)(0.222*colorAux.getRed()+0.707*colorAux.getGreen()+0.071*colorAux.getBlue());
                colorAux = new Color(media,media,media);
                //Asignamos el nuevo valor al BufferedImage
                outImage.setRGB(i, j,colorAux.getRGB());
            }
        }
        Imagenes newImagen = new Imagenes(this.api,outImage);
        newImagen.empaquetarImagen();
    }
	
	public Vector<Integer> histogramaAbsoluto(){  
    	Vector<Integer> hist = new Vector<Integer>(0);
    	Color aux;
    	// Inicializamos el vector o tabla.
    	for(int i = 0; i < SIZE; i++)
    		hist.addElement(0);
    	
    	for( int i = 0; i < imagenReal.getWidth(); i++ )
            for( int j = 0; j < imagenReal.getHeight(); j++ ){
                aux = new Color(this.imagenReal.getRGB(i, j));
                hist.set(aux.getRed(),hist.get(aux.getRed()) + 1);
            }
    	return hist;
    }
	
	public void createGraphic(String name, Vector<Integer> vectorHist){
		Histograma histo = new Histograma(name,vectorHist);
		ChartPanel panel = new ChartPanel(histo.grafica);
		JInternalFrame ventana = new JInternalFrame(name,true,true,true,true);
		ventana.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		ventana.add(panel);
		ventana.pack();
		ventana.setVisible(true);
		this.api.desktopPane.add(ventana);
	}
	
	public void graficaHistogramaAbsoluto(int pos){
		Vector<Integer> vectorHist = histogramaAbsoluto();
		createGraphic(HISTO_ABSO + ": imagen " + (pos + 1),vectorHist);	
	}
	
	public void graficaHistogramaAcumulado(int pos){
		Vector<Integer> vectorHist = histogramaAbsoluto();
		Vector<Integer> vectorAcum = new Vector<Integer>(0);
    	
		vectorAcum.addElement(vectorHist.get(0));
    	for(int i = 1; i< vectorHist.size(); i++)
    		vectorAcum.addElement(vectorAcum.get(i - 1) + vectorHist.get(i));
		createGraphic(HISTO_ACUM + ": imagen " + (pos + 1),vectorAcum);	
	}
	
}
