package it.univr.swe;

import it.univr.swe.messages.*;

/**
 * Represents an automatic car.
 */
public class AutomaticCar extends Car {

	/**
	 * Constructs an <tt>AutomaticCar</tt> with the specified id.
	 * @param id the car's id
	 */
	public AutomaticCar(int id) {
		this.id = id;
		this.setDisplay("");
	}
	
	/**
	 * {@inheritDoc}
	 * The car sends an {@link ExitMessage} to the {@link Tower}, specifying its type.
	 */
	@Override
	public void exit() {
		ExitMessage exitMsg = new ExitMessage(id, CarType.AUTOMATIC, carChannel);
		sendBehavior.send(exitMsg);
	}
}
