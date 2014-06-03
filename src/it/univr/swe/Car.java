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
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
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
	
	protected abstract void exit();
	
}
