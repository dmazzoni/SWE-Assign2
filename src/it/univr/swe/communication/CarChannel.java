package it.univr.swe.communication;

import it.univr.swe.Tower;
import it.univr.swe.messages.Message;

public class CarChannel
{
	private int traffic;
	private Tower tower;
	
	public CarChannel()
	{
		
	}
	
	public void trasnmit(Message msg)
	{
		
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
