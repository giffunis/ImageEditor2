package TramosLineal;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyDrawPanel extends JPanel {

	MyDrawPanel(){
		super();
		this.setSize(400, 400);
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
		this.setVisible(true);
	}
	
}
