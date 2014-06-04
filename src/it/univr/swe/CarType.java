package it.univr.swe;

public enum CarType
{
	MANUAL,
	AUTOMATIC;

	public int getTraffic()
	{
		switch(this)
		{
			case AUTOMATIC:
				return 10;
			case MANUAL:
				return 5;
			default:
				return 0;
		}
	}
	
}
