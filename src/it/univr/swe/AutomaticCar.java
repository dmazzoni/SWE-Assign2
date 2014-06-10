package it.univr.swe;

import it.univr.swe.messages.*;

public class AutomaticCar extends Car {

	public AutomaticCar(int id) {
		this.id = id;
	}
	
	@Override
	protected void exit() {
		ExitMessage exitMsg = new ExitMessage(id, CarType.AUTOMATIC, carChannel);
		sendBehavior.send(exitMsg);
	}
}
