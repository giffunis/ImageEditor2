package CustomsMouseListeners;

import Imagenes.Imagenes;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;

public class ImagenesOnClick implements MouseListener {
	
	Imagenes imagen;
	public Point origen;
	public Point end;
	
	public ImagenesOnClick(Imagenes imagen){
		this.imagen = imagen;
		this.origen = null;
		this.end = null;
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
//		imagen.statusBar.setPositionMouse(get_X(e), get_Y(e));
		imagen.statusBar.setPositionMouse(e.getX(),e.getY());
		try{
			Color pixel = new Color(imagen.imagenReal.getRGB(e.getX(), e.getY()));
			imagen.statusBar.setGrayMouse(pixel);
		}catch(Exception a){
			System.out.println("Fuera de rango");
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.origen = e.getPoint(); 
		this.imagen.origin = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.end = e.getPoint();
		this.imagen.end = e.getPoint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent e) {
//		imagen.statusBar.setPositionMouse(e.getX(), e.getY());
   }

}
