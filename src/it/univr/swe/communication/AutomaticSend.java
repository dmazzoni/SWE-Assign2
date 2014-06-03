package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

public class AutomaticSend extends SendBehavior {
	
	private static final int SPEED_MSG_INTERVAL = 100;
	
	public AutomaticSend(Car car) {
		super(car);
		timer.schedule(speedTask, 0, SPEED_MSG_INTERVAL);
	}

	@Override
	protected void sendOk(OkMessage msg) {
		TowerChannel ch = car.getTowerChannel();
		ch.transmit(msg);
	}

	@Override
	protected void sendSpeed(SpeedMessage msg) {
		CarChannel ch = car.getCarChannel();
		ch.transmit(msg);
	}

	@Override
	protected void sendExit(ExitMessage msg) {
		timer.cancel();
		TowerChannel ch = car.getTowerChannel();
		ch.transmit(msg);
	}

}
