package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

public class SendAdapter extends AutomaticSend {
	
	private ManualSend manual;

	public SendAdapter(Car car) {
		super(car);
		manual = new ManualSend(car);
	}
	
	@Override
	protected void sendOk(OkMessage msg) {
		manual.sendOk(msg);
	}

	@Override
	protected void sendSpeed(SpeedMessage msg) {
		manual.sendSpeed(msg);
	}

	@Override
	protected void sendExit(ExitMessage msg) {
		manual.sendExit(msg);
	}

}
