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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.Vector;

public class Tower
{
	private static final int SPEED_MSG_INTERVAL = 50;
	
	/**
	 * Map to take trace of the speed of every registered car in the system.
	 * If the Boolean value is set to true, the next sent message alerts the
	 * specified car to decrease its speed (brake).
	 */
	private TreeMap<Integer,Boolean> map;
	/**
	 * Array of channels used for the communication from registered cars to
	 * the tower
	 */
	private ArrayList<CarChannel> carChannels;
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
	
	/**
	 * Position of the next map entry to check
	 */
	private int next;
	
	/**
	 * Array of actions used by the simulator to show the application run.
	 */
	private Vector<String> actions;
	
	public Tower()
	{
		map = new TreeMap<Integer, Boolean>();
		carChannels = new ArrayList<CarChannel>();
		carChannels.add(new CarChannel(this));
		towerChannel = new TowerChannel(this);
		buffer = new ArrayList<Message>();
		timer = new Timer();
		next = 0; //Invalid value
		actions = new Vector<String>();
		
		//Set the task
		TimerTask task = new TimerTask()
		{
			@Override
			public void run()
			{
				//If there's some message into the buffer
				if(buffer.size() > 0)
				{
					towerChannel.transmit(buffer.remove(0));
					actions.add("Tower send RegisterMessage");
				}
				else if(map.size() > 0)
				{
					int key = (int) map.keySet().toArray()[next];
					boolean brake = map.get(key);
					towerChannel.transmit(new TowerMessage(next, brake));
					actions.add("Tower send TowerMessage");
					
					if(++next == map.size())
						next = 0;
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
			
			boolean is_full = true;
			for(CarChannel ch : carChannels)
			{
				if( (100 - ch.getTraffic()) >= type.getTraffic())
				{
					is_full = false;
					ch.setTraffic(ch.getTraffic() + type.getTraffic());
					regMsg = new RegisterMessage(id, ch);
					break;
				}
			}
			
			if(is_full)
			{
				if(carChannels.size() < 5)
				{
					CarChannel ch = new CarChannel(this);
					actions.add("Tower create new CarChannel");
					ch.setTraffic(ch.getTraffic() + type.getTraffic());
					regMsg = new RegisterMessage(id, ch);
					carChannels.add(ch);
				}
				else
					regMsg = new RegisterMessage(id, null);
			}
			
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
	
	/**
	 * Method invoked by Simulator to get TowerChannel for adding a Car or 
	 * for showing the state of all cars
	 * @return
	 */
	public TowerChannel getTowerChannel()
	{
		return towerChannel;
	}
	
	public Vector<String> getActions()
	{
		Vector<String> result = actions;
		actions = new Vector<String>();
		return result;
	}
	
	/**
	 * Method invoked by MainWindow to get the value of every CarChannel
	 * @return The List of carChannels
	 */
	public ArrayList<CarChannel> getCarChannels()
	{
		return carChannels;
	}
}
