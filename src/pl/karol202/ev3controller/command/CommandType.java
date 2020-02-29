package pl.karol202.ev3controller.command;

import android.app.Activity;
import pl.karol202.ev3controller.R;
import pl.karol202.ev3controller.activity.ActivityEditCommandMotor;
import pl.karol202.ev3controller.activity.ActivityEditCommandMotorStop;

public enum CommandType
{
	MOTOR(Command.TYPE_MOTOR, R.string.name_command_motor, R.drawable.arrow_up, ActivityEditCommandMotor.class),
	MOTOR_STOP(Command.TYPE_MOTOR_STOP, R.string.name_command_motor_stop, R.drawable.arrow_up, ActivityEditCommandMotorStop.class);

	private final int typeCode;
	private final int name;
	private final int image;
	private final Class<? extends Activity> editActivity;

	CommandType(int typeCode, int name, int image, Class<? extends Activity> editActivity)
	{
		this.typeCode = typeCode;
		this.name = name;
		this.image = image;
		this.editActivity = editActivity;
	}

	public int getTypeCode()
	{
		return typeCode;
	}

	public int getName()
	{
		return name;
	}

	public int getImage() { return image; }

	public Class<? extends Activity> getEditActivity()
	{
		return editActivity;
	}
}
