package it.univr.swe.messages;

import it.univr.swe.communication.TowerChannel;

public class JoinMessage implements Message {
	
	private TowerChannel channel;
	
	public JoinMessage(TowerChannel channel){
		
		this.channel = channel;
	}

	public TowerChannel getChannel() {
		return channel;
	}

}
