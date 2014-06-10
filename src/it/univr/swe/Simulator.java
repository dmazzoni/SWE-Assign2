package it.univr.swe;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it.univr.swe.communication.*;

public class Simulator {
	
	private Tower tower;
	private static int id = 1;

	public Simulator(){
		
		tower = new Tower();
		Timer time = new Timer();
		time.scheduleAtFixedRate(new SimulatorTask(), 0, 3000);
			
	}
	
	private class SimulatorTask extends TimerTask {

		private int automaticCars = 0;
		private int manualCars = 0;
		private TowerChannel towerChannel;
	
		private int count = 0;
		
		private SimulatorTask() {
			this.towerChannel = tower.getTowerChannel();
		}
		@Override
		public void run() {
			count++;
			List<Car> cars = towerChannel.getCars();
			
			if(automaticCars < 40) {
				Car c = new AutomaticCar(id++);
				c.setSpeed(45);
				c.setReceiveBehavior(new AutomaticReceive(c));
				c.setSendBehavior(new AutomaticSend(c));
				towerChannel.registerCar(c);
				automaticCars++;
			}
			if(manualCars < 50) {
				Car c = new ManualCar(id++);
				c.setSpeed(45);
				c.setReceiveBehavior(new ManualReceive(c));
				c.setSendBehavior(new ManualSend(c));
				towerChannel.registerCar(c);
				manualCars++;
			}
			for(Car c : cars) {
				int delta;
				if(c instanceof ManualCar && c.getDisplay().equals("Decrease speed"))
					delta = (int)(Math.random()*10) - 10;
				else 
					delta = (int)(Math.random()*10) - 4;
				c.setSpeed(c.getSpeed() + delta);
			}
			if(count == 10) {
				Car c = cars.get((int)Math.random()*(cars.size()-1));
				c.exit();
				count = 0;
			}
			
		}
	}

	/**
	 * Method invoked by MainWindow to get all the informations that it needs.
	 * @return The Tower Object
	 */
	public Tower getTower() {
		return this.tower;
	}

	
	
}
