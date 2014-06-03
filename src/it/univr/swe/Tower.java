package it.univr.swe;

import it.univr.swe.communication.CarChannel;
import it.univr.swe.communication.TowerChannel;
import it.univr.swe.exception.IllegalMessageException;
import it.univr.swe.messages.ExitMessage;
import it.univr.swe.messages.Message;
import it.univr.swe.messages.OkMessage;
import it.univr.swe.messages.SpeedMessage;

import java.util.HashMap;
import java.util.Map;

public class Tower
{
	private Map<Integer,Boolean> map;
	private CarChannel[] carChannels;
	private TowerChannel towerChannel;
	
	public Tower()
	{
		map = new HashMap<Integer, Boolean>();
		carChannels = new CarChannel[5];
		carChannels[0] = new CarChannel();
		carChannels[1] = new CarChannel();
		carChannels[2] = new CarChannel();
		carChannels[3] = new CarChannel();
		carChannels[4] = new CarChannel();
		towerChannel = new TowerChannel();
	}
	
	public void receive(Message msg)
	{
		if(msg instanceof OkMessage)
		{
			
		}
		else if(msg instanceof SpeedMessage)
		{
			
		}
		else if(msg instanceof ExitMessage)
		{
			
		}
		else
			throw new IllegalMessageException();
	}
}
