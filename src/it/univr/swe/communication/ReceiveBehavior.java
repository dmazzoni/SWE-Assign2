package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.exception.*;
import it.univr.swe.messages.*;

public abstract class ReceiveBehavior {
	
	protected Car car;

	protected ReceiveBehavior(Car car) {
		this.car = car;
	}
	
	public final void receive(Message msg) {
		if (msg instanceof JoinMessage)
			receiveJoin((JoinMessage) msg);
		else if (msg instanceof TowerMessage)
			receiveTower((TowerMessage) msg);
		else if (msg instanceof RegisterMessage)
			receiveRegister((RegisterMessage) msg);
		else
			throw new IllegalMessageException();
	}
	
	protected abstract void receiveJoin(JoinMessage msg);
	
	protected abstract void receiveTower(TowerMessage msg);
	
	protected abstract void receiveRegister(RegisterMessage msg);
}
