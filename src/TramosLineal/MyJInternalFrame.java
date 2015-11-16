package TramosLineal;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import ImageEditor2.ImageEditor2;


@SuppressWarnings("serial")
public class MyJInternalFrame extends JInternalFrame{

	JPanel panelBotton;
	MyLeftPanel panelIzq;
	MyDrawPanel panelDer;
	JButton btn;
	int nTramos;
	ImageEditor2 api;
	
	public MyJInternalFrame(ImageEditor2 api, int nTramos) {
		super("Transformación Lineal",true,true,true,true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		//setLayout( new GridLayout(2,2));
		this.nTramos = nTramos;
		System.out.println("Número de tramos de MyJInternalFrame: " + nTramos);
		initComponentes();
		this.api = api;
		this.api.desktopPane.add(this);
	}

	private void initComponentes() {
		initPanelIzq();
		initPanelDer();
		initPanelBotton();
		pack();
	}

	private void initPanelBotton() {
		panelBotton = new JPanel();
		
		btn = new JButton("Draw");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					panelDer.draw(panelIzq.getPoints());
				} catch(Exception a){
					a.printStackTrace();
				}
			}
		});
		panelBotton.add(btn);
		
		btn = new JButton("Accept");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					transform(panelIzq.getPoints());
					setClosed( true ); // Falta llamar a la funcion 
				} catch(Exception a){
					a.printStackTrace();
				}
			}

		});
		panelBotton.add(btn);
		
		btn = new JButton("Cancel");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					setClosed( true );
				} catch(Exception a){
					a.printStackTrace();
				}
			}
		});
		panelBotton.add(btn);
		panelBotton.setVisible(true);
		add(panelBotton,BorderLayout.SOUTH);
			
	}

	private void initPanelDer() {
		panelDer = new MyDrawPanel();
		add(panelDer,BorderLayout.EAST);
	}

	private void initPanelIzq() {
		panelIzq = new MyLeftPanel(nTramos);
		add(panelIzq,BorderLayout.WEST);
	}
	
	private int getImageFromInternalFrame(){
		JInternalFrame internalFrame = api.desktopPane.getSelectedFrame();
		String aux = internalFrame.getTitle();
		for(int i = 0; i < api.imagenes.size(); i++){
			if(aux == api.imagenes.get(i).internalFrame.getTitle()){
				return i;
			}
		}
		return -1;
	}
	
	private void transform(Vector<Point> points) {
		api.imagenes.get(getImageFromInternalFrame()).LinealTransform(points);	
	}

}
