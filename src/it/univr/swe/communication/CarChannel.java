package it.univr.swe.communication;

import it.univr.swe.Tower;
import it.univr.swe.messages.ExitMessage;
import it.univr.swe.messages.Message;
import it.univr.swe.messages.SpeedMessage;

public class CarChannel
{
	private int traffic;
	private Tower tower;
	
	public CarChannel(Tower tower)
	{
		this.tower = tower;
	}
	
	public void transmit(Message msg)
	{
		if(msg instanceof SpeedMessage || msg instanceof ExitMessage)
		{
			tower.receive(msg);
		}
	}
	
	public int getTraffic()
	{
		return this.traffic;
	}
	
	public void setTraffic(int traffic)
	{
		this.traffic = traffic;
	}
}
