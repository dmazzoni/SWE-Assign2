package it.univr.swe.messages;

public class SpeedMessage implements Message {
	
	private int source;
	private int speed;
	
	public SpeedMessage(int source,int speed){
		
		this.source = source;
		this.speed = speed;
		
	}

	public int getSource() {
		return source;
	}

	public int getSpeed() {
		return speed;
	}
	
	

}
