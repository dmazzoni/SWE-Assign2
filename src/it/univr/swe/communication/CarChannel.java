package it.univr.swe.communication;

import it.univr.swe.Tower;
import it.univr.swe.messages.ExitMessage;
import it.univr.swe.messages.Message;
import it.univr.swe.messages.SpeedMessage;

/**
 * Represents a communication channel used by registered cars to send messages to the tower.
 */
public class CarChannel
{
	/**
	 * The channel capacity already assigned to connected cars.
	 */
	private int traffic;
	
	/**
	 * The tower to which forward the messages sent by cars.
	 */
	private Tower tower;
	
	/**
	 * Constructs a channel associated to the specified tower.
	 * @param tower the control tower
	 */
	public CarChannel(Tower tower)
	{
		this.tower = tower;
	}
	
	/**
	 * Forwards a message to the {@link Tower}.
	 * @param msg the message to transmit
	 */
	public void transmit(Message msg)
	{
		if(msg instanceof SpeedMessage || msg instanceof ExitMessage)
		{
			tower.receive(msg);
		}
	}
	
	/**
	 * Returns the currently used capacity of this channel.
	 * @return The used capacity value.
	 */
	public int getTraffic()
	{
		return this.traffic;
	}
	
	/**
	 * Sets the used capacity of this channel.
	 * @param traffic the updated used capacity
	 */
	public void setTraffic(int traffic)
	{
		this.traffic = traffic;
	}
}
