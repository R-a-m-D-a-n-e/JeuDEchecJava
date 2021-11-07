package Pieces;

import javax.swing.ImageIcon;

import Coordonnees.Coordonnees;
import Damier.Damier;

public class Cavaliers extends Piece{
	
	public static final ZoneMove z=new ZoneMove(Math.PI/4, 8,1,new double []{2.7,1.2});
	private char type;
	
	public Cavaliers(int x,int y,int joueur,char type) {
		super(x, y, joueur,null);
		this.type=type;

		ImageIcon icon;
		if(type=='g')
		{
			if(joueur==JOUEUR0)
			{
				icon=new ImageIcon(getClass().getResource("/Images/cavaliers_B.png"));
			}
			else
			{
				icon=new ImageIcon(getClass().getResource("/Images/cavaliers_N2.png"));
			}
		}
		else
		{
			if(joueur==JOUEUR0)
			{
				icon=new ImageIcon(getClass().getResource("/Images/cavaliers_B2.png"));
			}
			else
			{
				icon=new ImageIcon(getClass().getResource("/Images/cavaliers_N.png"));
			}
		}

		img=icon.getImage();
	}

	@Override
	public Object clone() {
		return new Cavaliers(position.x,position.y,joueur,type);
	}

	@Override
	public String toString() {
		if(type=='g')
		{
			if(joueur==JOUEUR0)
				return "c";
			return "C";
		}
		if(joueur==JOUEUR0)
			return "s";
		return "S";
	}

//////////////////////////Deplacements /////////////////////
	
	public Coordonnees getVect(Coordonnees xy)
	{
		Coordonnees tmp;

		for(int k=0;k<z.TOURS;k++)
		{
			
			tmp=position.clone();
			tmp=tmp.add(z.getVect());
			
			if(tmp.equals(xy))
				return z.getVect();
			
			z.rotation();
		}
		
		return null;
	}
	
	public boolean coordonneValid(Coordonnees position) {
	
		return super.coordonneValid(position, z);
	}
	
	
	public boolean pieceDeplacable(Damier d,char c) {
		return super.pieceDeplacable(d, z,c);
	}

}
