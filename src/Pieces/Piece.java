package Pieces;

import java.awt.Image;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Coordonnees.Coordonnees;
import Damier.Damier;
import Play.Play;
import Robo.Robo;

public abstract class Piece {
	
	private static Clip move,captur;
	public static final int JOUEUR0=0,JOUEUR1=1;
	protected Coordonnees position;
	protected final int joueur;
	protected Image img;
	
	public Piece(int x,int y,int joueur,Image img)
	{
		this.position=new Coordonnees(x, y);
		this.joueur=joueur;
		this.img=img;
		try {
			AudioInputStream audio= AudioSystem.getAudioInputStream(getClass().getResource("/son/move.wav"));
			move=AudioSystem.getClip();
			move.open(audio);
			
			audio= AudioSystem.getAudioInputStream(getClass().getResource("/son/captur.wav"));
			captur=AudioSystem.getClip();
			captur.open(audio);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public Coordonnees getPosition()
	{
		return position;
	}
	
	public Image getImage()
	{
		return img;
	}
	
	public void setImage(Image img)
	{
		this.img=img;
	}
	
	public int getJoueur()
	{
		return joueur;
	}
	
	public boolean equals(Object o)
	{
		if(o==null)
			return false;
		if(o==this)
			return true;
		Piece tmp=(Piece)o;
		
		if(tmp.joueur==joueur && position.equals(tmp.position))
			return true;
		return false;		
	}
	
	public abstract Object clone();
	public abstract String toString();
	public String toString(int i)
	{
		return position.toString();
	}
	
///////////////////////////////////////   Les deplacements   //////////////////////////	
	
	
	protected boolean coordonneValid(Coordonnees position,ZoneMove z) {

		
		if( !position.coordonneValid())
			return false;
		
		for(int k=0;k<z.TOURS;k++)
		{
			if(z.testLinaier(new Coordonnees((this.position.x-position.x), (this.position.y-position.y))))
				return true;
			
			z.rotation();
		}
		
		return false;
	}

	public abstract boolean coordonneValid(Coordonnees xy);

	protected boolean tryMove(Coordonnees position,Damier d,Coordonnees vect,char c)
	{
		Coordonnees dxy=this.position.clone().add(vect);
		
		boolean deplacable=false;

		while(dxy.coordonneValid()  && !dxy.equals(position))
		{
			if(d.getDamier(dxy)!=null)
				break;
			
			deplacable=true;
		
			if(c=='r')
			{
				ajouterLab(dxy,"move.png");
			}
			if(c=='R')
			{
				Robo.addCaseMove(dxy);
			}
			dxy=dxy.add(vect);
		}
		
		if(dxy.coordonneValid() && (d.getDamier(dxy)==null || d.getDamier(dxy).joueur!=joueur))
		{
			if(c=='r')
			{
				if(d.getDamier(dxy)!=null)
					ajouterLab(dxy,"move2.png");
				else
					ajouterLab(dxy,"move.png");
				return true;
			}
			if(c=='R')
			{
				Robo.addCaseMove(dxy);
			}
			
			if(dxy.equals(position))
			{
				return true;
			}
		}
		
		if(c=='u')
			return false;
		return deplacable;
	
		
	}
	
	
	public boolean testMove(Coordonnees position, Damier d) {
		
		if(this instanceof Cavaliers)
		{
			Cavaliers c=(Cavaliers)this;
			return  coordonneValid(position) && tryMove(position, d,c.getVect(position),'u');
		}
		return  coordonneValid(position) && tryMove(position, d,position.calculerTangente(this.position),'u');
	}
	
	public void move(Coordonnees position,Damier d,char c)
	{
		RuntimeException e=new RuntimeException("coordonnee no valide\n");
		
		if(testMove(position,d))
		{
			
			if(d.getDamier(position) instanceof Roi)
			{
				Roi r=(Roi)(d.getDamier(position));
				r.setMort();
			}
			if(d.getDamier(position)!=null)
			{
				captur.start();
				if(c=='u' && Play.play.getPartie().getActionRobo())
				{
					Robo.popListRobo(d.getDamier(position));
				}
			}
			else
				move.start();
				
			d.setDamier(position,d.getDamier(this.position));
			d.setDamier(this.position,null);
			this.position=position.clone();
			if(d.getDamier(this.position) instanceof Pion)
			{
				Pion p=(Pion)(d.getDamier(this.position));
				if(p.getCaseX2())
					p.setCaseX2();
				if(p.testPromu())
					p.promu();
			}
		}
		else 
		{
			throw e;
		}
	}
	
	public abstract boolean pieceDeplacable(Damier d,char c);
	
	protected boolean pieceDeplacable(Damier d,ZoneMove z,char c) {
		Coordonnees tmp;
		boolean res=false;
		
		for(int k=0;k<z.TOURS;k++)
		{
			tmp=position.clone();
			tmp=tmp.add(z.getRayon());
			
			if(tryMove(tmp, d, z.getVect(),c))
				res= true;
			
			z.rotation();
		}
		
		
		return res;
	}
	
	public void ajouterLab(Coordonnees position,String s)
	{
		JLabel p=new JLabel();
		p.setBounds(position.y*50+23, position.x*50+23, 50, 50);
		p.setIcon(new ImageIcon(getClass().getResource("/Images/"+s)));
		Play.play.getPartie().add(p);
	}
		

}
