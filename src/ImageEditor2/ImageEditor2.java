package ImageEditor2;

// Generic class
import java.awt.*;
import java.util.*;
import javax.swing.*;

//	My class.
import Imagenes.Imagenes;

public class ImageEditor2 {
	JFrame framePrincipal;
	JDesktopPane desktopPane;
	public Vector<Imagenes> imagenes;
	//JMenuBar2 menuBar;
	//JToolBar2 toolBar2;
	
	ImageEditor2(){
		imagenes = new Vector<Imagenes>(0);
		initFramePrincipal();
		initDesktopPane();
//		initToolBar2();
//		initMenuBar2();
		
//		framePrincipal.add("North", barraBotonesPrincipal);	//	Añadimos barraPrincipal al framePrincipal
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
		
	}
	
	void initMenuBar2(){
		
	}
	
	public static void main(String[] args) {
    	@SuppressWarnings("unused")
		ImageEditor2 programa = new ImageEditor2();
    }
}
