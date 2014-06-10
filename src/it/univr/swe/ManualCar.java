package it.univr.swe;

import it.univr.swe.messages.*;

public class ManualCar extends Car {

	public ManualCar(int id) {
		this.id = id;
	}
	
	@Override
	protected void exit() {
		ExitMessage exitMsg = new ExitMessage(id, CarType.MANUAL, carChannel);
		sendBehavior.send(exitMsg);
	}
}
