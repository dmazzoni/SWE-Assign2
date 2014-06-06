package it.univr.swe;

import java.util.ArrayList;
import java.util.List;

import it.univr.swe.communication.CarChannel;
import it.univr.swe.gui.MainWindow;

public class Simulator extends Thread{
	
	Tower tower;
	List<Car> cars;
	List<CarChannel> carChannel;

	public Simulator(){
		
		tower = new Tower();
		cars = new ArrayList<Car>();
		carChannel = new ArrayList<CarChannel>();
		
		
		
	}

	public void getObjects(MainWindow mainWindow) {
		mainWindow.setObjects(cars, carChannel, tower);
	}

	
	
}
