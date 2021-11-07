package Pieces;

import javax.swing.ImageIcon;

import Coordonnees.Coordonnees;
import Damier.Damier;

public class Fous extends Piece{
	
	public static final ZoneMove z=new ZoneMove(Math.PI/2, 4,Damier.DIM,new double []{1,1.1});
	
	public Fous(int x,int y,int joueur) {
		super(x, y, joueur,null);
		ImageIcon icon;
		if(joueur==JOUEUR0)
		{
			icon=new ImageIcon(getClass().getResource("/Images/fous_B.png"));
		}
		else
		{
			icon=new ImageIcon(getClass().getResource("/Images/fous_N.png"));
		}

		this.img=icon.getImage();
	}

	@Override
	public Object clone() {
		return new Fous(position.x, position.y, joueur);
	}

	@Override
	public String toString() {
		if(joueur==JOUEUR0)
			return "f";
		return "F";
	}

//////////////////////////Deplacements /////////////////////

	public boolean coordonneValid(Coordonnees position) {
	
		return super.coordonneValid(position, z);
	}
	
	
	public boolean pieceDeplacable(Damier d,char c) {
		return super.pieceDeplacable(d, z,c);
	}

}
