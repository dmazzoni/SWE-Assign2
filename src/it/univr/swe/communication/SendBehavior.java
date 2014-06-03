package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.exception.*;
import it.univr.swe.messages.*;
import java.util.*;

public abstract class SendBehavior {

	protected Car car;
	protected Timer timer;
	
	protected SendBehavior(Car car) {
		this.car = car;
		this.timer = new Timer();
	}
	
	public void send(Message msg) {
		if (msg instanceof OkMessage)
			sendOk((OkMessage) msg);
		else if (msg instanceof SpeedMessage)
			sendSpeed((SpeedMessage) msg);
		else if (msg instanceof ExitMessage)
			sendExit((ExitMessage) msg);
		else
			throw new IllegalMessageException();
	}
	
	protected abstract void sendOk(OkMessage msg);
	
	protected abstract void sendSpeed(SpeedMessage msg);
	
	protected abstract void sendExit(ExitMessage msg);
}
