package BarMenu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import Damier.Damier;
import Partie.Partie;

@SuppressWarnings("serial")
public class BarMenu extends JMenuBar{

	public BarMenu()
	{
		super();
		
		///// File
		
		JMenu file=new JMenu("File");
		
			///////////// new
			
			JMenuItem neveau= new JMenuItem("New");
			neveau.setIcon(new ImageIcon(getClass().getResource("/icons/new.png")));
			neveau.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK) );
			neveau.addActionListener(this::mnuNewListener);
			file.add(neveau);
			file.addSeparator();
			
			///////////// open
			
			JMenuItem open= new JMenuItem("Open...");
			open.setIcon(new ImageIcon(getClass().getResource("/icons/open.png")));
			open.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK) );
			open.addActionListener(this::mnuOpenListener);
			file.add(open);
			file.addSeparator();
			
			///////////// save
			
			JMenuItem save= new JMenuItem("Save");
			save.setIcon(new ImageIcon(getClass().getResource("/icons/save.png")));
			save.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK) );
			save.addActionListener(this::mnuSaveListener);
			file.add(save);
			file.addSeparator();
			
			///////////// exit
			
			JMenuItem exit= new JMenuItem("exit");
			exit.setIcon(new ImageIcon(getClass().getResource("/icons/exit.png")));
			exit.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK) );
			exit.addActionListener(this::mnuExitListener);
			file.add(exit);
			file.addSeparator();
			
		this.add(file);
			
		////// Edit
		JMenu edit=new JMenu("Edit");
			
			///////////// undo
			
			JMenuItem undo= new JMenuItem("Undo");
			undo.setIcon(new ImageIcon(getClass().getResource("/icons/undo.png")));
			undo.addActionListener(this::mnuUndoListener);
			undo.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK) );
			edit.add(undo);
			edit.addSeparator();
			
			///////////// redo
			
			JMenuItem redo= new JMenuItem("Redo");
			redo.setIcon(new ImageIcon(getClass().getResource("/icons/redo.png")));
			redo.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK) );
			redo.addActionListener(this::mnuRedoListener);
			edit.add(redo);
			edit.addSeparator();
			
		this.add(edit);
		
		////// help
		JMenu help=new JMenu("?");
		
			///////////// about
			
			JMenuItem about= new JMenuItem("A propos");
			about.setIcon(new ImageIcon(getClass().getResource("/icons/about.png")));
			about.addActionListener( this::mnuAboutListener );
			help.add(about);
			help.addSeparator();
			
		this.add(help);
		
	}
	
	public void mnuAboutListener( ActionEvent event ) {
        JOptionPane.showMessageDialog ( Play.Play.play.getPartie() , "+ Sorbonne Université\n+ Licence Informatique\n+ Ramdane Salhi\n+ Jeu d'échec V3.8", "A propos de Jeux", 0, new ImageIcon(getClass().getResource("/icons/ghost.png")));
    }
	
	public void mnuNewListener( ActionEvent event ) {
		 Play.Play.play.getPartie().getSon().close();
		 Play.Play.play.newPartie(new Partie(Play.Play.play.getPartie().getActionRobo()));
	}
	
	public void mnuExitListener( ActionEvent event ) {
		
		
		if(JOptionPane.showConfirmDialog(Play.Play.play.getPartie(),"Sauvegarder avant de quitter ?","Save",0)==0)
			Play.Play.play.getPartie().save();
		System.exit(0);
    }
	
	public void mnuUndoListener( ActionEvent event ) {
		
		if(Play.Play.play.getPartie().getState()=='0')
		{
			Play.Play.play.getPartie().removeAll();
			
			if(Play.Play.play.getPartie().getPostionHistory()==1)
			{
				Play.Play.play.getPartie().setDamier((Damier) Play.Play.play.getPartie().getHistory().get(0).clone());
				Play.Play.play.getPartie().setPostionHistory(0);
				if(!Play.Play.play.getPartie().getActionRobo())
					Play.Play.play.getPartie().changeJoueur();
			}
			
		}
    }
	
	public void mnuRedoListener( ActionEvent event ) {
		
		if(Play.Play.play.getPartie().getState()=='0')
		{
			Play.Play.play.getPartie().removeAll();
			
			if(Play.Play.play.getPartie().getPostionHistory()==0 && Play.Play.play.getPartie().getHistory().size()==2)
			{
				Play.Play.play.getPartie().setDamier((Damier) Play.Play.play.getPartie().getHistory().get(1).clone());
				Play.Play.play.getPartie().setPostionHistory(1);
				if(!Play.Play.play.getPartie().getActionRobo())
					Play.Play.play.getPartie().changeJoueur();
			}
		}
    }
	
	public void mnuSaveListener( ActionEvent event ) {
		
		Play.Play.play.getPartie().save();
    }

	public void mnuOpenListener( ActionEvent event ) {
		
		Play.Play.play.getPartie().open();
	}
	
}
