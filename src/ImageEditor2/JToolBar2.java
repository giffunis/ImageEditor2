package ImageEditor2;


import java.awt.GridLayout;
import java.net.URL;

import javax.swing.*;

import Imagenes.Imagenes;
import TramosLineal.MyFrame2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class JToolBar2 extends JToolBar{
		
		ImageEditor2 api;
		JButton btnItem;
		
		public JToolBar2(ImageEditor2 api){
			this.api = api;
			initAbrir();
			initGuardar();
			initEscalaGrises();
			initHistogramaAbsoluto();
			initHistogramaAcumulado();
			initAjusteLineal();
			ecuaHisto();
		}
		
		// function to create a Button
		void createBtn(String name, String imageRoute){
			btnItem = new JButton();
			btnItem.setName(name);
			btnItem.setToolTipText(name);
			btnItem.setSelected(false);
			btnItem.setIcon(new ImageIcon(imageRoute));	
		}
		
		void createBtn(String name, URL imageRoute){
			btnItem = new JButton();
			btnItem.setName(name);
			btnItem.setToolTipText(name);
			btnItem.setSelected(false);
			btnItem.setIcon(new ImageIcon(imageRoute));	
		}
		
		int getImageFromInternalFrame(){
			JInternalFrame internalFrame = api.desktopPane.getSelectedFrame();
			String aux = internalFrame.getTitle();
			for(int i = 0; i < api.imagenes.size(); i++){
				if(aux == api.imagenes.get(i).internalFrame.getTitle()){
					return i;
				}
			}
			return -1;
		}
//---------------------------------------OPEN-----------------------------------------
		void initAbrir(){
			createBtn("Abrir",Thread.currentThread().getContextClassLoader().getResource("Images/open.png"));
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnAbrirActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {
			Imagenes imagen = new Imagenes(this.api);
			imagen.abrirImagen();
		}
//----------------------------------------SAVE------------------------------------------
		void initGuardar(){
			createBtn("Guardar",Thread.currentThread().getContextClassLoader().getResource("Images/save.png"));
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnGuardarActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
			api.imagenes.get(getImageFromInternalFrame()).guardarImagen();
		}
		
//----------------------------------------ESCALA DE GRISES------------------------------------------
		void initEscalaGrises(){
			createBtn("Escala de grises",Thread.currentThread().getContextClassLoader().getResource("Images/EscalaGrises.png"));
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnEscalaGrisesActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		private void btnEscalaGrisesActionPerformed(java.awt.event.ActionEvent evt) {
			api.imagenes.get(getImageFromInternalFrame()).escalaGrises();
		}

//----------------------------------------HISTOGRAMA ABSOLUTO------------------------------------------
		void initHistogramaAbsoluto(){
			createBtn("Histograma absoluto",Thread.currentThread().getContextClassLoader().getResource("Images/histogram2.png"));
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnHistogramaAbsolutoActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		private void btnHistogramaAbsolutoActionPerformed(java.awt.event.ActionEvent evt) {
			int pos = getImageFromInternalFrame();
			api.imagenes.get(pos).graficaHistogramaAbsoluto(pos);
		}

//----------------------------------------HISTOGRAMA ACUMULADO------------------------------------------
		void initHistogramaAcumulado(){
			createBtn("Histograma acumulado",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnHistogramaAcumuladoActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		private void btnHistogramaAcumuladoActionPerformed(java.awt.event.ActionEvent evt) {
			int pos = getImageFromInternalFrame();
			api.imagenes.get(pos).graficaHistogramaAcumulado(pos);
		}
		
//----------------------------------------AJUSTE LINEAL POR TRAMOS------------------------------------------
		void initAjusteLineal(){
			createBtn("Ajuste lineal por tramos",Thread.currentThread().getContextClassLoader().getResource("Images/save.png"));
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnAjusteLinealActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		private void ventana2Tramos(int nTramos){
			System.out.println("Número de tramos que le pasamos a MyJInternalFrame: " + nTramos);
			@SuppressWarnings("unused")
			MyFrame2 ventana = new MyFrame2(this.api, nTramos);
		}
		
		private void NTramos(){
			JFrame marco = new JFrame("Transformación lineal");
			JPanel panel = new JPanel();
			JLabel label = new JLabel("Números de tramos: ");
			JTextField tramos = new JTextField(2);
			JButton btnAccept = new JButton("Aceptar");
			JButton btnCancel = new JButton("Cancelar");
			//final int [] nTramos = new int [1];
			
			panel.setLayout( new GridLayout(2,2));
			panel.add(label);
			panel.add(tramos);
			panel.add(btnAccept);
			panel.add(btnCancel);
			panel.setVisible(true);
			marco.add(panel);
			marco.pack();
			marco.setVisible(true);
			
			btnAccept.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						marco.dispose();
						int nTramos = Integer.parseInt(tramos.getText());
						ventana2Tramos(nTramos);
						
					} catch(Exception a){
						//System.out.println("No ha introducido ningún valor");
					}
				}
			});
			
			btnCancel.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						marco.dispose();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		
		
		private void btnAjusteLinealActionPerformed(java.awt.event.ActionEvent evt) {
			NTramos();
		}
		
//----------------------------------------ECUALIZACION DEL HISTOGRAMA------------------------------------------
		void ecuaHisto(){
			createBtn("Ecualización del histograma",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnEcuaHistoActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		private void btnEcuaHistoActionPerformed(java.awt.event.ActionEvent evt) {
			int pos = getImageFromInternalFrame();
			api.imagenes.get(pos).ecualizarHisto();
		}		

}
