package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

/**
 * Behavior that can be assigned to an {@link AutomaticCar} in order to adapt it into a {@link ManualCar}.
 * @see ReceiveAdapter
 */
public class SendAdapter extends AutomaticSend {
	
	/**
	 * The behavior to which the actions are redirected.
	 */
	private ManualSend manual;

	public SendAdapter(Car car) {
		super(car);
		manual = new ManualSend(car);
	}
	
	/**
	 * {@inheritDoc}<br>
	 * Updates are sent at the rate of a manual car.
	 */
	@Override
	protected void startSpeedUpdates() {
		timer.schedule(speedTask, 0, ManualSend.SPEED_MSG_INTERVAL);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void sendOk(OkMessage msg) {
		manual.sendOk(msg);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void sendSpeed(SpeedMessage msg) {
		manual.sendSpeed(msg);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void sendExit(ExitMessage msg) {
		manual.sendExit(msg);
	}

}
