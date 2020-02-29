package pl.karol202.ev3controller.command;

import android.app.Activity;
import android.content.SharedPreferences;
import lejos.robotics.RegulatedMotor;
import pl.karol202.ev3controller.Robot;

public class CommandMotorStop implements Command
{
	private int motorIndex;
	private boolean lock;

	@Override
	public int getTypeCode() { return CommandType.MOTOR_STOP.getTypeCode(); }

	@Override
	public int getName() { return CommandType.MOTOR_STOP.getName(); }

	@Override
	public int getIcon() { return CommandType.MOTOR_STOP.getImage(); }

	@Override
	public Class<? extends Activity> getEditActivity() { return CommandType.MOTOR_STOP.getEditActivity(); }

	@Override
	public boolean isError()
	{
		return false;
	}

	@Override
	public void run()
	{
		RegulatedMotor motor = Robot.rbt.getMotor(motorIndex);
		if(lock) motor.stop();
		else motor.flt();
	}

	@Override
	public void loadCommand(int list, int command)
	{
		SharedPreferences prefs = CommandsManager.prefs;
		String header = "list" + list + "command" + command;
		motorIndex = prefs.getInt(header + "motor", -1);
		lock = prefs.getBoolean(header + "lock", true);
	}

	@Override
	public void saveCommand(int list, int command)
	{
		SharedPreferences.Editor editor = CommandsManager.editor;
		String header = "list" + list + "command" + command;
		editor.putInt(header + "motor", motorIndex);
		editor.putBoolean(header + "lock", lock);
	}

	@Override
	public void removeCommand(int list, int command)
	{
		SharedPreferences.Editor editor = CommandsManager.editor;
		String header = "list" + list + "command" + command;
		editor.remove(header + "motor");
		editor.remove(header + "lock");
	}

	public int getMotorIndex() { return motorIndex; }

	public void setMotorIndex(int motorIndex) { this.motorIndex = motorIndex; }

	public boolean isLock() { return lock; }

	public void setLock(boolean lock) { this.lock = lock; }
}
