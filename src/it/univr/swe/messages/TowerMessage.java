package it.univr.swe.messages;

public class TowerMessage implements Message {
	
	private int destination;
	private boolean brake;
	
	public TowerMessage(int destination,boolean brake){
		
		this.destination = destination;
		this.brake = brake;
		
	}

	public int getDestination() {
		return destination;
	}

	public boolean isBrake() {
		return brake;
	}

}
