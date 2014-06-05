package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.exception.*;
import it.univr.swe.messages.*;

import java.util.*;

/**
 * The abstract message transmission behavior of a {@link Car}.
 */
public abstract class SendBehavior {

	/**
	 * The car associated to this behavior instance
	 */
	protected Car car;
	
	/**
	 * The timer that triggers transmission of speed updates
	 */
	protected Timer timer;
	
	/**
	 * The task launched by the timer
	 */
	protected TimerTask speedTask;
	
	/**
	 * Constructs a behavior instance.
	 * @param car The car associated to this behavior
	 */
	protected SendBehavior(Car car) {
		this.car = car;
		timer = new Timer();
		speedTask = new TimerTask() {

			@Override
			public void run() {
				Car c = SendBehavior.this.car;
				SendBehavior.this.sendSpeed(new SpeedMessage(c.getId(), c.getSpeed()));
			}
			
		};
	}
	
	/**
	 * Sends the specified message over the appropriate channel.
	 * @param msg The message to send
	 */
	public final void send(Message msg) {
		if (msg instanceof OkMessage)
			sendOk((OkMessage) msg);
		else if (msg instanceof SpeedMessage)
			sendSpeed((SpeedMessage) msg);
		else if (msg instanceof ExitMessage)
			sendExit((ExitMessage) msg);
		else
			throw new IllegalMessageException();
	}
	
	/**
	 * Schedules the speed update task on this behavior's timer.
	 */
	protected abstract void startSpeedUpdates();
	
	/**
	 * Sends the specified {@link OkMessage} to the control tower.
	 * @param msg The message to send
	 */
	protected abstract void sendOk(OkMessage msg);
	
	/**
	 * Sends the specified {@link SpeedMessage} to the control tower.
	 * @param msg The message to send
	 */
	protected abstract void sendSpeed(SpeedMessage msg);
	
	/**
	 * Sends the specified {@link ExitMessage} to the control tower.
	 * Disables speed updates from the associated car.
	 * @param msg The message to send
	 */
	protected abstract void sendExit(ExitMessage msg);
}
