package it.univr.swe;

import it.univr.swe.communication.CarChannel;
import it.univr.swe.communication.TowerChannel;
import it.univr.swe.exception.IllegalMessageException;
import it.univr.swe.messages.ExitMessage;
import it.univr.swe.messages.JoinMessage;
import it.univr.swe.messages.Message;
import it.univr.swe.messages.OkMessage;
import it.univr.swe.messages.RegisterMessage;
import it.univr.swe.messages.SpeedMessage;
import it.univr.swe.messages.TowerMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class Tower
{
	private static final int SPEED_MSG_INTERVAL = 10;
	
	/**
	 * Map to take trace of the speed of every registered car in the system.
	 * If the Boolean value is set to true, the next sent message alerts the
	 * specified car to decrease its speed (brake).
	 */
	private Map<Integer,Boolean> speedMap;
	
	/**
	 * Array of channels used for the communication from registered cars to
	 * the tower
	 */
	private List<CarChannel> carChannels;
	
	/**
	 * Reference to the channel dedicated to the broadcast communication at
	 * every registered car.
	 */
	private TowerChannel towerChannel;
	
	/**
	 * Buffer for the messages created in response to invocations of the
	 * receive() method. 
	 */
	private List<Message> replyBuffer;
	
	/**
	 * Timer for the transmission interval of the messages.
	 */
	private Timer timer;
	
	/**
	 * Array of actions used by the simulator to show the application status.
	 */
	private List<String> actions;
	
	public Tower()
	{
		speedMap = new TreeMap<Integer, Boolean>();
		carChannels = new ArrayList<CarChannel>();
		carChannels.add(new CarChannel(this));
		towerChannel = new TowerChannel(this);
		replyBuffer = new ArrayList<Message>();
		timer = new Timer();
		actions = new CopyOnWriteArrayList<String>();
		timer.scheduleAtFixedRate(new TowerTask(), 0, SPEED_MSG_INTERVAL);
	}
	
	/**
	 * Method invoked by channels to transfer a message from cars to the tower.
	 * @param msg the message to transfer
	 * @throws IllegalMessageException
	 */
	public void receive(Message msg) throws IllegalMessageException
	{
		//Choose the action to do switching on the message implementation
		if(msg instanceof OkMessage)
		{
			receiveOk((OkMessage) msg);
		}
		else if(msg instanceof SpeedMessage)
		{
			receiveSpeed((SpeedMessage) msg);
		}
		else if(msg instanceof ExitMessage)
		{
			receiveExit((ExitMessage) msg);
		}
		else
			throw new IllegalMessageException();
	}
	
	/**
	 * Returns the dedicated tower channel.
	 * @return The tower channel.
	 */
	public TowerChannel getTowerChannel()
	{
		return towerChannel;
	}
	
	/**
	 * Returns the list of actions taken by the tower.
	 * @return The list of actions.
	 */
	public List<String> getActions()
	{
		List<String> result = actions;
		actions = new CopyOnWriteArrayList<String>();
		return result;
	}
	
	/**
	 * Method invoked by MainWindow to get the value of every CarChannel
	 * @return The List of carChannels
	 */
	public List<CarChannel> getCarChannels()
	{
		return carChannels;
	}
	
	/**
	 * Processes an incoming {@link OkMessage}.<br>
	 * A {@link RegisterMessage} is prepared in response to the received message,
	 * and if there are slots left the car is assigned to a channel.
	 * @param msg the received message
	 */
	private void receiveOk(OkMessage msg) {
		
		int id = msg.getSource();
		CarType type = msg.getType();
		RegisterMessage regMsg = null;
		
		boolean is_full = true;
		for (CarChannel ch : carChannels)
		{
			if( (100 - ch.getTraffic()) >= type.getTraffic())
			{
				is_full = false;
				ch.setTraffic(ch.getTraffic() + type.getTraffic());
				regMsg = new RegisterMessage(id, ch);
				break;
			}
		}
		
		if (is_full)
		{
			if(carChannels.size() < 5)
			{
				CarChannel ch = new CarChannel(this);
				actions.add("Tower created a new CarChannel");
				ch.setTraffic(ch.getTraffic() + type.getTraffic());
				regMsg = new RegisterMessage(id, ch);
				carChannels.add(ch);
			}
			else
				regMsg = new RegisterMessage(id, null);
		}
		
		replyBuffer.add(regMsg);
	}
	
	/**
	 * Processes an incoming {@link SpeedMessage}.<br>
	 * The map of speed statuses is updated according to the specified speed.
	 * @param msg the received message
	 */
	private void receiveSpeed(SpeedMessage msg) {
		
		int source = msg.getSource();
		int speed = msg.getSpeed();
		speedMap.put(source, (speed > 50) ? true : false);
	}
	
	/**
	 * Processes an incoming {@link ExitMessage}.<br>
	 * The car is unregistered from the network, and the slots in its channel are freed.
	 * @param msg the received message
	 */
	private void receiveExit(ExitMessage msg) {
		
		int source = msg.getSource();
		CarChannel ch = msg.getChannel();
		CarType type = msg.getType();
		ch.setTraffic(ch.getTraffic() - type.getTraffic());
		towerChannel.unregisterCar(source);
	}
	
	/**
	 * Manages the transmission of outgoing messages from the tower.
	 */
	private class TowerTask extends TimerTask {
		
		/**
		 * Index of the next speed entry to check.
		 */
		int mapIndex;
		
		/**
		 * Constructs a TowerTask.
		 */
		private TowerTask() {
			mapIndex = 0;
		}

		/**
		 * Broadcasts a message to all cars.<br>
		 * Messages are prioritized in the following order:<br>
		 * <ul>
		 * <li>{@link RegisterMessage}</li>
		 * <li>{@link SpeedMessage}</li>
		 * <li>{@link JoinMessage}</li>
		 * </ul>
		 */
		@Override
		public void run() {
			//If there's a message in the buffer
			if (replyBuffer.size() > 0) {
				towerChannel.transmit(replyBuffer.remove(0));
				actions.add("Tower sent RegisterMessage");
			}
			else if (speedMap.size() > 0) {
				int key = (int) speedMap.keySet().toArray()[mapIndex];
				boolean brake = speedMap.get(key);
				TowerMessage msg = new TowerMessage(key, brake);
				towerChannel.transmit(msg);
				actions.add("Tower sent TowerMessage to Car #" + msg.getDestination());
				mapIndex++;
			}
			
			if (mapIndex == speedMap.size()) {
				towerChannel.transmit(new JoinMessage(towerChannel));
				mapIndex = 0;
			}
		}
	}
}
