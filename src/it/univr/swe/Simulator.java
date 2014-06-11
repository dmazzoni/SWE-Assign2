package it.univr.swe;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it.univr.swe.communication.*;
import it.univr.swe.gui.InfoBean;

public class Simulator {
	
	private Tower tower;
	private static int id = 1;

	public Simulator(){
		
		tower = new Tower();
		Timer time = new Timer();
		time.scheduleAtFixedRate(new SimulatorTask(), 0, 1000);
			
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
			
			List<Car> cars = getRegisteredCars();
			
			for(Car c : cars) {
				int delta;
				if(c instanceof ManualCar && c.getDisplay().equals("Decrease speed"))
					delta = (int)(Math.random()*10) - 10;
				else 
					delta = (int)(Math.random()*10) - 4;
				c.setSpeed(c.getSpeed() + delta);
			}
			if(count == 5) {
				Car c = cars.get((int)(Math.random()*(cars.size()-1)));
				c.exit();
				count = 0;
			}
			
		}
		
		/**
		 * Method that return only the cars that are registered in the network
		 * @return A List of registered cars
		 */
		public List<Car> getRegisteredCars(){
			List<Car> allCars = towerChannel.getCars();
			List<Car> registeredCars = new ArrayList<Car>();
			for(Car c : allCars){
				if(c.isRegistered()){
					registeredCars.add(c);
				}
			}
			return registeredCars;
		}
	}

	/**
	 * Method invoked by MainWindow to get all the data that needs
	 * @return The InfoBean
	 */
	public InfoBean getInfoBean() {
		List<Car> cars = tower.getTowerChannel().getCars();
		List<String> newActions = tower.getActions();
		List<CarChannel> channels = tower.getCarChannels();
		return new InfoBean(cars,newActions,channels);
	}

	
	
}
