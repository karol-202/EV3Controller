package pl.karol202.ev3controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import lejos.robotics.RegulatedMotor;
import pl.karol202.ev3controller.Controller.ResultListener;

public class Robot
{
	public static Robot rbt;

	public String robotIp;
	public RemoteRequestEV3 ev3;
	public RegulatedMotor motorA;
	public RegulatedMotor motorB;
	public RegulatedMotor motorC;
	public RegulatedMotor motorD;

	public Robot()
	{
		Robot.rbt = this;
	}

	/*public void connect(Context context)
	{
		connect(context, null);
	}*/

	public void connect(Context context, final ResultListener listener)
	{
		final Dialog dialog = new Dialog(context);
		dialog.setTitle(R.string.title_dialog_connect);
		dialog.setContentView(R.layout.dialog_connect);
		final EditText editTextIp = (EditText) dialog.findViewById(R.id.editText_ip);
		final Button buttonConnect = (Button) dialog.findViewById(R.id.button_connect);
		buttonConnect.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				robotIp = editTextIp.getText().toString();
				new Controller(listener).execute("connect", robotIp);
				dialog.cancel();
			}
		});
		dialog.show();
	}

	/*public void disconnect(Context context)
	{
		disconnect(context, null);
	}*/

	public void disconnect(Context context, final ResultListener listener)
	{
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle(R.string.title_dialog_disconnect);
		dialogBuilder.setMessage(R.string.text_disconnect);
		dialogBuilder.setPositiveButton("Tak", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				robotIp = "";
				new Controller(listener).execute("disconnect");
				dialog.cancel();
			}
		});
		dialogBuilder.setNegativeButton("Nie", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		});
		dialogBuilder.create().show();
	}

	public void initRobotComponents()
	{
		new Controller().execute("init");
	}

	//Do not use in main thread
	public void init()
	{
		if(ev3 == null) return;
		Profile profile = ProfilesManager.getSelectedProfile();
		if(profile == null) return;
		rbt.motorA = rbt.ev3.createRegulatedMotor("A", profile.getMotorType(0));
		rbt.motorB = rbt.ev3.createRegulatedMotor("B", profile.getMotorType(1));
		rbt.motorC = rbt.ev3.createRegulatedMotor("C", profile.getMotorType(2));
		rbt.motorD = rbt.ev3.createRegulatedMotor("D", profile.getMotorType(3));
	}

	public void getStatus(ResultListener listener, int resultCode)
	{
		if(ev3 == null) return;
		new Controller(listener).execute("getStatus", Integer.toString(resultCode));
	}

	public RegulatedMotor getMotor(int index)
	{
		if(ev3 == null) return null;
		switch(index)
		{
		case 0: return motorA;
		case 1: return motorB;
		case 2: return motorC;
		case 3: return motorD;
		}
		return null;
	}

	public boolean checkConnected()
	{
		if(ev3 != null) if(ev3.checkConnected()) return true;
		disconnected();
		return false;
}

	public void disconnected()
	{
		rbt.ev3 = null;
		rbt.motorA = null;
		rbt.motorB = null;
		rbt.motorC = null;
		rbt.motorD = null;
	}

	public void stopAllMotors() { new Controller().execute("stopMotors"); }

	//Do not use in main thread
	public void stopMotors()
	{
		rbt.motorA.flt();
		rbt.motorB.flt();
		rbt.motorC.flt();
		rbt.motorD.flt();
	}
}
