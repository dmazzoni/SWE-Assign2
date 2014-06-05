package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

/**
 * The message transmission behavior of a {@link ManualCar}.
 */
public class ManualSend extends SendBehavior {
	
	/**
	 * The interval between speed messages, in milliseconds
	 */
	protected static final int SPEED_MSG_INTERVAL = 200;
	
	public ManualSend(Car car) {
		super(car);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void startSpeedUpdates() {
		timer.schedule(speedTask, 0, SPEED_MSG_INTERVAL);
	}

	/**
	 * {@inheritDoc}<br>
	 * The message is sent over the dedicated tower channel.
	 * @see TowerChannel
	 */
	@Override
	protected void sendOk(OkMessage msg) {
		TowerChannel ch = car.getTowerChannel();
		if (ch != null)
			ch.transmit(msg);
	}

	/**
	 * {@inheritDoc}<br>
	 * The message is sent over the standard channel the car has been assigned to.
	 * @see CarChannel
	 */
	@Override
	protected void sendSpeed(SpeedMessage msg) {
		CarChannel ch = car.getCarChannel();
		if (ch != null)
			ch.transmit(msg);
	}

	/**
	 * {@inheritDoc}<br>
	 * The message is sent over the dedicated tower channel.
	 * @see TowerChannel
	 */
	@Override
	protected void sendExit(ExitMessage msg) {
		timer.cancel();
		TowerChannel ch = car.getTowerChannel();
		if (ch != null)
			ch.transmit(msg);
	}

}
