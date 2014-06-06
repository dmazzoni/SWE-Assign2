package it.univr.swe;

import it.univr.swe.communication.CarChannel;
import it.univr.swe.gui.MainWindow;

public class Simulator extends Thread{

	public Simulator(){
		
		
	}
	
	public void refresh(MainWindow main){
		
		Tower tower = new Tower();
		Car[] cars = new Car[10];
		CarChannel[] carChannel = new CarChannel[5];
		
		main.setCarChannel(carChannel);
		main.setTower(tower);
		main.setCars(cars);
		
	}
	
}
