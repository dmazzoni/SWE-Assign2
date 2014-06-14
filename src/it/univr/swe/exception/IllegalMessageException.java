package it.univr.swe.exception;

/**
 * Exception thrown in case an incorrect message type is transmitted.
 */
public class IllegalMessageException extends IllegalArgumentException
{
	private static final long serialVersionUID = 1L;
	
	public IllegalMessageException()
	{
		super();
	}
}
