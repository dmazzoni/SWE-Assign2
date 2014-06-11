package it.univr.swe.gui;

import it.univr.swe.Car;
import it.univr.swe.communication.CarChannel;

import java.util.List;

public class InfoBean {
	
	private List<Car> cars;
	private List<String> newActions;
	private List<CarChannel> traffics; 
	
	public InfoBean(List<Car> cars, List<String> newActions, List<CarChannel> traffics){
		this.cars = cars;
		this.newActions = newActions;
		this.traffics = traffics;
	}

	public List<Car> getCars() {
		return cars;
	}

	public List<String> getNewActions() {
		return newActions;
	}

	public List<CarChannel> getTraffics() {
		return traffics;
	}
	
	

}
