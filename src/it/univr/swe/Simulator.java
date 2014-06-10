package it.univr.swe;

import it.univr.swe.gui.MainWindow;

public class Simulator extends Thread{
	
	private Tower tower;

	public Simulator(){
		
		tower = new Tower();
		
	}

	public static void main(String args[]){
		
		Simulator sim = new Simulator();
		
		MainWindow main = new MainWindow(sim);
		main.setVisible(true);
		
		sim.start();
		
	}
	
	/*QUESTO METODO È NECESSARIO!!! 
	 * Gli oggetti che vengono passati alla main window possono provenire da fonti diverse
	 * ( ad esempio cars è contenuto in TowerChannel )
	*/

	/**
	 * Method invoked by MainWindow to get all the informations that it needs.
	 * @return The Tower Object
	 */
	public Tower getTower() {
		return this.tower;
	}

	
	
}
