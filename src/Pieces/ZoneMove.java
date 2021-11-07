package Pieces;

import java.util.Arrays;

import Coordonnees.Coordonnees;

public class ZoneMove {

	public final double THETA,TOURS,RAYON;
	private double [] vect;

	public ZoneMove(double tHETA, double tOURS, double rAYON, double[] vect) {
		THETA = tHETA;
		TOURS = tOURS;
		RAYON = rAYON;
		this.vect = vect;
	}

	
	
	public String toString() {
		return  Arrays.toString(vect);
	}



	public Coordonnees getVect()
	{
		return new Coordonnees((int)(vect[0]), (int)(vect[1]));
	}
	
	public void rotation() 
	{
		double tmp=vect[0];
		
		vect[0]=(vect[0]*Math.cos(THETA)-vect[1]*Math.sin(THETA));
		vect[1]=(tmp*Math.sin(THETA)+vect[1]*Math.cos(THETA));
	}
	
	public Coordonnees getRayon()
	{
		return new Coordonnees((int)(vect[0]*RAYON), (int)(vect[1]*RAYON));
	}
	
	
	public boolean testLinaier(Coordonnees xy)
	{
		return (xy.x)*(int)(vect[1])==(int)(vect[0])*(xy.y);
	}


}
