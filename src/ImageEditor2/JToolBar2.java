package ImageEditor2;

import javax.swing.*;

import Imagenes.Imagenes;

@SuppressWarnings("serial")
public class JToolBar2 extends JToolBar{
		
		static final String HISTO_ABSO = "Histograma Absoluto";
		static final String HISTO_ACUM = "Histograma Acumulativo";
		
		ImageEditor2 api;
		JButton btnItem;
		
		public JToolBar2(ImageEditor2 api){
			this.api = api;
			initAbrir();
			initGuardar();
			initEscalaGrises();
		}
		
		// function to create a Button
		void createBtn(String name, String imageRoute){
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
			createBtn("Abrir","src/Images/open.png");
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
			createBtn("Guardar","src/Images/save.png");
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
			createBtn("Escala de grises","src/Images/EscalaGrises.png");
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
		
		
}
