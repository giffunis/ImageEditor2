package CustomsMouseListeners;

import Imagenes.Imagenes;

import java.awt.Color;
import java.awt.event.*;

public class ImagenesOnClick implements MouseListener {
	
	Imagenes imagen;
	
	public ImagenesOnClick(Imagenes imagen){
		this.imagen = imagen;
	}
	
	private Integer get_X(MouseEvent e){
        int bufferImageWidth = this.imagen.imagenReal.getWidth();
        int internalFrameWidth =this.imagen.panel.getWidth();
        int posCursor = e.getX();

        int distancia = (int) Math.abs(internalFrameWidth - bufferImageWidth)/2;
        int salida = posCursor + distancia;

        if(bufferImageWidth < internalFrameWidth)
                salida = posCursor - distancia;
        if(salida < 0 || salida > bufferImageWidth)
                return null;
        return salida;
	}

	private Integer get_Y(MouseEvent e){
        int bufferImageWidth = this.imagen.imagenReal.getHeight();
        int internalFrameWidth =this.imagen.panel.getHeight();
        int posCursor = e.getY();

        int distancia = (int) Math.abs(internalFrameWidth - bufferImageWidth + 6)/2;
        int salida = posCursor + distancia;

        if(bufferImageWidth < internalFrameWidth)
                salida = posCursor - distancia;
        if(salida < 0 || salida > bufferImageWidth )
                return null;
        return salida;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		imagen.statusBar.setPositionMouse(get_X(e), get_Y(e));
		try{
			Color pixel = new Color(imagen.imagenReal.getRGB(get_X(e), get_Y(e)));
			imagen.statusBar.setGrayMouse(pixel);
		}catch(Exception a){
			System.out.println("Fuera de rango");
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
