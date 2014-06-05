package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.exception.*;
import it.univr.swe.messages.*;

/**
 * The abstract message receiving behavior of a {@link Car}.
 */
public abstract class ReceiveBehavior {
	
	/**
	 * The car associated to this behavior instance
	 */
	protected Car car;

	/**
	 * Constructs a behavior instance.
	 * @param car The car associated to this behavior
	 */
	protected ReceiveBehavior(Car car) {
		this.car = car;
	}
	
	/**
	 * Processes an incoming message.
	 * @param msg The received message
	 */
	public final void receive(Message msg) {
		if (msg instanceof JoinMessage)
			receiveJoin((JoinMessage) msg);
		else if (msg instanceof TowerMessage)
			receiveTower((TowerMessage) msg);
		else if (msg instanceof RegisterMessage)
			receiveRegister((RegisterMessage) msg);
		else
			throw new IllegalMessageException();
	}
	
	/**
	 * Processes an incoming {@link JoinMessage}.<br>
	 * The car replies with an {@link OkMessage} stating its type.
	 * @param msg The received message
	 * @see OkMessage
	 */
	protected abstract void receiveJoin(JoinMessage msg);
	
	/**
	 * Processes an incoming {@link TowerMessage}.
	 * @param msg The received message
	 */
	protected abstract void receiveTower(TowerMessage msg);
	
	/**
	 * Processes an incoming {@link RegisterMessage}.<br>
	 * If the car is accepted into the network, it starts sending speed updates to the control tower.
	 * @param msg The received message
	 */
	protected abstract void receiveRegister(RegisterMessage msg);
}
