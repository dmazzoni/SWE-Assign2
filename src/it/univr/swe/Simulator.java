package it.univr.swe;

import it.univr.swe.communication.*;

public class Simulator{
	
	private Tower tower;
	private static int id = 1;

	public Simulator(){
		
		tower = new Tower();
		TowerChannel towerChannel = tower.getTowerChannel();
		for(int i=0; i<40; i++) {
			Car c = new AutomaticCar(id++);
			c.setReceiveBehavior(new AutomaticReceive(c));
			c.setSendBehavior(new AutomaticSend(c));
			towerChannel.registerCar(c);
		}
		
		for(int i=0; i<50; i++) {
			Car c = new ManualCar(id++);
			c.setReceiveBehavior(new ManualReceive(c));
			c.setSendBehavior(new ManualSend(c));
			towerChannel.registerCar(c);
		}
		
	}


	
	/*QUESTO METODO È NECESSARIO!!! */

	/**
	 * Method invoked by MainWindow to get all the informations that it needs.
	 * @return The Tower Object
	 */
	public Tower getTower() {
		return this.tower;
	}

	
	
}
