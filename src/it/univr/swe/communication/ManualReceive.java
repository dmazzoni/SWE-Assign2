package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

public class ManualReceive extends ReceiveBehavior {
	
	public ManualReceive(Car car) {
		super(car);
	}

	@Override
	protected void receiveJoin(JoinMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void receiveTower(TowerMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void receiveRegister(RegisterMessage msg) {
		// TODO Auto-generated method stub
		
	}

}
