package CustomsMouseListeners;

import Imagenes.Imagenes;

import java.awt.event.*;

import javax.swing.*;

public class ImagenesOnClick implements MouseListener {
	
	Imagenes imagen;
	
	public ImagenesOnClick(Imagenes imagen){
		this.imagen = imagen;
	}
	
	@SuppressWarnings("unused")
	private int getX(){
		
		return 0;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int aux = this.imagen.imagenReal.getWidth();
		JOptionPane.showMessageDialog(new JFrame(), "has echo click sobre la imagen cuya anchura es: " + aux);
		
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
