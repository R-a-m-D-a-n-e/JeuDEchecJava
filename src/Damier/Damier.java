package Damier;


import Coordonnees.Coordonnees;
import Pieces.Cavaliers;
import Pieces.Dame;
import Pieces.Fous;
import Pieces.Piece;
import Pieces.Pion;
import Pieces.Roi;
import Pieces.Tours;

public class Damier {
	
	public static final int DIM=8;
	private Piece[][] damier=new Piece[DIM][DIM];
	private Roi roi_b,roi_n;
	
	public Damier()
	{
		for(int i=0;i<DIM;i++)
		{
			damier[1][i]=new Pion(1,i,Piece.JOUEUR1);
			damier[DIM-2][i]=new Pion(DIM-2,i,Piece.JOUEUR0);
		}
		
		damier[0][0]=new Tours(0,0,Piece.JOUEUR1);
		damier[0][7]=new Tours(0,7,Piece.JOUEUR1);
		damier[7][0]=new Tours(7,0,Piece.JOUEUR0);
		damier[7][7]=new Tours(7,7,Piece.JOUEUR0);
		
		damier[0][1]=new Fous(0,1,Piece.JOUEUR1);
		damier[0][6]=new Fous(0,6,Piece.JOUEUR1);
		damier[7][1]=new Fous(7,1,Piece.JOUEUR0);
		damier[7][6]=new Fous(7,6,Piece.JOUEUR0);
		
		damier[0][2]=new Cavaliers(0,2,Piece.JOUEUR1,'g');
		damier[0][5]=new Cavaliers(0,5,Piece.JOUEUR1,'d');
		damier[7][2]=new Cavaliers(7,2,Piece.JOUEUR0,'g');
		damier[7][5]=new Cavaliers(7,5,Piece.JOUEUR0,'d');
		
		damier[0][3]=new Dame(0,3,Piece.JOUEUR1);
		damier[7][3]=new Dame(7,3,Piece.JOUEUR0);
		
		damier[0][4]=new Roi(0,4,Piece.JOUEUR1);
		roi_n=(Roi)(damier[0][4]);
		
		damier[7][4]=new Roi(7,4,Piece.JOUEUR0);
		roi_b=(Roi)(damier[7][4]);
		
		
	}
	
	public Damier(Damier d)
	{
		for(int i=0;i<DIM;i++)
		{
			for(int j=0;j<DIM;j++)
			{
				if(d.getDamier(i, j)!=null)
					damier[i][j]=(Piece) d.getDamier(i, j).clone();
				else
					damier[i][j]=null;
			}
		}
		roi_n=d.roi_n;
		roi_b=d.roi_b;
	}
	
	public Piece getDamier(int x,int y)
	{
		
		return damier[x][y];
	}
	
	
	public Piece getDamier(Coordonnees position)
	{
		
		return damier[position.x][position.y];
	}
	
	public void setDamier(Coordonnees position,Piece p)
	{
		damier[position.x][position.y]=p;
	}
	
	public void setDamier(int x,int y,Piece p)
	{
		damier[x][y]=p;
	}
	
	public void setRoi(Roi roi)
	{
		if(roi.getJoueur()==Piece.JOUEUR0)
			roi_b=roi;
		roi_n=roi;
	}
	
	public boolean getRoiMort()
	{
		return roi_b.getMort() || roi_n.getMort();
	}
	
	public String toString()
	{
		String res="   ";
		
		for(int i=0;i<DIM;i++)
			res+=" "+i+" ";
		
		res+="\n";
		
		for(int i=0;i<DIM;i++)
		{
			res+=" "+i+" ";
			
			for(int j=0;j<DIM;j++)
			{
				if(damier[i][j]==null)
				{
					res+=" . ";
				}
				else
				{
					res+=" "+damier[i][j].toString()+" ";
				}
			}
			res+="\n";
		}
		
		return res;
	}
	
	public String toString(int p)
	{
		String res="     ";
		
		for(int i=0;i<DIM;i++)
			res+="  "+i+"  ";
		
		res+="\n";
		
		for(int i=0;i<DIM;i++)
		{
			res+="  "+i+"  ";
			
			for(int j=0;j<DIM;j++)
			{
				if(damier[i][j]==null)
				{
					res+="  .  ";
				}
				else
				{
					res+=damier[i][j].getPosition().toString();
				}
			}
			res+="\n";
		}
		
		return res;
	}
	
	public Object clone()
	{
		return new Damier(this);
	}
	
}














