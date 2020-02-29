package pl.karol202.ev3controller;

import android.os.AsyncTask;

import java.io.IOException;

public class Controller extends AsyncTask<String, Integer, Result>
{
	public interface ResultListener
	{
		void onPostExecute(Result result);
	}

	private Robot rbt;
	private ResultListener listener;

	public Controller()
	{
		this.rbt = Robot.rbt;
	}

	public Controller(ResultListener listener)
	{
		this();
		this.listener = listener;
	}

	@Override
	protected Result doInBackground(String... params)
	{
		if(params[0] == "connect")
		{
			if(rbt.ev3 != null) return new Result(Result.RESULT_ALREADY_CONNECTED, null);
			try
			{
				rbt.ev3 = new RemoteRequestEV3(params[1]);
				rbt.initRobotComponents();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return new Result(Result.RESULT_EXCEPTION, null);
			}
			return new Result(Result.RESULT_CONNECTED, null);
		}

		if(rbt.ev3 == null) return new Result(Result.RESULT_NOT_CONNECTED, null);

		if(params[0] == "disconnect")
		{
			rbt.motorA.stop();
			rbt.motorB.stop();
			rbt.motorC.stop();
			rbt.motorC.stop();
			rbt.ev3.disConnect();
			rbt.disconnected();
			return new Result(Result.RESULT_DISCONNECTED, null);
		}
		if(params[0] == "init")
		{
			rbt.init();
		}
		if(params[0] == "getStatus")
		{
			RobotStatus status = new RobotStatus(rbt.robotIp, rbt.ev3.getName(), rbt.ev3.getPower().getVoltage());
			return new Result(Integer.parseInt(params[1]), status);
		}
		if(params[0] == "stopMotors")
		{
			rbt.stopMotors();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Result result)
	{
		if(listener != null) listener.onPostExecute(result);
	}
}
