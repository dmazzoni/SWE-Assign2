package it.univr.swe;

import it.univr.swe.communication.CarChannel;
import it.univr.swe.communication.TowerChannel;
import it.univr.swe.exception.IllegalMessageException;
import it.univr.swe.messages.ExitMessage;
import it.univr.swe.messages.Message;
import it.univr.swe.messages.OkMessage;
import it.univr.swe.messages.RegisterMessage;
import it.univr.swe.messages.SpeedMessage;
import it.univr.swe.messages.TowerMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

public class Tower
{
	private static final int SPEED_MSG_INTERVAL = 50;
	
	/**
	 * Map to take trace of the speed of every registered car in the system.
	 * If the Boolean value is set to true, the next sent message alerts the
	 * specified car to decrease its speed (brake).
	 */
	private Map<Integer,Boolean> map;
	/**
	 * Array of channels used for the communication from registered cars to
	 * the tower
	 */
	private CarChannel[] carChannels;
	/**
	 * Reference to the channel dedicated to the broadcast communication at
	 * every registered car.
	 */
	private TowerChannel towerChannel;
	/**
	 * Buffer for the messages created in response to an invocation of the
	 * method receive. The tower send them all on the next signal by the
	 * internal timer. 
	 */
	private List<Message> buffer;
	
	/**
	 * Timer for the transmission interval of the messages.
	 */
	private Timer timer;
	
	public Tower()
	{
		map = new HashMap<Integer, Boolean>();
		carChannels = new CarChannel[5];
		carChannels[0] = new CarChannel(this);
		carChannels[1] = new CarChannel(this);
		carChannels[2] = new CarChannel(this);
		carChannels[3] = new CarChannel(this);
		carChannels[4] = new CarChannel(this);
		towerChannel = new TowerChannel(this);
		buffer = new ArrayList<Message>();
		timer = new Timer();
		
		//Set the task
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				//Send all the messages into the buffer
				for(Message msg : buffer)
				{
					if(msg instanceof RegisterMessage)
					{
						towerChannel.transmit(msg);
					}
				}
				//Send the messages based on the map.
				for(Entry<Integer, Boolean> entry : map.entrySet())
				{
					towerChannel.transmit(new TowerMessage(entry.getKey(), entry.getValue()));
				}
			}
		};
		timer.schedule(task, 0, SPEED_MSG_INTERVAL);
	}
	
	/**
	 * Method invoked by channels to transfer a messages from cars
	 * to the tower.
	 * @param msg	the message to transfer
	 * @throws IllegalMessageException
	 */
	public void receive(Message msg) throws IllegalMessageException
	{
		//Choose the action to do switching on the message implementation
		if(msg instanceof OkMessage)
		{
			int id = ((OkMessage) msg).getSource();
			CarType type = ((OkMessage) msg).getType();
			RegisterMessage regMsg = null;
			
			for(CarChannel ch : carChannels)
			{
				if( (100 - ch.getTraffic()) >= type.getTraffic())
				{
					ch.setTraffic(ch.getTraffic() + type.getTraffic());
					regMsg = new RegisterMessage(id, ch);
					break;
				}
			}
			
			if(regMsg == null)
				regMsg = new RegisterMessage(id, null);
			
			buffer.add(regMsg);
		}
		else if(msg instanceof SpeedMessage)
		{
			int source = ((SpeedMessage) msg).getSource();
			int speed = ((SpeedMessage) msg).getSpeed();
			map.put(source, (speed > 50) ? true : false);
		}
		else if(msg instanceof ExitMessage)
		{
			int source = ((ExitMessage) msg).getSource();
			CarChannel ch = ((ExitMessage) msg).getChannel();
			CarType type = ((ExitMessage) msg).getType();
			ch.setTraffic(ch.getTraffic() - type.getTraffic());
			towerChannel.unregisterCar(source);
		}
		else
			throw new IllegalMessageException();
	}
}
