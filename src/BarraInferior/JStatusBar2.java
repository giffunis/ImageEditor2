package BarraInferior;


import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import Imagenes.Imagenes;

@SuppressWarnings("serial")


public class JStatusBar2 extends JPanel{

  JLabel sizeImage = null;
  JLabel typeImage = null;
  JLabel minMax = null;
  JLabel shadeOfGrayMouse = null;
  JLabel positionMouse = null;
  JLabel brillo = null;
  Imagenes image;

  public JStatusBar2(Imagenes image){
    super();
    this.image = image;
    this.setBorder(new BevelBorder(BevelBorder.LOWERED));
    this.setPreferredSize(new Dimension(this.getWidth(), 24));
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    init();
  }

  private void init(){
    sizeImage = new JLabel(image.getImageSize());
    sizeImage.setHorizontalAlignment(SwingConstants.LEFT);
    typeImage = new JLabel(image.getImageType());
    typeImage.setHorizontalAlignment(SwingConstants.LEFT);
    minMax = new JLabel(image.getMinMax());
    minMax.setHorizontalAlignment(SwingConstants.LEFT);
    brillo = new JLabel("Brillo: " + image.getBrillo());
    brillo.setHorizontalAlignment(SwingConstants.LEFT);
    shadeOfGrayMouse = new JLabel("Grey Color:");
    shadeOfGrayMouse.setHorizontalAlignment(SwingConstants.LEFT);
    positionMouse = new JLabel("pixel:");
    positionMouse.setHorizontalAlignment(SwingConstants.LEFT);
    
    
    this.add(sizeImage);
    this.add(new JLabel(" | "));
	this.add(typeImage);
	this.add(new JLabel(" | "));
	this.add(minMax);
	this.add(new JLabel(" | "));
	this.add(brillo);
	this.add(new JLabel(" | "));
	this.add(shadeOfGrayMouse);
	this.add(new JLabel(" | "));
	this.add(positionMouse);
  }

  public void setPositionMouse(Integer x, Integer y){
    if (x != null && y != null)
      positionMouse.setText("pixel: (" + x + "," + y + ")");
    else
      positionMouse.setText("pixel:");
  }

  public void setGrayMouse(Color color){
    if (color != null)
      shadeOfGrayMouse.setText("Grey Color: " + color.getRed() + " ");
    else
      shadeOfGrayMouse.setText("Grey Color:");
  }
  
}

