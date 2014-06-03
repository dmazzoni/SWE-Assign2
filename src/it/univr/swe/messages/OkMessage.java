package it.univr.swe.messages;

import it.univr.swe.CarType;

public class OkMessage implements Message {
	
	private int source;
	private CarType type;
	
	public OkMessage(int source,CarType type){
		
		this.source = source;
		this.type = type;
		
	}
	
	public int getSource() {
		return source;
	}
	public CarType getType() {
		return type;
	}
	
	

}
