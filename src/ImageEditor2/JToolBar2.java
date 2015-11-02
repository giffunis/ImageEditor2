package ImageEditor2;

import javax.swing.*;

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
			initHistogramaAbsoluto();
			initHistogramaAcumulado();
		}
		
		// function to create a Button
		void createBtn(String name, String imageRoute){
			btnItem = new JButton();
			btnItem.setName(name);
			btnItem.setToolTipText(name);
			btnItem.setSelected(false);
			btnItem.setIcon(new ImageIcon(imageRoute));	
		}
		
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
			ProcesamientoImagen objProcesamiento =new ProcesamientoImagen();
			BufferedImage auxImage;
			auxImage = objProcesamiento.abrirImagen();
			@SuppressWarnings("unused")
			Imagenes imagenCompleta = new Imagenes(auxImage,api);
		}
		

	//---------------------------------------- GUARDAR ---------------------------------------------

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
			JInternalFrame internalFrame = api.desktopPane.getSelectedFrame();
			String aux = internalFrame.getTitle();
			int pos = 0;
			for(int i = 0; i < api.imagenes.size(); i++){
				if(aux == api.imagenes.get(i).internalFrame.getTitle()){
					pos = i;
				}
			}
//			JOptionPane.showMessageDialog(new JFrame(), pos);
			ProcesamientoImagen imagenSalida = new ProcesamientoImagen();
			
			
			
			imagenSalida.imageActual = api.imagenes.get(pos).imagenReal;
			imagenSalida.guardarImagen();
		}
		
	//--------------------------------------BTN ESCALA DE GRISES----------------------------------
		
		void initEscalaGrises(){
			createBtn("Escala de grises","src/Images/EscalaGrises.png");
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnEscalaGrisesActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		static BufferedImage deepCopy(BufferedImage bi) {
	        ColorModel cm = bi.getColorModel();
	        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	        WritableRaster raster = bi.copyData(null);
	        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	    }
		
		private void btnEscalaGrisesActionPerformed(java.awt.event.ActionEvent evt) {
			JInternalFrame internalFrame = api.desktopPane.getSelectedFrame();
			String aux = internalFrame.getTitle();
			int pos = 0;
			for(int i = 0; i < api.imagenes.size(); i++){
				if(aux == api.imagenes.get(i).internalFrame.getTitle()){
					pos = i;
				}
			}
//			JOptionPane.showMessageDialog(new JFrame(), pos);
			ProcesamientoImagen imagenSalida = new ProcesamientoImagen();
			imagenSalida.imageActual = deepCopy(api.imagenes.get(pos).imagenReal);
			imagenSalida.imageActual = imagenSalida.escalaGrises();	
			@SuppressWarnings("unused")
			Imagenes imagenCompleta = new Imagenes(imagenSalida.imageActual,api);
		}

//		-----------------------------btnHistogramaAbsoluto	
		
		void initHistogramaAbsoluto(){
			createBtn("Histograma Absoluto","src/Images/histogram2.png");
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnHistogramaAbsolutoActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		private void btnHistogramaAbsolutoActionPerformed(java.awt.event.ActionEvent evt) {
			JInternalFrame internalFrame = api.desktopPane.getSelectedFrame();
			String aux = internalFrame.getTitle();
			int pos = 0;
			for(int i = 0; i < api.imagenes.size(); i++){
				if(aux == api.imagenes.get(i).internalFrame.getTitle()){
					pos = i;
				}
			}
			ProcesamientoImagen imagenSalida = new ProcesamientoImagen();
			imagenSalida.imageActual = api.imagenes.get(pos).imagenReal;
			Vector<Integer> vectorHist = imagenSalida.histogramaAbsoluto();
			
			Histograma histo = new Histograma(this.api,HISTO_ABSO + ": imagen " + (pos + 1),vectorHist);
			
			ChartPanel panel = new ChartPanel(histo.grafica);
			JInternalFrame ventana = new JInternalFrame(HISTO_ABSO + ": imagen " + (pos + 1),true,true,true,true);
			ventana.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
			ventana.add(panel);
			ventana.pack();
			ventana.setVisible(true);
			this.api.desktopPane.add(ventana);
			
		}
		
		void initHistogramaAcumulado(){
			createBtn("Histograma acumulado","src/Images/histogram1.png");
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnHistogramaAcumulativoActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		private void btnHistogramaAcumulativoActionPerformed(java.awt.event.ActionEvent evt) {
			JInternalFrame internalFrame = api.desktopPane.getSelectedFrame();
			String aux = internalFrame.getTitle();
			int pos = 0;
			for(int i = 0; i < api.imagenes.size(); i++){
				if(aux == api.imagenes.get(i).internalFrame.getTitle()){
					pos = i;
				}
			}
			ProcesamientoImagen imagenSalida = new ProcesamientoImagen();
			imagenSalida.imageActual = api.imagenes.get(pos).imagenReal;
			Vector<Integer> vectorHist = imagenSalida.histogramaAcumulativo();
			
			Histograma histo = new Histograma(this.api,HISTO_ACUM + ": imagen " + (pos + 1),vectorHist);
			
			ChartPanel panel = new ChartPanel(histo.grafica);
			JInternalFrame ventana = new JInternalFrame(HISTO_ACUM + ": imagen " + (pos + 1),true,true,true,true);
			ventana.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
			ventana.add(panel);
			ventana.pack();
			ventana.setVisible(true);
			this.api.desktopPane.add(ventana);
			
		}
		
	}
}
