package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

public class ManualSend extends SendBehavior {
	
	public ManualSend(Car car) {
		super(car);
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
