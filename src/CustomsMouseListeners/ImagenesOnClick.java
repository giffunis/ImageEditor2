package CustomsMouseListeners;

import Imagenes.Imagenes;

import java.awt.event.*;

import javax.swing.*;

public class ImagenesOnClick implements MouseListener {
	
	Imagenes imagen;
	
	public ImagenesOnClick(Imagenes imagen){
		this.imagen = imagen;
	}
	
	private Integer getX(MouseEvent e){
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
	
	
	private Integer getY(MouseEvent e){
        int bufferImageHeight = this.imagen.imagenReal.getHeight();
        int internalFrameHeight =this.imagen.panel.getHeight();
        int posCursor = e.getY();

        int distancia = (int) Math.abs(internalFrameHeight - bufferImageHeight)/2;
        int salida = posCursor + distancia;

        if(bufferImageHeight < internalFrameHeight)
                salida = posCursor - distancia;
        if(salida < 0 || salida > bufferImageHeight)
                return null;
        return salida;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(new JFrame(), "Point(" + getX(e)+ "," + getY(e) +")");
		
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

}
