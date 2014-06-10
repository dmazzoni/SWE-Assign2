package it.univr.swe;

import it.univr.swe.messages.*;

public class ManualCar extends Car {

	public ManualCar(int id) {
		this.id = id;
		this.setDisplay("");
	}
	
	@Override
	public void exit() {
		ExitMessage exitMsg = new ExitMessage(id, CarType.MANUAL, carChannel);
		sendBehavior.send(exitMsg);
	}
}
