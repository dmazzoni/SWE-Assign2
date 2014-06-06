package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

/**
 * The message receiving behavior of an {@link AutomaticCar}.
 */
public class AutomaticReceive extends ReceiveBehavior {
	
	public AutomaticReceive(Car car) {
		super(car);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void receiveJoin(JoinMessage msg) {
		
		if (car.getCarChannel() == null) {
			TowerChannel ch = msg.getChannel();
			car.setTowerChannel(ch);
			OkMessage reply = new OkMessage(car.getId(), CarType.AUTOMATIC);
			car.send(reply);
		}
	}

	/**
	 * {@inheritDoc}<br>
	 * The car automatically slows down to the speed limit if the message specifies so.
	 */
	@Override
	protected void receiveTower(TowerMessage msg) {
		
		if (msg.getDestination() != car.getId())
			return;
		
		if (msg.isBrake()) {
			car.setDisplay("Decrease speed");
			car.setSpeed(50);
		}
		else {
			car.setDisplay("Ideal");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void receiveRegister(RegisterMessage msg) {
		
		if (msg.getDestination() != car.getId())
			return;
		
		CarChannel ch = msg.getChannel();
		if (ch != null) {
			car.setCarChannel(ch);
			car.setDisplay("Car accepted");
			car.registered();
		}
		else {
			car.setDisplay("Base station is busy, please wait");
		}
	}

}
