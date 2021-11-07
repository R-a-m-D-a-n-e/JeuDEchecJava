package Pieces;

import javax.swing.ImageIcon;

import Coordonnees.Coordonnees;
import Damier.Damier;

public class Dame extends Piece {

	public static final ZoneMove z=new ZoneMove(Math.PI/4, 8,Damier.DIM,new double []{1.5,0});
	
	public Dame(int x,int y,int joueur) {
		super(x, y, joueur,null);
		
		ImageIcon icon;
		if(joueur==JOUEUR0)
		{
			icon=new ImageIcon(getClass().getResource("/Images/dame_B.png"));
		}
		else
		{
			icon=new ImageIcon(getClass().getResource("/Images/dame_N.png"));
		}

		this.img=icon.getImage();
	}

	@Override
	public Object clone() {
		return new Dame(position.x, position.y, joueur);
	}

	@Override
	public String toString() {
		if(joueur==JOUEUR0)
			return "d";
		return "D";
	}

//////////////////////////Deplacements /////////////////////
	

	public boolean coordonneValid(Coordonnees position) {
	
		return super.coordonneValid(position, z);
	}
	
	
	public boolean pieceDeplacable(Damier d,char c) {
		return super.pieceDeplacable(d, z,c);
	}

}
