package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

/**
 * Behavior that can be assigned to an {@link AutomaticCar} in order to adapt it into a {@link ManualCar}.
 * @see SendAdapter
 */
public class ReceiveAdapter extends AutomaticReceive {
	
	/**
	 * The behavior to which the actions are redirected.
	 */
	private ManualReceive manual;

	public ReceiveAdapter(Car car) {
		super(car);
		manual = new ManualReceive(car);
	}
	
	/**
	 * {@inheritDoc}<br>
	 * In this case, the car identifies itself as a {@link ManualCar}.
	 */
	@Override
	protected void receiveJoin(JoinMessage msg) {
		manual.receiveJoin(msg);
	}

	/**
	 * If its speed is too high, the car does not automatically slow down.
	 */
	@Override
	protected void receiveTower(TowerMessage msg) {
		manual.receiveTower(msg);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void receiveRegister(RegisterMessage msg) {
		manual.receiveRegister(msg);
	}

}
