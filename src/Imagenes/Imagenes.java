package Imagenes;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.image.*;
import java.io.File;
import java.util.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartPanel;

import BarraInferior.JStatusBar;

import javax.swing.filechooser.FileFilter;




import CustomsMouseListeners.ImagenesOnClick;
import ImageEditor2.*;

public class Imagenes{
	
	/*
	 * Constants
	 */
	static final int SIZE = 256;
	static final String HISTO_ABSO = "Histograma Absoluto";
	static final String HISTO_ACUM = "Histograma Acumulado";
	
	/*
	 * 
	 */
	public JPanel panel;
	public BufferedImage imagenReal;
	public JInternalFrame internalFrame;
	public JLabel label;
	public JStatusBar statusBar;
	private ImageEditor2 api;
	private Vector<Integer> histo;
	private Vector<Integer> acumulado;
	private String nombre;
	
	/*
	 * Constructor. It needs the main application of ImageEditor2
	 */
	public Imagenes(ImageEditor2 api){
		this.api = api;
	}
	
	/*
	 * Another Constructor. Uses it when you have to pass a BufferedImage
	 */
	public Imagenes(ImageEditor2 api, BufferedImage imagen){
		this.api = api;
		this.imagenReal = imagen;
	}
	
// -----------------------------------INIT-------------------------------------------
	private void initStatusBar(){
		int alto = this.imagenReal.getHeight();
		int ancho = this.imagenReal.getWidth();
		String imageSize = "Tamaño: " + Integer.toString(ancho) + " x " + Integer.toString(alto);
		// Cálculo del rango de valores:
		int i = 0, min = 0, max = 0;
		do{
			min = i;
			i++;
		}while(this.histo.get(i) == 0);
		i = SIZE -1;
		do{
			max = i;
			i--;
		}while(this.histo.get(i) == 0);
		String imageMinMax = "Min: " + min + "; Max: " + max;	
		
		String typeImage = this.nombre.substring(this.nombre.lastIndexOf('.') + 1).trim();
		
		// Create the list of secondary components
		List<JComponent> secondaryComponents = new ArrayList<JComponent>();
		secondaryComponents.add( new JLabel("Type: " + typeImage));
		secondaryComponents.add( new JLabel(imageMinMax) );
		secondaryComponents.add( new JLabel("Pos: null"));
		
		statusBar = new JStatusBar(new JLabel(imageSize),secondaryComponents);
	}
	
	private void init_internalFrame(){
		internalFrame = new JInternalFrame("imagen"+(api.imagenes.size() + 1) ,true,true,true,true);
		internalFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		internalFrame.setLayout(new BorderLayout());

	}
	
	private void init_panel(){
		label = new JLabel();
		label.setIcon(new ImageIcon(imagenReal));	
		panel = new JPanel(new GridBagLayout());
		panel.add(label);
		ImagenesOnClick listImage = new ImagenesOnClick(this);
        panel.addMouseListener(listImage);
	}
	
	private void initHistogramaAbsoluto(){  
    	histo = new Vector<Integer>(0);
    	Color aux;
    	// Inicializamos el vector o tabla.
    	for(int i = 0; i < SIZE; i++)
    		histo.addElement(0);
    	
    	for( int i = 0; i < imagenReal.getWidth(); i++ )
            for( int j = 0; j < imagenReal.getHeight(); j++ ){
                aux = new Color(this.imagenReal.getRGB(i, j));
                histo.set(aux.getRed(),histo.get(aux.getRed()) + 1);
            }
    }
	
	private void initHistogramaAcumulado(){
		acumulado = new Vector<Integer>(0);
		acumulado.addElement(this.histo.get(0));
    	for(int i = 1; i< histo.size(); i++)
    		acumulado.addElement(acumulado.get(i - 1) + histo.get(i));
	}
	
	/*
	 * this function build the final Object
	 */
	private void empaquetarImagen(){
		init_internalFrame();
		init_panel();
		initHistogramaAbsoluto();
		initHistogramaAcumulado();
		initStatusBar();
		internalFrame.add(panel);
		internalFrame.add(statusBar,BorderLayout.SOUTH);
		internalFrame.pack();
		internalFrame.setVisible(true);
		this.api.desktopPane.add(internalFrame);
		this.api.imagenes.addElement(this);
	}
	
	// -----------------------------------------------------GETS----------------------------------------
	public Vector<Integer> getHistoAbsoluto(){return this.histo;}
	public Vector<Integer> getHistoAcumulativo(){return this.acumulado;}
	
	
	private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
	
	//---------------------------------------------POINT OPERATIONS-----------------------------------
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
                this.nombre = imagenSeleccionada.getName();
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
		createGraphic(HISTO_ABSO + ": imagen " + (pos + 1),this.histo);	
	}
	
	public void graficaHistogramaAcumulado(int pos){
		createGraphic(HISTO_ACUM + ": imagen " + (pos + 1),this.acumulado);	
	}
	
}
