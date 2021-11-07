package Play;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Paint extends Thread{
	
	public Paint()
	{
		super();
	}
	
	public void run()
	{
		while(!Play.play.getPartie().testFinPartie())
		{
			Play.play.getPartie().repaint();
		}
		
		JLabel l=new JLabel();
		l.setIcon(new ImageIcon(getClass().getResource("/Images/echec_et_mat.png"))); 
		l.setBounds(80, 170, 300, 77);
		Play.play.getPartie().add(l);
		Play.play.getPartie().getSon().start();
		Play.play.getPartie().repaint();
		
	}

}
