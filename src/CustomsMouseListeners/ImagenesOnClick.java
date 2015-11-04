package CustomsMouseListeners;

import Imagenes.Imagenes;


import java.awt.event.*;

import javax.swing.*;

public class ImagenesOnClick implements MouseListener {
	
	Imagenes imagen;
	
	public ImagenesOnClick(Imagenes imagen){
		this.imagen = imagen;
	}
	
//	private Integer getX(MouseEvent e){
//        int bufferImageWidth = this.imagen.imagenReal.getWidth();
//        int internalFrameWidth =this.imagen.label.getWidth();
//        int posCursor = e.getX();
//
//        int distancia = (int) Math.abs(internalFrameWidth - bufferImageWidth)/2;
//        int salida = posCursor + distancia;
//
//        if(bufferImageWidth < internalFrameWidth)
//                salida = posCursor - distancia;
//        if(salida < 0 || salida > bufferImageWidth)
//                return null;
//        return salida;
//	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		JOptionPane.showMessageDialog(new JFrame(), e.getPoint());
		
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
