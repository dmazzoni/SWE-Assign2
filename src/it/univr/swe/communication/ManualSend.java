package it.univr.swe.communication;

import java.util.TimerTask;

import it.univr.swe.*;
import it.univr.swe.messages.*;

public class ManualSend extends SendBehavior {
	
	private static final int SPEED_MSG_INTERVAL = 100;
	
	private TimerTask speedTask;
	
	public ManualSend(Car car) {
		super(car);
		speedTask = new TimerTask() {

			@Override
			public void run() {
				ManualSend.this.sendSpeed(new SpeedMessage(car.getId(), car.getSpeed()));
			}
			
		};
		timer.schedule(speedTask, 0, SPEED_MSG_INTERVAL);
	}

	@Override
	protected void sendOk(OkMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void sendSpeed(SpeedMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void sendExit(ExitMessage msg) {
		// TODO Auto-generated method stub
		
	}

}
