package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

public class ManualReceive extends ReceiveBehavior {
	
	public ManualReceive(Car car) {
		super(car);
	}

	@Override
	protected void receiveJoin(JoinMessage msg) {
		
		if (car.getCarChannel() == null) {
			TowerChannel ch = msg.getChannel();
			car.setTowerChannel(ch);
			OkMessage reply = new OkMessage(car.getId(), CarType.MANUAL);
			car.send(reply);
		}
	}

	@Override
	protected void receiveTower(TowerMessage msg) {
		
		if (msg.getDestination() != car.getId())
			return;
		
		if (msg.isBrake()) {
			car.setDisplay("Decrease speed");
		}
		else {
			car.setDisplay("Ideal");
		}
	}

	@Override
	protected void receiveRegister(RegisterMessage msg) {
		
		if (msg.getDestination() != car.getId())
			return;
		
		CarChannel ch = msg.getChannel();
		if (ch != null) {
			car.setCarChannel(ch);
			car.setDisplay("Car accepted");
		}
		else {
			car.setDisplay("Base station is busy, please wait");
		}
	}

}
