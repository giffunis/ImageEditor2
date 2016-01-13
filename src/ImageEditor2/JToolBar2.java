package ImageEditor2;


import java.awt.GridLayout;
import java.awt.Point;
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
			gamma();
			especificacionHistograma();
			calc_entropia();
			BrilloContraste();
			difImages();
			roi();
			no_cambio();
			espHor();
			espVer();
			tras();
			escalado();
			rotar();
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
		
//----------------------------------------CORRECCIÓN GAMMA------------------------------------------
	
		void gamma(){
			createBtn("Corrección Gamma",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnGammaActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		private void btnGammaActionPerformed(java.awt.event.ActionEvent evt) {
			int pos = getImageFromInternalFrame();
			
			JFrame marco = new JFrame("Factor de gamma");
			JPanel panel = new JPanel();
			JLabel label = new JLabel("Factor gamma: ");
			JTextField factor = new JTextField(2);
			JButton btnAccept = new JButton("Aceptar");
			JButton btnCancel = new JButton("Cancelar");
			//final int [] nTramos = new int [1];
			
			panel.setLayout( new GridLayout(2,2));
			panel.add(label);
			panel.add(factor);
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
						double g = Double.parseDouble(factor.getText());
						marco.dispose();
						api.imagenes.get(pos).correcGamma(g);
						
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
		
		void especificacionHistograma(){
			createBtn("Especificación del Histograma",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
			btnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnEspecificacionHistogramaActionPerformed(evt);
	            }
	        });
			add(btnItem);
		}
		
		private void btnEspecificacionHistogramaActionPerformed(java.awt.event.ActionEvent evt) {
			int pos = getImageFromInternalFrame();
			api.imagenes.get(pos).espeHisto();
		}
		
		//----------------------------------------ENTROPÍA------------------------------------------
				void calc_entropia(){
					createBtn("Entropia",Thread.currentThread().getContextClassLoader().getResource("Images/histogram2.png"));
					btnItem.addActionListener(new java.awt.event.ActionListener() {
			            public void actionPerformed(java.awt.event.ActionEvent evt) {
			                btnEntropiaActionPerformed(evt);
			            }
			        });
					add(btnItem);
				}
				
				private void btnEntropiaActionPerformed(java.awt.event.ActionEvent evt) {
					int pos = getImageFromInternalFrame();
					
					JFrame marco = new JFrame("Entropía");
					JPanel panel = new JPanel();
					JLabel label = new JLabel("Entropía: " + api.imagenes.get(pos).entropia());
					JButton btnAccept = new JButton("Aceptar");
					
					panel.setLayout( new GridLayout(2,1));
					panel.add(label);
					panel.add(btnAccept);
					panel.setVisible(true);
					marco.add(panel);
					marco.pack();
					marco.setVisible(true);
					
					btnAccept.addActionListener(new ActionListener() {	
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
				
		//----------------------------------------BRILLO Y CONTRASTE------------------------------------------

	void BrilloContraste(){
		createBtn("Brillo y Contraste",Thread.currentThread().getContextClassLoader().getResource("Images/histogram2.png"));
		btnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrilloContrasteActionPerformed(evt);
            }
        });
		add(btnItem);
	}
	
	private void btnBrilloContrasteActionPerformed(java.awt.event.ActionEvent evt) {
		int pos = getImageFromInternalFrame();
		
		JFrame marco = new JFrame("Brillo y Contraste");
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Brillo: " + api.imagenes.get(pos).getBrillo());
		JLabel label2 = new JLabel("Contraste: " + api.imagenes.get(pos).getContraste());
		JButton btnAccept = new JButton("Aceptar");
		JButton btnCancel = new JButton("Cancelar");
		JTextField newBrillo = new JTextField(2);
		JTextField newContraste = new JTextField(2);
		
		panel.setLayout( new GridLayout(3,2));
		panel.add(label);
		panel.add(newBrillo);
		panel.add(label2);
		panel.add(newContraste);
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
					int brillo = Integer.parseInt(newBrillo.getText());
					int contraste = Integer.parseInt(newContraste.getText());
					api.imagenes.get(pos).setBrilloContraste(brillo, contraste);
					marco.dispose();
					
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
	
	//----------------------------------------DIFERENCIA DE IMAGEN------------------------------------------
	

	void difImages(){
		createBtn("Diferencia de imágenes",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
		btnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDifImagesActionPerformed(evt);
            }
        });
		add(btnItem);
	}
	
	private void btnDifImagesActionPerformed(java.awt.event.ActionEvent evt) {
		int pos = getImageFromInternalFrame();
		api.imagenes.get(pos).diferenciaImagenes();
	}	
	
	//----------------------------------------ROI------------------------------------------

	
	void roi(){
		createBtn("ROI",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
		btnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnROIActionPerformed(evt);
            }
        });
		add(btnItem);
	}
	
	private void btnROIActionPerformed(java.awt.event.ActionEvent evt) {
		int pos = getImageFromInternalFrame();
		Point origen = api.imagenes.get(pos).origin;
		Point end = api.imagenes.get(pos).end;
		System.out.println("PuntoOrigen " + origen);
		System.out.println("PuntoOrigen " + end);
		
		api.imagenes.get(pos).regionInteres(origen,end);
		
	}	
	
	//----------------------------------------NO CAMBIO------------------------------------------
	
	void no_cambio(){
		createBtn("Umbral T",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
		btnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNoCambioActionPerformed(evt);
            }
        });
		add(btnItem);
	}
	
	private void btnNoCambioActionPerformed(java.awt.event.ActionEvent evt) {
		int pos = getImageFromInternalFrame();
		
		JFrame marco = new JFrame("Imagen de Cambios");
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Umbral T: ");
		JTextField factor = new JTextField(2);
		JButton btnAccept = new JButton("Aceptar");
		JButton btnCancel = new JButton("Cancelar");
		//final int [] nTramos = new int [1];
		
		panel.setLayout( new GridLayout(2,2));
		panel.add(label);
		panel.add(factor);
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
					int t = Integer.parseInt(factor.getText());
					marco.dispose();
					api.imagenes.get(pos).noCambio(t);
					
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
	
	//----------------------------------------ESPEJO HORIZONTAL------------------------------------------
	

	void espHor(){
		createBtn("Espejo Horizontal",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
		btnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEspHorActionPerformed(evt);
            }
        });
		add(btnItem);
	}
	
	private void btnEspHorActionPerformed(java.awt.event.ActionEvent evt) {
		int pos = getImageFromInternalFrame();
		api.imagenes.get(pos).espejoHorizontal();
	}	


	//----------------------------------------ESPEJO VERTICAL------------------------------------------
	

	void espVer(){
		createBtn("Espejo Horizontal",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
		btnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEspVerActionPerformed(evt);
            }
        });
		add(btnItem);
	}
	
	private void btnEspVerActionPerformed(java.awt.event.ActionEvent evt) {
		int pos = getImageFromInternalFrame();
		api.imagenes.get(pos).espejoVertical();
	}	
	
	//----------------------------------------TRASPUESTA------------------------------------------
	

	void tras(){
		createBtn("Traspuesta",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
		btnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraspuestaActionPerformed(evt);
            }
        });
		add(btnItem);
	}
	
	private void btnTraspuestaActionPerformed(java.awt.event.ActionEvent evt) {
		int pos = getImageFromInternalFrame();
		api.imagenes.get(pos).traspuesta();
	}	
	
	//----------------------------------------ESCALADO------------------------------------------
	

	void escalado(){
		createBtn("Escalado",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
		btnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscaladoActionPerformed(evt);
            }
        });
		add(btnItem);
	}
	
	private void btnEscaladoActionPerformed(java.awt.event.ActionEvent evt) {
		int pos = getImageFromInternalFrame();
		api.imagenes.get(pos).escalar(false,700,700);
	}
	
	//----------------------------------------ROTACIONES------------------------------------------
	
	void rotar(){
		createBtn("Rotar",Thread.currentThread().getContextClassLoader().getResource("Images/histogram1.png"));
		btnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRotarActionPerformed(evt);
            }
        });
		add(btnItem);
	}
	
	private void btnRotarActionPerformed(java.awt.event.ActionEvent evt) {
		int pos = getImageFromInternalFrame();
		int grados = 70;
		int valorAux = 360 - grados;
		api.imagenes.get(pos).rotacion(false,valorAux);
	}
	
	
}
