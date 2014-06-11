package it.univr.swe;

import it.univr.swe.communication.*;
import it.univr.swe.messages.*;

/**
 * Represents a car.
 */
public abstract class Car {

	/**
	 * The id of the car.
	 */
	protected int id;
	
	/**
	 * The speed.
	 */
	private int speed;
	
	/**
	 * The message transmission behavior associated to this car.
	 */
	protected SendBehavior sendBehavior;
	
	/**
	 * The message receiving behavior associated to this car.
	 */
	protected ReceiveBehavior receiveBehavior;
	
	/**
	 * The channel used to send messages to the {@link Tower} once the car is registered.
	 */
	protected CarChannel carChannel;
	
	/**
	 * The channel used to register the car and receive messages from the {@link Tower}.
	 */
	private TowerChannel towerChannel;
	
	/**
	 * The information shown on the car's display.
	 */
	private String display;
	
	/**
	 * Processes an incoming message.
	 * @param msg the received message
	 * @see ReceiveBehavior
	 */
	public void receive(Message msg) {
		receiveBehavior.receive(msg);
	}
	
	/**
	 * Sends a message to the tower.
	 * @param msg the message to send
	 * @see SendBehavior
	 */
	public void send(Message msg) {
		sendBehavior.send(msg);
	}
	
	/**
	 * Starts sending speed updates to the tower.
	 */
	public void registered() {
		sendBehavior.startSpeedUpdates();
	}
	
	/**
	 * Returns the car's current speed.
	 * @return The speed value.
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Sets the message transmission behavior of this car.
	 * @param sendBehavior the behavior
	 */
	public void setSendBehavior(SendBehavior sendBehavior) {
		this.sendBehavior = sendBehavior;
	}

	/**
	 * Sets the message receiving behavior of this car.
	 * @param receiveBehavior the behavior
	 */
	public void setReceiveBehavior(ReceiveBehavior receiveBehavior) {
		this.receiveBehavior = receiveBehavior;
	}

	/**
	 * Sets the speed of this car.<br>
	 * If a negative value is specified, the speed is set to 0.
	 * @param speed the speed value
	 */
	public void setSpeed(int speed) {
		if(speed < 0)
			this.speed = 0;
		else
			this.speed = speed;
	}
	
	/**
	 * Returns this car's id.
	 * @return The id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns this car's standard channel.
	 * @return The car channel.
	 */
	public CarChannel getCarChannel() {
		return carChannel;
	}

	/**
	 * Sets this car's standard channel.
	 * @param carChannel the car channel
	 */
	public void setCarChannel(CarChannel carChannel) {
		this.carChannel = carChannel;
	}
	
	/**
	 * Returns this car's tower channel.
	 * @return The tower channel.
	 */
	public TowerChannel getTowerChannel() {
		return towerChannel;
	}

	/**
	 * Sets this car's tower channel.
	 * @param towerChannel the tower channel
	 */
	public void setTowerChannel(TowerChannel towerChannel) {
		this.towerChannel = towerChannel;
	}
	
	/**
	 * Returns whether a car has been admitted to the network or not.
	 * @return <tt>true</tt> if the car has been registered, <tt>false</tt> otherwise.
	 */
	public boolean isRegistered() {
		return carChannel != null;
	}

	/**
	 * Returns the current message shown on the car's display.
	 * @return The displayed message.
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * Sets the message shown on the car's display.
	 * @param display the message to show
	 */
	public void setDisplay(String display) {
		this.display = display;
	}
	
	/**
	 * Prompts a car to exit the network.
	 */
	public abstract void exit();
	
}
