package it.univr.swe;

import it.univr.swe.messages.*;

public class AutomaticCar extends Car {

	public AutomaticCar(int id) {
		this.id = id;
		this.setDisplay("");
	}
	
	@Override
	public void exit() {
		ExitMessage exitMsg = new ExitMessage(id, CarType.AUTOMATIC, carChannel);
		sendBehavior.send(exitMsg);
	}
}
