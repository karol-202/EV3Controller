package pl.karol202.ev3controller.command;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import pl.karol202.ev3controller.Robot;

public class CommandMotor implements Command
{
	public class AsyncTaskMotorStop extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				RegulatedMotor motor = Robot.rbt.getMotor(motorIndex);
				while(motor.isMoving()) Thread.sleep(10);
				if(brake) motor.stop();
				else motor.flt(true);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			return null;
		}
	}

	public static final int LARGE_MOTOR_MAX_SPEED = 1050;
	public static final int MEDIUM_MOTOR_MAX_SPEED = 1560;

	private int motorIndex;
	private int speed;
	private int limit;
	private boolean relativeToCurrent;
	private boolean brake;
	private boolean immediately;

	public CommandMotor() {}

	@Override
	public int getTypeCode()
	{
		return CommandType.MOTOR.getTypeCode();
	}

	@Override
	public int getName()
	{
		return CommandType.MOTOR.getName();
	}

	@Override
	public int getIcon() { return CommandType.MOTOR.getImage(); }

	@Override
	public Class<? extends Activity> getEditActivity() { return CommandType.MOTOR.getEditActivity(); }

	@Override
	public boolean isError()
	{
		if(motorIndex < 0 || motorIndex > 3) return true;
		if(limit < -1) return true;
		return false;
	}

	@Override
	public void run()
	{
		RegulatedMotor motor = Robot.rbt.getMotor(motorIndex);
		if(limit == -1)
		{
			boolean backward = speed < 0;
			int speed = this.speed * (backward ? -1 : 1);
			motor.setSpeed(speed);

			if(!backward) motor.forward();
			else motor.backward();

			try
			{
				if (!immediately) while (motor.isMoving()) Thread.sleep(10);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			motor.setSpeed(speed);
			if(relativeToCurrent) motor.rotate(limit, immediately);
			else motor.rotateTo(limit, immediately);
		}
		if(immediately) new AsyncTaskMotorStop().execute();
		else
		{
			if(brake) motor.stop();
			else motor.flt(true);
		}
	}

	@Override
	public void loadCommand(int list, int command)
	{
		SharedPreferences prefs = CommandsManager.prefs;
		String header = "list" + list + "command" + command;
		motorIndex = prefs.getInt(header + "motor", -1);
		speed = prefs.getInt(header + "speed", -1);
		limit = prefs.getInt(header + "limit", -1);
		relativeToCurrent = prefs.getBoolean(header + "relativeToCurrent", true);
		brake = prefs.getBoolean(header + "brake", false);
		immediately = prefs.getBoolean(header + "immediatly", false);
	}

	@Override
	public void saveCommand(int list, int command)
	{
		SharedPreferences.Editor editor = CommandsManager.editor;
		String header = "list" + list + "command" + command;
		editor.putInt(header + "motor", motorIndex);
		editor.putInt(header + "speed", speed);
		editor.putInt(header + "limit", limit);
		editor.putBoolean(header + "relativeToCurrent", relativeToCurrent);
		editor.putBoolean(header + "brake", brake);
		editor.putBoolean(header + "immediatly", immediately);
	}

	@Override
	public void removeCommand(int list, int command)
	{
		SharedPreferences.Editor editor = CommandsManager.editor;
		String header = "list" + list + "commamnd" + command;
		editor.remove(header + "motor");
		editor.remove(header + "speed");
		editor.remove(header + "limit");
		editor.remove(header + "relativeToCurrent");
		editor.remove(header + "brake");
		editor.remove(header + "immediatly");
	}

	//public static int getLargeMotorMaxSpeed() { return LARGE_MOTOR_MAX_SPEED; }

	//public static int getMediumMotorMaxSpeed() { return MEDIUM_MOTOR_MAX_SPEED; }

	public int getMotorIndex()
	{
		return motorIndex;
	}

	public void setMotorIndex(int motorIndex)
	{
		this.motorIndex = motorIndex;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public int getLimit()
	{
		return limit;
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	public boolean isRelativeToCurrent()
	{
		return relativeToCurrent;
	}

	public void setRelativeToCurrent(boolean relativeToCurrent)
	{
		this.relativeToCurrent = relativeToCurrent;
	}

	public boolean isBrake()
	{
		return brake;
	}

	public void setBrake(boolean brake)
	{
		this.brake = brake;
	}

	public boolean isImmediately()
	{
		return immediately;
	}

	public void setImmediately(boolean immediately)
	{
		this.immediately = immediately;
	}
}
