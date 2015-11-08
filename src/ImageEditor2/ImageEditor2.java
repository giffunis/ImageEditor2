package ImageEditor2;

// Generic class
import java.awt.*;
import java.util.*;
import javax.swing.*;

import BarraInferior.JStatusBar;
//	My class.
import Imagenes.Imagenes;

public class ImageEditor2 {

	
	JFrame framePrincipal;
	public JDesktopPane desktopPane;
	public Vector<Imagenes> imagenes;
	//JMenuBar2 menuBar;
	JToolBar2 toolBar2;
	
	
	ImageEditor2(){
		imagenes = new Vector<Imagenes>(0);
		initFramePrincipal();
		initDesktopPane();
		initToolBar2();
//		initMenuBar2();
//		framePrincipal.setJMenuBar(menuBar);

	}
	
	void initFramePrincipal(){
		framePrincipal = new JFrame("ImageEditor2");
		framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framePrincipal.setLayout(new BorderLayout());
		framePrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
		framePrincipal.setVisible(true);
	}
	
	void initDesktopPane(){
		desktopPane = new JDesktopPane();
		this.framePrincipal.add(desktopPane,BorderLayout.CENTER);
	}
	
	void initToolBar2(){
		toolBar2 = new JToolBar2(this);
		framePrincipal.add("North", toolBar2);
	}
	
	void initMenuBar2(){
		
	}
	
	
	public static void main(String[] args) {
    	@SuppressWarnings("unused")
		ImageEditor2 programa = new ImageEditor2();
    }
}
