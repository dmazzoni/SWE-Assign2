package it.univr.swe;

import it.univr.swe.communication.*;
import it.univr.swe.messages.*;

public abstract class Car {

	protected int id;
	private int speed;
	protected SendBehavior sendBehavior;
	protected ReceiveBehavior receiveBehavior;
	protected CarChannel carChannel;
	private TowerChannel towerChannel;
	private String display;
	
	public void receive(Message msg) {
		receiveBehavior.receive(msg);
	}
	
	public void send(Message msg) {
		sendBehavior.send(msg);
	}
	
	public void registered() {
		sendBehavior.startSpeedUpdates();
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSendBehavior(SendBehavior sendBehavior) {
		this.sendBehavior = sendBehavior;
	}

	public void setReceiveBehavior(ReceiveBehavior receiveBehavior) {
		this.receiveBehavior = receiveBehavior;
	}

	public void setSpeed(int speed) {
		if(speed < 0)
			this.speed = 0;
		else
			this.speed = speed;
	}
	
	public int getId() {
		return id;
	}
	
	public CarChannel getCarChannel() {
		return carChannel;
	}

	public void setCarChannel(CarChannel carChannel) {
		this.carChannel = carChannel;
	}
	
	public TowerChannel getTowerChannel() {
		return towerChannel;
	}

	public void setTowerChannel(TowerChannel towerChannel) {
		this.towerChannel = towerChannel;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
		
	public abstract void exit();
	
}
