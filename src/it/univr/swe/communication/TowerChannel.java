package it.univr.swe.communication;

import it.univr.swe.Car;
import it.univr.swe.Tower;
import it.univr.swe.messages.JoinMessage;
import it.univr.swe.messages.Message;
import it.univr.swe.messages.OkMessage;
import it.univr.swe.messages.RegisterMessage;
import it.univr.swe.messages.TowerMessage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents the communication channel used by the {@link Tower} to send messages to cars,
 * and also used by cars during the registration phase.
 */
public class TowerChannel
{
	/**
	 * The tower to which forward messages sent by cars.
	 */
	private Tower tower;
	
	/**
	 * The list of cars to which broadcast messages sent by the tower.
	 */
	private List<Car> cars;
	
	/**
	 * Constructs a <tt>TowerChannel</tt> associated to the specified tower.
	 * @param tower the control tower
	 */
	public TowerChannel(Tower tower)
	{
		this.tower = tower;
		cars = new CopyOnWriteArrayList<Car>();
	}
	
	/**
	 * Transmits a message.<br>
	 * If it was sent by the tower, the message is broadcast to all cars.<br>
	 * Messages sent by cars are forwarded to the tower.
	 * @param msg the message to transmit
	 */
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
	
	/**
	 * Adds a car to the broadcast list of this channel.
	 * @param car the car to add
	 */
	public void registerCar(Car car)
	{
		cars.add(car);
	}
	
	/**
	 * Removes the car with the specified id from the broadcast list of this channel.
	 * @param id the car's id
	 */
	public void unregisterCar(int id)
	{
		int pos = -1;
		while(cars.get(++pos).getId() != id);
		cars.remove(pos);
	}
	
	/**
	 * Returns the list of cars currently registered to this channel.
	 * @return The list of registered cars.
	 */
	public List<Car> getCars(){
		return cars;
	}
}
