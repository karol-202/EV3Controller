package pl.karol202.ev3controller;

public class Result
{
	public static final int RESULT_CONNECTED = 0;
	public static final int RESULT_DISCONNECTED = 1;
	public static final int RESULT_EXCEPTION = 2;
	public static final int RESULT_NOT_CONNECTED = 3;
	public static final int RESULT_ALREADY_CONNECTED = 4;
	public static final int RESULT_UPDATE_STATUS = 5;

	private int resultCode;
	private Object data;

	public Result(int resultCode, Object data)
	{
		this.resultCode = resultCode;
		this.data = data;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public Object getData()
	{
		return data;
	}
}
