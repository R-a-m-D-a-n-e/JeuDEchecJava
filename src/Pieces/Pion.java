package Pieces;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Coordonnees.Coordonnees;
import Damier.Damier;
import Robo.Robo;

public class Pion extends Piece implements MouseListener{
	
	private boolean caseX2;
	private char choixPromu;
	private JPanel listLab;
	public Pion(int x,int y,int joueur)
	{
		super(x, y, joueur,null);
		caseX2=true;
		choixPromu='x';
		listLab=new JPanel();
		ImageIcon icon;
		if(joueur==JOUEUR0)
		{
			icon=new ImageIcon(getClass().getResource("/Images/pions_B.png"));
		}
		else
		{
			icon=new ImageIcon(getClass().getResource("/Images/pions_N.png"));
		}

		this.img=icon.getImage();
	}
	

	@Override
	public Object clone() {
		return new Pion(position.x,position.y,joueur);
	}
	
	public String toString()
	{
		if(joueur==JOUEUR0)
			return "p";
		return "P";
	}
	
	public boolean getCaseX2()
	{
		return caseX2;
	}
	
	public void setCaseX2()
	{
		caseX2=false;
	}

	
////////////////////////Deplacements ///////////////////
	
	public boolean  coordonneValid(Coordonnees xy)
	{
		return xy.coordonneValid();
	}
		
	protected boolean tryMove(Coordonnees position,Damier d,Coordonnees vect,char c)
	{
		if (!position.coordonneValid())
			return false;
		
		if(joueur==JOUEUR0)
		{
			vect.x=-1;
		}
		
		if(d.getDamier(position)==null)
		{

			if((position.y-this.position.y)==0)
			{
			
				if((position.x-this.position.x)==vect.x )
				{
					if(c=='r')
					{
						ajouterLab(position,"move.png");
					}
					if(c=='R')
					{
						Robo.addCaseMove(position);
					}
					return true;
				}
				if(caseX2)
				{
					vect.x*=2;

					if((position.x-this.position.x)==vect.x && d.getDamier((int)((position.x+this.position.x)/2),(int)((position.y+this.position.y)/2))==null)
					{
						if(c=='r')
						{
							ajouterLab(position,"move.png");
						}
						if(c=='R')
						{
							Robo.addCaseMove(position);
						}
						return true;
					}
				}
			}
		}
		else {
		
			if(
					(
					vect.y!=0 && d.getDamier(position).joueur!=joueur &&
					(((position.x-this.position.x)==vect.x && (position.y-this.position.y)==vect.y) || 
					((position.x-this.position.x)==vect.x && (position.y-this.position.y)==-vect.y))
					)
	
				)
			{
				if(c=='r')
				{
					ajouterLab(position,"move2.png");
				}
				if(c=='R')
				{
					Robo.addCaseMove(position);
				}
				return true;
			}
		}
		
		return false;
	}
	
	public boolean testMove(Coordonnees position, Damier d) {
	
		return  tryMove(position, d,position.calculerTangente(this.position),'u');
	}
	
	
	@Override
	public boolean pieceDeplacable(Damier d,char s) {
		
		int tmp=1;
		
		boolean a,b,c;
		

		if(joueur==JOUEUR0)
			tmp=-1;
		
		a=tryMove(position.clone().add(new Coordonnees(tmp, tmp)), d,new Coordonnees(tmp, tmp),s);
		
		b=tryMove(position.clone().add(new Coordonnees(tmp, -tmp)), d,new Coordonnees(tmp, -tmp),s);
		
		c=tryMove(position.clone().add(new Coordonnees(tmp, 0)), d,new Coordonnees(tmp, 0),s) ;
		if(c && caseX2)
			tryMove(position.clone().add(new Coordonnees(tmp*2, 0)), d,new Coordonnees(tmp*2, 0),s) ;
		
		
		return 	a||b||c ;
	}
	
	private void addLab(int x,int y,char c,String s)
	{
		JLabel l=new JLabel(c+"");
		l.setIcon(new ImageIcon(getClass().getResource(s)));
		l.setBounds(x, y, 50,50);
		l.addMouseListener(this);
		listLab.add(l);
		
		Play.Play.play.getPartie().add(listLab);
	}

	public boolean testPromu()
	{
		if(joueur==Piece.JOUEUR0 && position.x==0)
			return true;
		
		if(position.x==7)
			return true;
		return false;
	}
	
	private void choixPromu()
	{
		int x,y;
		if(joueur==JOUEUR0)
		{
			x=position.y*50;
			y=position.x*50+71;
			if(x+200>430)
				x=x-((x+200)%430);
			listLab.setBounds(x, y, 200, 50);
			
			char c=Play.Play.play.getPartie().getState();
			
			Play.Play.play.getPartie().setState('.');
			
			addLab(0, 0, 't', "/Images/tours_B.png");
			addLab(50, 0, 'f', "/Images/fous_B.png");
			addLab(100, 0, 'c', "/Images/cavaliers_B.png");
			addLab(150, 0, 'd', "/Images/dame_B.png");
			Play.Play.play.getPartie().setState(c);
		}
		else
		{
			x=position.y*50;
			y=position.x*50-25;
			if(x+200>430)
				x=x-((x+200)%430);
			listLab.setBounds(x, y, 200, 50);
			
			char c=Play.Play.play.getPartie().getState();
			
			Play.Play.play.getPartie().setState('4');
			
			addLab(0, 0, 'T', "/Images/tours_N.png");
			addLab(50, 0, 'F', "/Images/fous_N.png");
			addLab(100, 0, 'C', "/Images/cavaliers_N.png");
			addLab(150, 0, 'D', "/Images/dame_N.png");
			Play.Play.play.getPartie().setState(c);
		}
	}
	
	public void promu()
	{
		if(Play.Play.play.getPartie().getActionRobo())
		{
			if(joueur==JOUEUR0)
				choixPromu='d';
			else
				choixPromu='D';
		}
		else
		{
			choixPromu();
		}

		while(choixPromu=='x')
		{try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
			Play.Play.play.getPartie().removeAll();
			Piece p=Play.Play.play.getPartie().creerPiece(choixPromu, position.x, position.y);
			Play.Play.play.getPartie().setCaseDamier(position.x, position.y, p);
			
			if(!Play.Play.play.getPartie().getActionRobo())
			{
				Play.Play.play.getPartie().addHistory(Play.Play.play.getPartie());
				Play.Play.play.getPartie().getHistory().remove(1);
			}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		
		JLabel source=(JLabel)e.getSource();

		try {
			AudioInputStream audio= AudioSystem.getAudioInputStream(getClass().getResource("/son/move.wav"));
			Clip son=AudioSystem.getClip();
			son.open(audio);
			son.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		choixPromu=source.getText().charAt(0);
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		try {
			AudioInputStream audio= AudioSystem.getAudioInputStream(getClass().getResource("/son/select2.wav"));
			Clip son=AudioSystem.getClip();
			son.open(audio);
			son.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		JLabel source=(JLabel)(e.getSource());
		JLabel select=new JLabel();
		select.setBounds(source.getX(),source.getY(), 50, 50);
		select.setIcon(new ImageIcon(getClass().getResource("/Images/select.png")));
		listLab.add(select);
	}


	@Override
	public void mouseExited(MouseEvent e) {
		listLab.remove(4);	
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
















