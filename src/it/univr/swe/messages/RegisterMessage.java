package it.univr.swe.messages;

import it.univr.swe.communication.CarChannel;

public class RegisterMessage implements Message {
	
	private int destination;
	private CarChannel channel;
	
	public RegisterMessage(int destination,CarChannel channel){
		
		this.destination = destination;
		this.channel = channel;
		
	}

	public int getDestination() {
		return destination;
	}

	public CarChannel getChannel() {
		return channel;
	}

}
