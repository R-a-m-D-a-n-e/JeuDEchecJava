package Play;


public class Start extends Thread {

	public Start()
	{
		super();
	}
	
	public void run()
	{
		Play.play.getPartie().start();
	}
	
}
