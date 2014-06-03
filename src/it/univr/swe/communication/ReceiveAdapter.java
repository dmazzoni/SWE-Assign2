package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

public class ReceiveAdapter extends AutomaticReceive {
	
	private ManualReceive manual;

	public ReceiveAdapter(Car car) {
		super(car);
		manual = new ManualReceive(car);
	}
	
	@Override
	protected void receiveJoin(JoinMessage msg) {
		manual.receiveJoin(msg);
	}

	@Override
	protected void receiveTower(TowerMessage msg) {
		manual.receiveTower(msg);
	}

	@Override
	protected void receiveRegister(RegisterMessage msg) {
		manual.receiveRegister(msg);
	}

}
