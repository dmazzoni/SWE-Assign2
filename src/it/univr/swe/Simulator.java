package it.univr.swe;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it.univr.swe.communication.*;
import it.univr.swe.gui.InfoBean;

/**
 * Manages the overall operation of the system.
 *
 */
public class Simulator {
	
	/**
	 * The tower.
	 */
	private Tower tower;
	
	/**
	 * The ID progressively assigned to each car.
	 */
	private static int id = 1;

	/**
	 * Constructs a simulator that initializes its timer, task and the {@link Tower}.
	 */
	public Simulator(){
		
		tower = new Tower();
		Timer time = new Timer();
		time.scheduleAtFixedRate(new SimulatorTask(), 0, 1000);
			
	}
	
	/**
	 * Every time this task is scheduled by the timer, the simulator:
	 * <li> creates 1 automatic car until they are 40
	 * <li> creates 1 manual car until they are 50
	 * <li> randomly varies the speed of the active cars
	 * <li> every 5 cycles makes a car exit 
	 */
	private class SimulatorTask extends TimerTask {

		/**
		 * Counts the automatic cars created
		 */
		private int automaticCars = 0;
		
		/**
		 * Counts the manual cars created
		 */
		private int manualCars = 0;
		
		/**
		 * Reference to the Tower channel to register new active cars
		 */
		private TowerChannel towerChannel;
	
		/**
		 * Counts the cycles of run of the task
		 */
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
				if (cars.size() > 0) {
					Car c = cars.get((int)(Math.random()*(cars.size()-1)));
					c.exit();
				}
				count = 0;
			}
			
		}
		
		/**
		 * Method that returns only the cars that are registered in the network
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
