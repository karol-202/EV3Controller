package pl.karol202.ev3controller.command;

import android.app.Activity;

public interface Command
{
	int TYPE_MOTOR = 0;
	int TYPE_MOTOR_STOP = 1;

	int getTypeCode();

	int getName();

	int getIcon();

	Class<? extends Activity> getEditActivity();

	boolean isError();

	void run();

	void loadCommand(int list, int command);

	void saveCommand(int list, int command);

	void removeCommand(int list, int command);
}
