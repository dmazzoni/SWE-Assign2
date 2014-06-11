package it.univr.swe;

import it.univr.swe.messages.*;

/**
 * Represents a manual car.
 */
public class ManualCar extends Car {

	/**
	 * Constructs a <tt>ManualCar</tt> with the specified id.
	 * @param id the car's id
	 */
	public ManualCar(int id) {
		this.id = id;
		this.setDisplay("");
	}
	
	/**
	 * {@inheritDoc}
	 * The car sends an {@link ExitMessage} to the {@link Tower}, specifying its type.
	 */
	@Override
	public void exit() {
		ExitMessage exitMsg = new ExitMessage(id, CarType.MANUAL, carChannel);
		sendBehavior.send(exitMsg);
	}
}
