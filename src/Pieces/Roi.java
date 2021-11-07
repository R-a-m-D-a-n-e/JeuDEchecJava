package Pieces;

import javax.swing.ImageIcon;

import Coordonnees.Coordonnees;
import Damier.Damier;

public class Roi extends Piece {

	public static final ZoneMove z=new ZoneMove(Math.PI/4, 8,1,new double []{1.5,0});
	private boolean mort;
	
	public Roi(int x, int y, int joueur) {
		super(x, y, joueur,null);
		mort=false;
		ImageIcon icon;
		if(joueur==JOUEUR0)
		{
			icon=new ImageIcon(getClass().getResource("/Images/roi_B.png"));
		}
		else
		{
			icon=new ImageIcon(getClass().getResource("/Images/roi_N.png"));
		}

		this.img=icon.getImage();
	}

	@Override
	public Object clone() {
		return new Roi(position.x, position.y, joueur);
	}
	
	public String toString()
	{
		if(joueur==JOUEUR0)
			return "r";
		return "R";
	}
	
	public boolean getMort()
	{
		return mort;
	}
	
	public void setMort()
	{
		mort=true;
	}
	
//////////////////////////  Deplacements /////////////////////
	
	public boolean coordonneValid(Coordonnees position) {

		
		return super.coordonneValid(position, z);
	}

	
	public boolean pieceDeplacable(Damier d,char c) {
		return super.pieceDeplacable(d, z,c);
	}
}
