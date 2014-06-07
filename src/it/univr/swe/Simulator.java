package it.univr.swe;

public class Simulator extends Thread{
	
	private Tower tower;

	public Simulator(){
		
		tower = new Tower();
		
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
