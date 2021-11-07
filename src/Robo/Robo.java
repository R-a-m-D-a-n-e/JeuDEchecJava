package Robo;

import java.util.ArrayList;

import Coordonnees.Coordonnees;
import Damier.Damier;
import Pieces.Piece;
import Play.Play;

public class Robo {

	private static ArrayList<Piece> listPiece;
	private Coordonnees pieceMove;
	private static ArrayList<Coordonnees> caseMove;
	
	public Robo(Damier d)
	{
		listPiece=new ArrayList<Piece>();
		pieceMove=null;
		caseMove=new ArrayList<Coordonnees>();
		
		for(int i=0;i<Damier.DIM;i++)
		{
			for(int j=0;j<Damier.DIM;j++)
			{
				if(d.getDamier(i, j) != null && d.getDamier(i, j).getJoueur()==Piece.JOUEUR1)
					listPiece.add(d.getDamier(i, j));
			}
		}
	}
	
	public static void popListRobo(Piece p)
	{
		listPiece.remove(p);
	}
	
	public static void addCaseMove(Coordonnees xy)
	{
		caseMove.add(xy);
	}
	
	public String toString()
	{
		String res="";
		
		for(Piece p:listPiece)
		{
			res+=p.toString()+" ";
		}
		
		return res;
	}
	
	private void pieceMove()
	{
		pieceMove= listPiece.get((int)(Math.random()*listPiece.size())).getPosition();
	}
	
	private void caseMove()
	{
		Play.play.getPartie().getDamier().getDamier(pieceMove).pieceDeplacable(Play.play.getPartie().getDamier(), 'R');
	}
	
	public void  moveRobo()
	{
		Coordonnees choix=null;
		
		while(caseMove.size()==0)
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pieceMove();
			caseMove();
		}
		
		for(Coordonnees c: caseMove)
		{
			if(Play.play.getPartie().getDamier().getDamier(c)!=null)
				choix=c;
		}
		
		if(choix==null)
			choix=caseMove.get((int)(Math.random()*caseMove.size()));
		
		try {
			Play.play.getPartie().getDamier().getDamier(pieceMove).move(choix,Play.play.getPartie().getDamier(),'R');
		}
		catch(Exception e){}
		
		caseMove.clear();
		Play.play.getPartie().changeJoueur();
	}
	
	
}



















