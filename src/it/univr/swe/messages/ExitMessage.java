package it.univr.swe.messages;

import it.univr.swe.CarType;
import it.univr.swe.communication.CarChannel;

public class ExitMessage implements Message {
	
	private int source;
	private CarType type;
	private CarChannel channel;
	
	public ExitMessage(int source,CarType type,CarChannel channel){
		
		this.source = source;
		this.type = type;
		this.channel = channel;
		
	}

	public int getSource() {
		return source;
	}

	public CarType getType() {
		return type;
	}

	public CarChannel getChannel() {
		return channel;
	}
		
}
