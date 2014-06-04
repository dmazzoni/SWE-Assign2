package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.exception.*;
import it.univr.swe.messages.*;

import java.util.*;

public abstract class SendBehavior {

	protected Car car;
	protected Timer timer;
	protected TimerTask speedTask;
	
	protected SendBehavior(Car car) {
		this.car = car;
		timer = new Timer();
		speedTask = new TimerTask() {

			@Override
			public void run() {
				Car c = SendBehavior.this.car;
				SendBehavior.this.sendSpeed(new SpeedMessage(c.getId(), c.getSpeed()));
			}
			
		};
	}
	
	public final void send(Message msg) {
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
