package it.univr.swe;

/**
 * Lists the possible types of cars.
 */
public enum CarType
{
	MANUAL,
	AUTOMATIC;

	/**
	 * Returns the packet rate of this car type.
	 * @return The packet rate.
	 */
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
