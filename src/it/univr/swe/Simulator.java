package it.univr.swe;

import java.util.ArrayList;
import java.util.List;

import it.univr.swe.communication.CarChannel;
import it.univr.swe.gui.MainWindow;

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
	 * @param mainWindow The MainWindow object
	 */
	public void getObjects(MainWindow mainWindow) {
		mainWindow.setObjects(tower.getTowerChannel().getCars(),tower);
	}

	
	
}
