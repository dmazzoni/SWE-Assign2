package it.univr.swe.communication;

import it.univr.swe.*;
import it.univr.swe.messages.*;

public class AutomaticReceive extends ReceiveBehavior {
	
	public AutomaticReceive(AutomaticCar car) {
		super(car);
	}

	@Override
	protected void receiveJoin(JoinMessage msg) {
		
		if (car.getCarChannel() == null) {
			OkMessage reply = new OkMessage(car.getId(), CarType.AUTOMATIC);
			car.send(reply);
		}
	}

	@Override
	protected void receiveTower(TowerMessage msg) {
		
		if (msg.getDestination() != car.getId())
			return;
		
		if (msg.isBrake()) {
			car.setDisplay("Decrease speed");
			car.setSpeed(50);
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
