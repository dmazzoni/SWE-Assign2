package it.univr.swe.communication;

import it.univr.swe.Car;
import it.univr.swe.Tower;
import it.univr.swe.messages.JoinMessage;
import it.univr.swe.messages.Message;
import it.univr.swe.messages.OkMessage;
import it.univr.swe.messages.RegisterMessage;
import it.univr.swe.messages.TowerMessage;

import java.util.ArrayList;
import java.util.List;

public class TowerChannel
{
	private Tower tower;
	private List<Car> cars;
	
	public TowerChannel(Tower tower)
	{
		this.tower = tower;
		cars = new ArrayList<Car>();
	}
	
	public void transmit(Message msg)
	{
		if(msg instanceof JoinMessage || msg instanceof TowerMessage || msg instanceof RegisterMessage)
		{
			for(Car car : cars)
			{
				car.receive(msg);
			}
		}
		else if(msg instanceof OkMessage)
		{
			tower.receive(msg);
		}
	}
	
	public void registerCar(Car car)
	{
		cars.add(car);
	}
	
	public void unregisterCar(int id)
	{
		int pos = -1;
		while(cars.get(++pos).getId() != id);
		cars.remove(pos);
	}
}
