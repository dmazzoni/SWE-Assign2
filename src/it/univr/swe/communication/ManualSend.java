package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

public class ManualSend extends SendBehavior {
	
	protected static final int SPEED_MSG_INTERVAL = 200;
	
	public ManualSend(Car car) {
		super(car);
	}
	
	@Override
	protected void startSpeedUpdates() {
		timer.schedule(speedTask, 0, SPEED_MSG_INTERVAL);
	}

	@Override
	protected void sendOk(OkMessage msg) {
		TowerChannel ch = car.getTowerChannel();
		if (ch != null)
			ch.transmit(msg);
	}

	@Override
	protected void sendSpeed(SpeedMessage msg) {
		CarChannel ch = car.getCarChannel();
		if (ch != null)
			ch.transmit(msg);
	}

	@Override
	protected void sendExit(ExitMessage msg) {
		timer.cancel();
		TowerChannel ch = car.getTowerChannel();
		if (ch != null)
			ch.transmit(msg);
	}

}
