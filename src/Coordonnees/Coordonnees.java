package Coordonnees;

import Damier.Damier;

public class Coordonnees {
	public int x,y;
	
	public Coordonnees(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	
	
	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}

	public boolean equals(Object o)
	{
		if(o==null)
			return false;
		if(o==this)
			return true;
		Coordonnees tmp=(Coordonnees)o;
		
		if(tmp.x==x && tmp.y==y)
			return true;
		return false;
		}
	
	public Coordonnees clone()
	{
		return new Coordonnees(x, y);
	}
	
	public Coordonnees add(Coordonnees xy)
	{
		return new Coordonnees(x+xy.x, y+xy.y);
	}
	
	public boolean coordonneValid()
	{
		return !(x<0 || y<0 || x>=Damier.DIM || y>=Damier.DIM);
	}
	
	public Coordonnees calculerTangente(Coordonnees xy)
	{
		int x,y;
		
		x=this.x-xy.x;
		y=this.y-xy.y;
		
		if(y!=0)
			y/=Math.abs(y);
		if(x!=0)
			x/=Math.abs(x);
		
		return new Coordonnees(x, y);
	}
	
}










