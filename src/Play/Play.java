package Play;

import javax.swing.JFrame;

import BarMenu.BarMenu;
import Partie.Partie;

@SuppressWarnings("serial")
public class Play extends JFrame{
	
	public static final Play play=new Play();
	private Partie partie;
	
	private Play()
	{
		super("Jeu d'echec");
		partie=null;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,490);
		setResizable(false);
		setLocationRelativeTo(null);
		//setAlwaysOnTop(true);
		setJMenuBar(new BarMenu());
		setVisible(true);
		
	}
	
	public void start()
	{
		Paint p=new Paint();
		p.start();
		Start go=new Start();
		
		go.start();
	}
	
	public Partie getPartie()
	{
		return partie;
	}
	
	public void newPartie(Partie p)
	{
		partie=p;
		setContentPane(partie);
		setVisible(true);
		start();
	}

}
















