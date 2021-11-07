package Partie;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import Coordonnees.Coordonnees;
import Damier.Damier;
import Pieces.Cavaliers;
import Pieces.Dame;
import Pieces.Fous;
import Pieces.Piece;
import Pieces.Pion;
import Pieces.Roi;
import Pieces.Tours;
import Robo.Robo;

@SuppressWarnings("serial")
public class Partie extends JPanel implements MouseListener {
	
	private int cpt=0;
 
	private Clip echecEtMat;
	private char state;
	private Damier damier;
	private Robo robo;
	private boolean roboAction;
	private int joueur;
	private Coordonnees positionPiece,positionCase;
	private Coordonnees clicMouse;
	private ArrayList<Damier> history ;
	private int h;

	public Partie(boolean actionRobo)
	{
		super();
		try {
			AudioInputStream audio= AudioSystem.getAudioInputStream(getClass().getResource("/son/EchecEtMat.wav"));
			echecEtMat=AudioSystem.getClip();
			echecEtMat.open(audio);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		damier=new Damier();
		//test();
		
		if(actionRobo)
		{
			robo =new Robo(damier);
			this.roboAction=actionRobo;
		}
		state='0';
		clicMouse=null;
		
		joueur=Piece.JOUEUR0;
		positionCase=new Coordonnees(0, 0);
		positionCase=new Coordonnees(0, 0);
		history=new ArrayList<Damier>();
		h=0;
		addHistory(this);
		
		setLayout(null);

		this.addMouseListener(this);
		
	}

	public char getState()
	{
		return state;
	}
	
	public void setState(char c)
	{
		state=c;
	}
	
	public Clip getSon()
	{
		return echecEtMat;
	}
	
	public String toString()
	{
		return "\t"+joueur+"\n"+damier.toString(1);
	}
	
	public void afficher()
	{
		System.out.println(toString());
	}
	
	public boolean getActionRobo()
	{
		return roboAction;
	}
	
	public String getJoueur()
	{
		if(joueur==Pion.JOUEUR0)
			return "JOUEUR0";
		return "JOUEUR1";
	}
	
	public void changeJoueur()
	{
		if(joueur==Pion.JOUEUR0)
			joueur =Pion.JOUEUR1;
		else
			joueur=Pion.JOUEUR0;
	}
	
	private boolean pieceJoueur(Coordonnees position)
	{
		return damier.getDamier(position)!=null && damier.getDamier(position).getJoueur()==joueur;
	}
	
	public boolean selectPiece(Coordonnees position)
	{
		return (position.coordonneValid() &&  pieceJoueur(position) && damier.getDamier(position).pieceDeplacable(damier,'r'));
	}
	
	public void test()
	{
		damier.setDamier(new Coordonnees(4, 0), new Pion(4,0,Pion.JOUEUR0));
		damier.setDamier(new Coordonnees(1, 5), new Pion(1,5,Pion.JOUEUR0));
		damier.setDamier(new Coordonnees(1, 1), new Pion(1,1,Pion.JOUEUR0));
		damier.setDamier(new Coordonnees(6, 3), new Pion(6,3,Pion.JOUEUR1));
		damier.setDamier(new Coordonnees(3, 1), new Pion(3,1,Pion.JOUEUR1));
		damier.setDamier(new Coordonnees(5, 1), new Dame(5,1,Pion.JOUEUR0));
		damier.setDamier(new Coordonnees(3, 2), new Dame(3,2,Pion.JOUEUR1));
		damier.setDamier(new Coordonnees(6, 0), null);
		
	}
	
	public boolean testFinPartie()
	{
		return damier.getRoiMort();
	}
	
	public Damier getDamier()
	{
		return damier;
	}
	
	public void setDamier(Damier d)
	{
		this.damier=d;
	}
	
	public void setCaseDamier(int x,int y,Piece p)
	{
		damier.setDamier(x, y, p);
	}
	
	public ArrayList<Damier> getHistory()
	{
		return history;
	}
	
	public int getPostionHistory()
	{
		return h;
	}
	
	public void setPostionHistory(int h)
	{
		this.h=h;
	}
	
	public void addHistory(Partie p)
	{
		if(history.size()==2)
		{
			if(h==0)
			{
				history.remove(1);
			}
			else
			{
				history.remove(0);
			}
		}
		
		
		history.add((Damier) p.damier.clone());
		this.h=history.size()-1;

	}
	
	public void save()
	{
		JFileChooser choix=new JFileChooser();
		
		if(choix.showOpenDialog(null)!=0)
			return ;
		
		String nom=choix.getSelectedFile().getAbsolutePath();
		char [] tabNom=nom.toCharArray();
		
		if(tabNom[tabNom.length-1]!='s' || tabNom[tabNom.length-2]!='r' || tabNom[tabNom.length-3]!='.')
		{
			 nom+=".rs";
		}
		DataOutputStream file=null;
		try {
			file = new DataOutputStream (new FileOutputStream(new File(nom))) ;
			
			file.writeInt(joueur);
			for(int i=0;i<Damier.DIM;i++)
			{
				for(int j=0;j<Damier.DIM;j++)
				{
					if(damier.getDamier(i, j)==null)
					{
						file.writeChar('.');
					}
					else
					{
						file.writeChars(damier.getDamier(i, j).toString());
					}
				}
				file.writeChar('\n');
			}
			
			file.close();
		}
		catch(Exception e)
		{
			System.out.println("la partie n'est pas sauvgarde");
			return;
		}
		
	}
	
	public Piece creerPiece(char c,int x,int y)
	{
		switch(c)
		{
			case 'p':return new Pion(x,y,Piece.JOUEUR0);
			case 't':return new Tours(x, y,Piece.JOUEUR0);
			case 'c':return new Cavaliers(x, y,Piece.JOUEUR0,'g');
			case 's':return new Cavaliers(x, y,Piece.JOUEUR0,'d');
			case 'f':return new Fous(x, y,Piece.JOUEUR0);
			case 'r':return new Roi(x, y,Piece.JOUEUR0);
			case 'd':return new Dame(x, y,Piece.JOUEUR0);
			case 'P':return new Pion(x,y,Piece.JOUEUR1);
			case 'T':return new Tours(x, y,Piece.JOUEUR1);
			case 'C':return new Cavaliers(x, y,Piece.JOUEUR1,'g');
			case 'S':return new Cavaliers(x, y,Piece.JOUEUR1,'d');
			case 'F':return new Fous(x, y,Piece.JOUEUR1);
			case 'R':return new Roi(x, y,Piece.JOUEUR1);
			case 'D':return new Dame(x, y,Piece.JOUEUR1);
		}
		return null;
	}
	
	public void open()
	{
		JFileChooser choix=new JFileChooser();
		if(choix.showOpenDialog(null)!=0)
			return ;
		
		String nom=choix.getSelectedFile().getAbsolutePath();
		char [] tabNom=nom.toCharArray();
		
		if(tabNom[tabNom.length-1]!='s' || tabNom[tabNom.length-2]!='r' || tabNom[tabNom.length-3]!='.')
		{
			 JOptionPane.showInternalMessageDialog( this , "le fichier n'exist pas !!!!", "Erreur",WIDTH);
			 return ;
		}
		
		DataInputStream file=null;
		
		try {
			file = new DataInputStream(new FileInputStream(choix.getSelectedFile()));
			
			joueur=file.readInt();
			for(int i=0;i<Damier.DIM;i++)
			{
				for(int j=0;j<Damier.DIM;j++)
				{
					char c=file.readChar();
					if(c=='.')
					{
						damier.setDamier(i, j, null);
					}
					else
					{
						if(c=='R' || c=='r')
						{
							Roi roi=(Roi)creerPiece(c,i,j);
							damier.setRoi(roi);
							damier.setDamier(i, j,roi);
						}
						else
						{
							damier.setDamier(i, j,creerPiece(c,i,j));
						}
					}
				}
				file.readChar();
			}
			
			file.close();
			
			history.clear();
			this.addHistory(this);
			this.h=0;
		}
		catch(Exception e)
		{
			System.out.println("la partie n'est pas charge");
			damier=new Damier();
			return;
		}
		
	}
	
	public void start()
	{
		
		while(!testFinPartie())
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(state=='4') 
			{
				robo.moveRobo();
				state='0';
				System.out.println(cpt);
				cpt++;
			}
			
			if(state=='3')
			{
				positionCase =new Coordonnees((int)((clicMouse.x-25)/50), (int)((clicMouse.y-20)/50));
				this.removeAll();
				try {
					damier.getDamier(positionPiece).move(positionCase, damier,'u');
					changeJoueur();
					addHistory(this);
					if(roboAction)
						state='4';
				}
				catch(Exception e)
				{
					state='0';
				}
			}
			if(state=='1')
			{

				try {
					AudioInputStream audio= AudioSystem.getAudioInputStream(getClass().getResource("/son/select2.wav"));
					Clip son=AudioSystem.getClip();
					son.open(audio);
					son.start();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				positionPiece=new Coordonnees((int)((clicMouse.x-25)/50), (int)((clicMouse.y-20)/50));
				if(selectPiece(positionPiece))
				{
					state='2';
				}
				else
				{
					state='0';
				}
			}
			
		}
	}
	
	//////////////////////// Interface graphique
	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		if(state=='0' || state=='2')
		{
			clicMouse=new Coordonnees((int)(arg0.getY()), (int)(arg0.getX()));
			if(state=='0')
				state='1';
			else
				state='3';
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/Images/echiquier.jpg"));
		Image image =icon.getImage();
		g2.drawImage(image, 0, 0, null);
		
		for(int i=0;i<Damier.DIM;i++)
		{
			for(int j=0;j<Damier.DIM;j++)
			{
				if(damier.getDamier(i, j)!=null)
				{
					g2.drawImage(damier.getDamier(i, j).getImage(), j*50+25, i*50+20, null);
				}
			}
		}
		
	}
	
	
}
























