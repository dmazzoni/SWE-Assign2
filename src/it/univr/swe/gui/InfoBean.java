package it.univr.swe.gui;

import it.univr.swe.Car;
import it.univr.swe.communication.CarChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates information about cars, channels, and tower actions.
 */
public class InfoBean {
	
	/**
	 * The cars connected to the {@link Tower}.
	 */
	private List<Car> cars;
	
	/**
	 * Strings which describe the tower actions.
	 */
	private List<String> newActions;
	
	/**
	 * The traffic levels of each {@link CarChannel}.
	 */
	private List<Integer> trafficLevels; 
	
	/**
	 * Constructs an InfoBean with the specified information.
	 * @param cars the list of cars
	 * @param newActions the list of tower actions
	 * @param trafficLevels the list of traffic levels
	 */
	public InfoBean(List<Car> cars, List<String> newActions, List<CarChannel> channels){
		this.cars = cars;
		this.newActions = newActions;
		this.trafficLevels = new ArrayList<Integer>();
		for (CarChannel ch : channels)
			trafficLevels.add(ch.getTraffic());
	}

	/**
	 * Returns the cars currently connected to the {@link TowerChannel}.
	 * @return The list of cars.
	 */
	public List<Car> getCars() {
		return cars;
	}

	/**
	 * Returns the tower actions.
	 * @return The list of actions.
	 */
	public List<String> getNewActions() {
		return newActions;
	}

	/**
	 * Returns the list of traffic levels.
	 * @return The list of channel usage levels.
	 */
	public List<Integer> getTraffics() {
		return trafficLevels;
	}
	
	

}
