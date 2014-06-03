package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;
import java.util.*;

public abstract class SendBehavior {

	protected Car car;
	private Timer timer;
	
	protected SendBehavior(Car car) {
		this.car = car;
	}
	
	public void send(Message msg) {
		if (msg instanceof OkMessage)
			sendOk((OkMessage) msg);
		else if (msg instanceof SpeedMessage)
			sendSpeed((SpeedMessage) msg);
		else if (msg instanceof ExitMessage)
			sendExit((ExitMessage) msg);
	}
	
	protected abstract void sendOk(OkMessage msg);
	
	protected abstract void sendSpeed(SpeedMessage msg);
	
	protected abstract void sendExit(ExitMessage msg);
}
