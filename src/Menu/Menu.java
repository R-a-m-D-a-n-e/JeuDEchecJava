package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Partie.Partie;



@SuppressWarnings("serial")
public class Menu extends JFrame implements ActionListener,MouseListener{
	private JPanel panel;
	private JButton joueur1,joueur2,exit;
	private Clip musicMenu,clic,select;
	
	public Menu()
	{
		super("Jeu d'echec");
		
		try {
			AudioInputStream audio= AudioSystem.getAudioInputStream(getClass().getResource("/son/musicMenu.wav"));
			musicMenu=AudioSystem.getClip();
			musicMenu.open(audio);
			
			audio= AudioSystem.getAudioInputStream(getClass().getResource("/son/clic.wav"));
			clic=AudioSystem.getClip();
			clic.open(audio);
			
			musicMenu.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		panel=new JPanel();
		panel.setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,495);//changer a (450,505) pour si elle sera importe sous forame jar si non (450,495)
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setContentPane(panel);
		
		joueur1 = new JButton();
		joueur1.setBounds(160, 260, 135, 30);
		joueur1.setIcon(new ImageIcon(getClass().getResource("/icons/joueur1.png")));
		joueur1.addActionListener(this);
		joueur1.setBorderPainted(false);
		joueur1.setFocusPainted(false);
		joueur1. setContentAreaFilled(false);
		joueur1.addMouseListener(this);
		panel.add(joueur1);
		
		joueur2 = new JButton();
		joueur2.setBounds(160, 300, 135, 30);
		joueur2.setIcon(new ImageIcon(getClass().getResource("/icons/joueur2.png")));
		joueur2.addActionListener(this);
		joueur2.setBorderPainted(false);
		joueur2.setFocusPainted(false);
		joueur2. setContentAreaFilled(false);
		joueur2.addMouseListener(this);
		panel.add(joueur2);
		
		exit = new JButton();
		exit.setBounds(160, 340, 135, 30);
		exit.setIcon(new ImageIcon(getClass().getResource("/icons/exit1.png")));
		exit.addActionListener(this);
		exit.setBorderPainted(false);
		exit.setFocusPainted(false);
		exit. setContentAreaFilled(false);
		exit.addMouseListener(this);
		panel.add(exit);
		
		JLabel imag=new JLabel();
		imag.setIcon(new ImageIcon(getClass().getResource("/Images/echecs.jpg")));
		imag.setBounds(0, 0, 450, 470);
		panel.add(imag);
		
		setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		clic.start();
		
		if(e.getSource()==exit)
		{
			System.exit(0);
			Partie partie=new Partie(false);
			Play.Play.play.newPartie(partie);
			this.setVisible(false);
			musicMenu.close();
			select.close();
		}
		else
		{
			Partie partie;
			if(e.getSource()==joueur1)
			{
				partie=new Partie(true);
			}
			else
			{
				partie=new Partie(false);
			}
			Play.Play.play.newPartie(partie);
			this.setVisible(false);
			musicMenu.close();
			select.close();
		}
			
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		try {
			AudioInputStream audio= AudioSystem.getAudioInputStream(getClass().getResource("/son/select.wav"));
			select=AudioSystem.getClip();
			select.open(audio);
			select.start();
		}
		catch(Exception e1){}
		
		if(e.getSource()==joueur1)
			joueur1.setIcon(new ImageIcon(getClass().getResource("/icons/joueur1_v.png")));
		
		if(e.getSource()==joueur2)
			joueur2.setIcon(new ImageIcon(getClass().getResource("/icons/joueur2_v.png")));
		
		if(e.getSource()==exit)
			exit.setIcon(new ImageIcon(getClass().getResource("/icons/exit2.png")));
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
		if(e.getSource()==joueur1)
			joueur1.setIcon(new ImageIcon(getClass().getResource("/icons/joueur1.png")));
		
		if(e.getSource()==joueur2)
			joueur2.setIcon(new ImageIcon(getClass().getResource("/icons/joueur2.png")));
		
		if(e.getSource()==exit)
			exit.setIcon(new ImageIcon(getClass().getResource("/icons/exit1.png")));
	}


	@Override
	public void mousePressed(MouseEvent e) {
	}


	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
