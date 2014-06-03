package it.univr.swe;

import it.univr.swe.communication.*;
import it.univr.swe.messages.*;

public class AutomaticCar extends Car {

	public AutomaticCar(int id, ManualSend send, ManualReceive receive) {
		this.id = id;
		this.sendBehavior = send;
		this.receiveBehavior = receive;
	}
	
	@Override
	protected void exit() {
		ExitMessage exitMsg = new ExitMessage(id, CarType.AUTOMATIC, carChannel);
		sendBehavior.send(exitMsg);
	}
}
