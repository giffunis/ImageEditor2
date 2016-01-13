package Imagenes;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.io.File;
import java.util.*;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartPanel;
import BarraInferior.JStatusBar2;

import javax.swing.filechooser.FileFilter;




import CustomsMouseListeners.ImagenesOnClick;
import ImageEditor2.*;

public class Imagenes{
	
	static final int SIZE = 256;
	static final String HISTO_ABSO = "Histograma Absoluto";
	static final String HISTO_ACUM = "Histograma Acumulado";

	public JPanel panel;
	public BufferedImage imagenReal;
	public JInternalFrame internalFrame;
	public JLabel label;
	public JStatusBar2 statusBar;
	private ImageEditor2 api;
	private Vector<Integer> histo;
	private Vector<Integer> acumulado;
	private String nombre;
	public JLabel posXY;
	private int brillo;
	private int contraste;
	
	public Point origin;
	public Point end;
	
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
	private int minColor(){
		int i = 0, min = 0;
		do{
			i++;
			min = i;
		}while(this.histo.get(i) == 0);
		return min;
	}
	
	private int maxColor(){
		int i = SIZE -1, max = 0;
		do{
			max = i;
			i--;
		}while(this.histo.get(i) == 0);
		return max;
	}
	
	public String getMinMax(){
		int min = minColor(); 
		int max = maxColor();
		String imageMinMax = "[" + min + "," + max + "]";	
		return imageMinMax;
	}
	
	public String getImageType(){
		String typeImage ="";
		try{
			typeImage = this.nombre.substring(this.nombre.lastIndexOf('.') + 1).trim();
		} catch (Exception e) {
			typeImage = "no definied";
		}
		return typeImage;
	}
	
	public String getImageSize(){
		int alto = this.imagenReal.getHeight();
		int ancho = this.imagenReal.getWidth();
		String imageSize = Integer.toString(ancho) + " x " + Integer.toString(alto);
		return imageSize;
	}
	
	private void initStatusBar(){
		statusBar = new JStatusBar2(this);
		statusBar.setVisible(true);
	}
	
	
	private void init_internalFrame(){
		internalFrame = new JInternalFrame("imagen"+(api.imagenes.size() + 1) ,true,true,true,true);
		internalFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		internalFrame.setLayout(new BorderLayout());

	}
	
	private void init_panel(){
		label = new JLabel();
		label.setIcon(new ImageIcon(imagenReal));	
		ImagenesOnClick listImage = new ImagenesOnClick(this);
        label.addMouseListener(listImage);
		panel = new JPanel(new GridBagLayout());
		panel.add(label);
	}
	
	private void initHistogramaAbsoluto(){  
    	histo = new Vector<Integer>(0);
    	Color aux;

    	for(int i = 0; i < SIZE; i++)
    		histo.addElement(0);
    	
    	for( int i = 0; i < imagenReal.getWidth(); i++ )
            for( int j = 0; j < imagenReal.getHeight(); j++ ){
                aux = new Color(this.imagenReal.getRGB(i, j));
                histo.set(aux.getRed(),histo.get(aux.getRed()) + 1);
            }
    	
//    	for(int i = 0; i < histo.size(); i++)
//    		System.out.println("color = " + i + "; cantidad de pixels: " + histo.get(i));

    		
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
		calculateBrillo();
		calculateContraste();
		initStatusBar();
		internalFrame.add(panel);
		internalFrame.add(statusBar,BorderLayout.SOUTH);
		internalFrame.pack();
		internalFrame.setVisible(true);
		this.api.desktopPane.add(internalFrame);
		this.api.imagenes.addElement(this);
	}
	
	/*
	 * Private method to make a copy of an image.
	 */
	private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
	
	// -----------------------------------------------------GETS----------------------------------------
	/*
	 * Method that return in a vector, the histogram of an image.
	 */
	public Vector<Integer> getHistoAbsoluto(){return this.histo;}
	
	/*
	 * Method that return in a vector, the accumulated histogram of an image.
	 */
	public Vector<Integer> getHistoAcumulativo(){return this.acumulado;}
	
	//---------------------------------------------POINT OPERATIONS-----------------------------------
	
	/*
	 * Method to open an image
	 */
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
	
	/*
	 * Method to save an image
	 */
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
	
	/*
	 * Private method to draw a graphic
	 */
	private void createGraphic(String name, Vector<Integer> vectorHist){
		Histograma histo = new Histograma(name,vectorHist);
		ChartPanel panel = new ChartPanel(histo.grafica);
		JInternalFrame ventana = new JInternalFrame(name,true,true,true,true);
		ventana.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		ventana.add(panel);
		ventana.pack();
		ventana.setVisible(true);
		this.api.desktopPane.add(ventana);
	}
	
	/*
	 * Public method to create the histogram graphic of an image
	 */
	public void graficaHistogramaAbsoluto(int pos){
		createGraphic(HISTO_ABSO + ": imagen " + (pos + 1),this.histo);	
	}
	
	/*
	 * Public method to create the accumulated histogram graphic of an image
	 */
	public void graficaHistogramaAcumulado(int pos){
		createGraphic(HISTO_ACUM + ": imagen " + (pos + 1),this.acumulado);	
	}
	
	private void calculateBrillo(){
		float sumatorio = 0;
		int tamano = this.imagenReal.getHeight() * this.imagenReal.getWidth();
		for(int i = 0; i < SIZE; i++){
			sumatorio = this.histo.get(i) * i + sumatorio;
		}
		this.brillo = (int) (sumatorio / tamano);
	}
	
	public String getBrillo(){
		return String.valueOf(this.brillo);
	}
	
	
	public void LinealTransform(Vector<Point> points){
		int nTramos = points.size()/2;
		BufferedImage outImage = deepCopy(this.imagenReal);
		Vector<CalcRecta> vTramos = new Vector<CalcRecta>(0);
		Vector<Integer> tabla = new Vector<Integer>(0);
		
		for(int i = 0; i < nTramos; i++){
			vTramos.addElement(new CalcRecta(points.get(i*2), points.get(i*2+1)));
			
			if(points.get(i*2).x == 0){
				for(int j = points.get(i*2).x; j < points.get(i*2+1).x; j++)
					tabla.addElement(vTramos.get(i).calcVout(j));
			}else{
				for(int j = points.get(i*2).x; j <= points.get(i*2+1).x; j++)
					tabla.addElement(vTramos.get(i).calcVout(j));
					//System.out.println("j = " + j);
			}
//			for(int j = points.get(i*2).x; j <= points.get(i*2+1).x; j++)
//				tabla.addElement(vTramos.get(i).calcVout(j));
//				//System.out.println("j = " + j);
		}
//		Comprobación de los datos calculados
		System.out.println("ha hallado la ecuación de las rectas de N Tramos: " + vTramos.size());
		System.out.println("Vin + Vout");
		for(int i = 0; i < tabla.size();i++){
			System.out.println(i+" , "+tabla.get(i));
		}
//		Fin de la comprobación
		
		
		
		for( int i = 0; i < outImage.getWidth(); i++ ){
			int valor;
			Color colorAux;
            for( int j = 0; j < outImage.getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorAux=new Color(outImage.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                valor = tabla.get(colorAux.getRed());
                colorAux = new Color(valor,valor,valor);
                //Asignamos el nuevo valor al BufferedImage
                outImage.setRGB(i, j,colorAux.getRGB());
            }
        }
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();
	}
	
	public void ecualizarHisto(){
		
		BufferedImage outImage = deepCopy(this.imagenReal);
		Vector<Integer> tabla = new Vector<Integer>(0);
		float nume;
		int deno, redondeado;
		
		System.out.println("Tabla de ecualización del histograma");
		System.out.println("Vin | Vout");
				
		for(int i = 0; i < SIZE; i++){
			nume = (float)(SIZE * acumulado.get(i));
			deno = outImage.getHeight() * outImage.getWidth();
			redondeado = (int) (nume / deno);
			tabla.add(Math.max(0, redondeado - 1));
			System.out.println(i + "  " + tabla.get(i));
		}
		
		
		for( int i = 0; i < outImage.getWidth(); i++ ){
			int valor;
			Color colorAux;
            for( int j = 0; j < outImage.getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorAux=new Color(outImage.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                valor = tabla.get(colorAux.getRed());
                colorAux = new Color(valor,valor,valor);
                //Asignamos el nuevo valor al BufferedImage
                outImage.setRGB(i, j,colorAux.getRGB());
            }
        }
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();
	}
	
	
	public void correcGamma(double g){
		
		BufferedImage outImage = deepCopy(this.imagenReal);
		Vector<Integer> tabla = new Vector<Integer>(0);
		double vIn,vOut;
		
		System.out.println("Tabla de corrección gamma");
		System.out.println("Vin | Vout");
		
		for(int i = 0; i < SIZE; i++){
			vIn = ((double) i) / (SIZE - 1);
			vOut = Math.pow(vIn,g);
			tabla.add((int)(vOut * (SIZE - 1)));
			System.out.println(i + "  " + tabla.get(i));
		}
		
		
		for( int i = 0; i < outImage.getWidth(); i++ ){
			int valor;
			Color colorAux;
            for( int j = 0; j < outImage.getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorAux=new Color(outImage.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                valor = tabla.get(colorAux.getRed());
                colorAux = new Color(valor,valor,valor);
                //Asignamos el nuevo valor al BufferedImage
                outImage.setRGB(i, j,colorAux.getRGB());
            }
        }
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();
	}
	
	public void espeHisto(){
		
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
         
//      Cálculo del histograma absoluto de la imagen auxiliar.
        Vector<Integer> histo_aux = new Vector<Integer>(0);
    	Color aux;
    	int auxImageSize = auxImage.getWidth() * auxImage.getHeight();
    	int imagenRealSize = imagenReal.getWidth() * imagenReal.getHeight();

    	for(int i = 0; i < SIZE; i++)
    		histo_aux.addElement(0);
    	
    	for( int i = 0; i < auxImage.getWidth(); i++ )
            for( int j = 0; j < auxImage.getHeight(); j++ ){
                aux = new Color(auxImage.getRGB(i, j));
                histo_aux.set(aux.getRed(),histo_aux.get(aux.getRed()) + 1);
            }
    	
//    	Cálculo del histograma acumulado de la imagen auxiliar
        
    	Vector<Integer> acumulado_aux = new Vector<Integer>(0);
		acumulado_aux.addElement(histo_aux.get(0));
    	for(int i = 1; i< histo_aux.size(); i++)
    		acumulado_aux.addElement(acumulado_aux.get(i - 1) + histo_aux.get(i));
    	
//		Normalización del histograma acumulado de la imagen auxiliar.
    	
    	Vector<Float> auxImageAcumNorm = new Vector<Float>(0);
    	for(int i = 0; i < SIZE; i++)
    		auxImageAcumNorm.addElement((float)0);
    	for(int i = 0; i < acumulado_aux.size(); i++){
    		auxImageAcumNorm.set(i, ((float)acumulado_aux.get(i))/auxImageSize);
    	}
    	
// 		Normalización del histograma acumulado de la imagen a cambiar.
    	
    	Vector<Float> imagenRealAcumNorm = new Vector<Float>(0);
    	for(int i = 0; i < SIZE; i++)
    		imagenRealAcumNorm.addElement((float)0);
    	for(int i = 0; i < acumulado_aux.size(); i++){
    		imagenRealAcumNorm.set(i, ((float)this.acumulado.get(i))/imagenRealSize);
    	}
    	
//    	Cálculo de la tabla de transformación
    	Vector<Integer> tabla = new Vector<Integer>(0);
    	float lastDife, auxDif;
    	int indice = 0;
    	
    	for(int i = 0; i < SIZE; i++)
    		tabla.addElement(0);
    	
    	
//    	System.out.println("Tamaño de vector acumulado Real Normalizado: " + imagenRealAcumNorm.size());
//    	System.out.println("Tamaño de vector acumulado Imagen Auxiliar: " + auxImageAcumNorm.size());
//    	System.out.println("Tamaño de la tabla: " + tabla.size());

    	for(int i = 0; i < SIZE; i++){
    		lastDife = 9999;
    		for(int j = 0; j < SIZE; j++){
    			auxDif = Math.abs(auxImageAcumNorm.get(j) - imagenRealAcumNorm.get(i));
    			if(auxDif <= lastDife){
    				lastDife = auxDif;
    				indice = j;
    			}
    		}
    	//System.out.println("i: " + i);
    	tabla.set(i, indice);	
    	}
    	
//    	Creación de la nueva imagen con la tabla dada.
    	
    	//Copia de la imagen anterior
    	
    	BufferedImage outImage = deepCopy(this.imagenReal);
    	
    	//set con la tabla
    	
    	for( int i = 0; i < outImage.getWidth(); i++ ){
			int valor;
			Color colorAux;
            for( int j = 0; j < outImage.getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorAux=new Color(outImage.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                valor = tabla.get(colorAux.getRed());
                colorAux = new Color(valor,valor,valor);
                //Asignamos el nuevo valor al BufferedImage
                outImage.setRGB(i, j,colorAux.getRGB());
            }
        }
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();

	}
	
	public double entropia(){
		double prob = 0, sumatorio = 0, log2P = 0;
		for(int i = 0; i < SIZE; i++){
			prob = ((double)this.histo.get(i)) / (imagenReal.getHeight() * imagenReal.getWidth());
			if(prob != 0)
				log2P = (double)(Math.log(prob) / Math.log(2));
			else
				log2P = 0;
			sumatorio = sumatorio + (prob * log2P);
			System.out.println("p[" + i +"] = " + prob);
			System.out.println("log2P[" + i +"] = " + log2P);
			System.out.println("sum[" + i +"] = " + sumatorio);

		}
		return sumatorio * -1;
	}
	
	private void calculateContraste(){
		double sumatorio = 0;
		
		for(int i = 0; i < SIZE; i++){
			sumatorio = sumatorio + (this.histo.get(i) * Math.pow((i - this.brillo), 2));
		}
		this.contraste = (int) Math.sqrt(sumatorio/(imagenReal.getHeight() * imagenReal.getWidth()));
	}
	
	public int getContraste(){
		return this.contraste;
	}
	
	public void setBrilloContraste(int newBrillo, int newContraste){
		float a = (((float) newContraste) / this.contraste);
		float b = ((float) newBrillo) - a * this.brillo;
		Vector<Integer> tabla = new Vector<Integer>(0);
		int aux = 0;
		
		System.out.println("newBrillo: " + newBrillo);
		System.out.println("newContraste: " + newContraste);
		System.out.println("a: " + a);
		System.out.println("b: " + b);
		
		for(int i = 0; i < SIZE; i++)
    		tabla.addElement(0);
		System.out.println("Vin | Vout ");
		for(int i = 0; i < SIZE; i++){
			aux = (int)(a * i + b);
			if(aux < 0)
				aux = 0;
			if(aux >= SIZE)
				aux = SIZE - 1;
			tabla.set(i, aux);
			System.out.println(i + " | " + tabla.get(i) );
		}
		
//    	Creación de la nueva imagen con la tabla dada.
    	
    	//Copia de la imagen anterior
    	
    	BufferedImage outImage = deepCopy(this.imagenReal);
    	
    	//set con la tabla
    	
    	for( int i = 0; i < outImage.getWidth(); i++ ){
			int valor;
			Color colorAux;
            for( int j = 0; j < outImage.getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorAux=new Color(outImage.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                valor = tabla.get(colorAux.getRed());
                colorAux = new Color(valor,valor,valor);
                //Asignamos el nuevo valor al BufferedImage
                outImage.setRGB(i, j,colorAux.getRGB());
            }
        }
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();
	}
	
	
	
	
	
	
	public void diferenciaImagenes(){
		
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
           
    	//Copia de la imagen actual
    	
    	BufferedImage outImage = deepCopy(this.imagenReal);
    	
    	//set con la tabla
    	
    	for( int i = 0; i < outImage.getWidth(); i++ ){
			int valor;
			Color colorAux;
			Color colorI1, colorI2;
            for( int j = 0; j < outImage.getHeight(); j++ ){
                //Almacenamos el color del píxel
                colorI1 = new Color(auxImage.getRGB(i, j));
                colorI2 = new Color(imagenReal.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                valor = Math.abs(colorI1.getRed() - colorI2.getRed());
                colorAux = new Color(valor,valor,valor);
                //Asignamos el nuevo valor al BufferedImage
                outImage.setRGB(i, j,colorAux.getRGB());
            }
        }
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();

	}
	
	public void regionInteres(Point origenAux, Point endAux){
		int alto = Math.abs(origenAux.y - endAux.y);
		int ancho = Math.abs(origenAux.x - endAux.x);
		
		BufferedImage outImage = new BufferedImage(ancho,alto,imagenReal.getType());
		
		System.out.println("alto: " + outImage.getHeight());
		System.out.println("ancho: " + outImage.getWidth());
		
		Color colorAux;
		for(int i = 0; i < outImage.getWidth(); i++){
			for(int j = 0; j < outImage.getHeight(); j++){
				colorAux = new Color(imagenReal.getRGB(origenAux.x + i,origenAux.y + j));
				outImage.setRGB(i, j,colorAux.getRGB());
			}
		}
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();

	}
	
	public void noCambio(int umbralT){
		// Abro la nueva imagen
		BufferedImage imagenOriginal = null;
        JFileChooser selector=new JFileChooser();
        selector.setDialogTitle("Seleccione una imagen");
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG & GIF & BMP & PNG", "jpg", "gif", "bmp", "png");
        selector.setFileFilter(filtroImagen);
        int flag = selector.showOpenDialog(null);
        if(flag == JFileChooser.APPROVE_OPTION){
            try {
                File imagenSeleccionada=selector.getSelectedFile();
                imagenOriginal = ImageIO.read(imagenSeleccionada);
                this.nombre = imagenSeleccionada.getName();
            } catch (Exception e) {
            	JOptionPane.showMessageDialog(new JFrame(), "Se produjo un error al cargar la imagen");
            }    
        }
       
        Color colorDiferencia;
        Color colorOriginal;
        Color colorNoCambio;
        
        // Imagen real es en este caso la imagen diferencia
        // y imagenOriginal es la imagen original que habrimos y en la que vamos a pintar de rojo.
//        
//       for(int i = 0; i < imagenReal.getWidth(); i++){
//    	   for(int j = 0; j < imagenReal.getHeight(); j++){
//    		   colorDiferencia = new Color(imagenReal.getRGB(i, j));
//    		   if(colorDiferencia.getRed() >= umbralT){
////    			   colorOriginal = new Color(imagenOriginal.getRGB(i, j));
//    			   colorNoCambio = new Color(255,0,0);
//    			   imagenOriginal.setRGB(i, j,colorNoCambio.getRGB());
//    		   }
//    	   }
//       }
       
		BufferedImage outImage = new BufferedImage(imagenReal.getWidth(),imagenReal.getHeight(),BufferedImage.TYPE_INT_RGB);

		for(int i = 0; i < imagenReal.getWidth(); i++){
	    	   for(int j = 0; j < imagenReal.getHeight(); j++){
	    		   colorDiferencia = new Color(imagenReal.getRGB(i, j));
	    		   if(colorDiferencia.getRed() >= umbralT){
	    			   colorNoCambio = new Color(255,0,0);
	    			   outImage.setRGB(i, j,colorNoCambio.getRGB());
	    		   }
	    		   else{
	    			   colorOriginal = new Color(imagenOriginal.getRGB(i, j));
	    			   outImage.setRGB(i, j, colorOriginal.getRGB());
	    		   }
	    	   }
	       }
       
       
       
       
//		System.out.println("Typo: " + imagenOriginal.getType());
        
       Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();
       
	}
	
	public void espejoVertical(){
		BufferedImage outImage = new BufferedImage(imagenReal.getWidth(),imagenReal.getHeight(),BufferedImage.TYPE_INT_RGB);
		Color colorAux;
		int height = imagenReal.getHeight() - 1;
		
		for(int i = 0; i < imagenReal.getWidth(); i++){
	    	   for(int j = 0; j < imagenReal.getHeight(); j++){
	    		   colorAux = new Color(imagenReal.getRGB(i, j));
	    		   outImage.setRGB(i,height - j,colorAux.getRGB());
	    	   }
	       }
		
		
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();		
	}
	
	
	public void espejoHorizontal(){
		BufferedImage outImage = new BufferedImage(imagenReal.getWidth(),imagenReal.getHeight(),BufferedImage.TYPE_INT_RGB);
		Color colorAux;
		int width = imagenReal.getWidth() - 1;
		
		for(int i = 0; i < imagenReal.getWidth(); i++){
	    	   for(int j = 0; j < imagenReal.getHeight(); j++){
	    		   colorAux = new Color(imagenReal.getRGB(i, j));
	    		   outImage.setRGB(width - i,j,colorAux.getRGB());
	    	   }
	       }
		
		
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();
	}
	
	public void traspuesta(){
		BufferedImage outImage = new BufferedImage(imagenReal.getHeight(),imagenReal.getWidth(),BufferedImage.TYPE_INT_RGB);
		Color colorAux;
		
		for(int i = 0; i < imagenReal.getWidth(); i++){
	    	   for(int j = 0; j < imagenReal.getHeight(); j++){
	    		   colorAux = new Color(imagenReal.getRGB(i, j));
	    		   outImage.setRGB(j,i,colorAux.getRGB());
	    	   }
	       }
		
		
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();
	}
	
	private Color vecino(int newAncho, int newAlto, int i, int j){
		float a = ((float) newAncho) / (imagenReal.getWidth() -1);
		float b = ((float) newAlto) / (imagenReal.getHeight() - 1);
		
		int x = Math.round(((float)i) / a);
		int y = Math.round(((float)j) / b);
		
//		System.out.println("a = " + a);
//		System.out.println("b = " + b);
//		System.out.println("i = " + i);
//		System.out.println("j = " + j);
//		System.out.println("x = " + x);
//		System.out.println("y = " + y);
		
		return new Color(imagenReal.getRGB(x, y));
	}
	
	private Color bilineal(int newAncho, int newAlto, int i, int j){
		float a = ((float) newAncho) / (imagenReal.getWidth() -1);
		float b = ((float) newAlto) / (imagenReal.getHeight() - 1);
		
		float x = ((float)i) / a;
		float y = ((float)j) / b;
		
		int minX = (int) Math.floor(x);
		int minY = (int) Math.floor(y);
		int maxX = minX + 1;
		int maxY = minY + 1;
		
		double p = Math.abs(x - minX);
		double q = Math.abs(y - minY);
		
//		System.out.println("i = " + i);
//		System.out.println("j = " + j);
//		System.out.println("a = " + a);
//		System.out.println("b = " + b);
//		System.out.println("x = " + x);
//		System.out.println("y = " + y);
//		System.out.println("minX = " + minX);
//		System.out.println("minY = " + minY);
//		System.out.println("maxX = " + maxX);
//		System.out.println("maxY = " + maxY);
//		System.out.println("p = " + p);
//		System.out.println("q = " + q);
		
		Color pA = new Color(imagenReal.getRGB(minX, maxY));
		Color pB = new Color(imagenReal.getRGB(maxX, maxY));
		Color pC = new Color(imagenReal.getRGB(minX, minY));
		Color pD = new Color(imagenReal.getRGB(maxX, minY));
		
		int valor = (int) (((float)pC.getRed()) + ((float)(pD.getRed() - pC.getRed())) * p + ((float)(pA.getRed() - pC.getRed())) * q + ((float)(pB.getRed() + pC.getRed() - pA.getRed() - pD.getRed())) * p * q);
		
		return new Color(valor,valor,valor);
	}
	
	public void escalar(Boolean vecino, int newAncho, int newAlto){
		BufferedImage outImage = new BufferedImage(newAncho,newAlto,BufferedImage.TYPE_INT_RGB);
		Color colorAux;

		
		if(vecino == true){
			System.out.println("Tipo: Vecino");
			for(int i = 0; i < outImage.getWidth(); i++){
		    	   for(int j = 0; j < outImage.getHeight(); j++){
		    		   colorAux = vecino(newAncho,newAlto,i,j);
		    		   outImage.setRGB(i,j,colorAux.getRGB());
		    	   }
		       }
		}else{
			System.out.println("Tipo: Bilineal");
			for(int i = 0; i < outImage.getWidth(); i++){
		    	   for(int j = 0; j < outImage.getHeight(); j++){
		    		   colorAux = bilineal(newAncho,newAlto,i,j);
		    		   outImage.setRGB(i,j,colorAux.getRGB());
		    	   }
		       }
		}
		
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();
	}
	
	
	private Point2D.Double transDirecta(int x, int y, int theta){
		double nX, nY;
		nX = Math.cos(Math.toRadians(theta))*x - Math.sin(Math.toRadians(theta))*y;
		nY = Math.sin(Math.toRadians(theta))*x + Math.cos(Math.toRadians(theta))*y;
		
		return new Point2D.Double(nX,nY);
	}
	
	private Point2D.Double transInversa(int x, int y, int theta){
		double nX, nY;
		nX = Math.cos(Math.toRadians(theta)) * x + Math.sin(Math.toRadians(theta))*y;
		nY = (Math.sin(Math.toRadians(theta)) * x * -1) + Math.cos(Math.toRadians(theta))*y;
		
		return new Point2D.Double(nX,nY);
	}
	
	private Vector<Integer> paralelogramo(int theta){
		Vector<Integer> valores = new Vector<Integer>(0);
		Vector<Point2D.Double> puntos = new Vector<Point2D.Double>(0);
		
		// Paso 1, rotar los puntos de las esquinas con la TD
		puntos.addElement(transDirecta(0,0,theta)); // Punto de origen E
		puntos.addElement(transDirecta(imagenReal.getWidth(),0,theta)); // Punto F
		puntos.addElement(transDirecta(imagenReal.getWidth(),imagenReal.getHeight(),theta)); // Punto G
		puntos.addElement(transDirecta(0,imagenReal.getHeight(),theta)); // Punto H
		
//		for(int i = 0; i < puntos.size(); i++){
//			System.out.println("Punto["+i+"]= " + puntos.get(i));
//		}
		
		// Paso 2, Determinar los valores para el paralelogramo
		double xMin = 999999, xMax = 0, yMin = 999999, yMax = 0;
		
		for(int i = 0; i < puntos.size(); i++){
			if(puntos.get(i).getX() <= xMin)
				xMin = puntos.get(i).getX();
			if(puntos.get(i).getX() > xMax)
				xMax = puntos.get(i).getX();
			if(puntos.get(i).getY() <= yMin)
				yMin = puntos.get(i).getY();
			if(puntos.get(i).getY() > yMax)
				yMax = puntos.get(i).getY();
		}
		
		valores.addElement((int) Math.round(xMin));
		valores.addElement((int) Math.round(yMin));
		valores.addElement((int) Math.round(xMax));
		valores.addElement((int) Math.round(yMax));
		
		return valores;
	}
	
	public void rotacion(Boolean vecino, int theta){
		Vector<Integer> valores = paralelogramo(theta);
		int xMin = valores.get(0);
		int yMin = valores.get(1);
		int ancho = Math.abs(valores.get(0) - valores.get(2));
		int alto = Math.abs(valores.get(1) - valores.get(3));
		BufferedImage outImage = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
		System.out.println("ancho = " + ancho);
		System.out.println("alto = " + alto);
		int x_, y_;
		Point2D.Double pAux;
		Color colorAux;
		
		if(vecino == true){
			for(int i = 0; i < outImage.getWidth(); i++){
				for(int j = 0; j < outImage.getHeight(); j++){
					x_ = i + xMin;
					y_ = j + yMin;
					pAux = transInversa(x_,y_,theta);
					if(pAux.getX() < 0 || pAux.getY() < 0 || pAux.getX() >= imagenReal.getWidth() || pAux.getY() >= imagenReal.getHeight()){
						colorAux = new Color(255,255,255);
					}else{
						colorAux = vecino2(pAux.getX(),pAux.getY());
					}
		    		outImage.setRGB(i,j,colorAux.getRGB());
				}
			}
		}else{
			for(int i = 0; i < outImage.getWidth(); i++){
				for(int j = 0; j < outImage.getHeight(); j++){
					x_ = i + xMin;
					y_ = j + yMin;
					pAux = transInversa(x_,y_,theta);
					if(pAux.getX() < 0 || pAux.getY() < 0 || pAux.getX() >= imagenReal.getWidth() || pAux.getY() >= imagenReal.getHeight()){
						colorAux = new Color(255,255,255);
					}else{
						colorAux = vecino2(pAux.getX(),pAux.getY());
					}
		    		outImage.setRGB(i,j,colorAux.getRGB());
				}
			}
		}
		
		Imagenes newImagen = new Imagenes(this.api,outImage);
		newImagen.empaquetarImagen();
	}
	
	
	private Color vecino2(double x_, double y_){
		int x = (int)Math.round(x_);
		int y = (int) Math.round(y_);
		return new Color(imagenReal.getRGB(x, y));
	}
	
}
